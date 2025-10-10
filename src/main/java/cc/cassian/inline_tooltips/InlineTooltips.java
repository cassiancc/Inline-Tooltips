package cc.cassian.inline_tooltips;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.BeehiveBlock;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InlineTooltips implements ClientModInitializer {
	public static final String MOD_ID = "inline_tooltips";
    public static final ModConfig CONFIG = ModConfig.createToml(FabricLoader.getInstance().getConfigDir(),"", MOD_ID, ModConfig.class);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register(((itemStack, tooltipContext, tooltipFlag, list) -> {
            if (!Minecraft.getInstance().hasShiftDown()) {
                var component = Component.empty();
                // Attribute Modifiers
                if (CONFIG.iconTooltips.attributeTooltips) {
                    for (EquipmentSlotGroup equipmentSlotGroup : EquipmentSlotGroup.values()) {
                        itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier, display) -> {
                            if (display != ItemAttributeModifiers.Display.hidden()) {
                                var player = Minecraft.getInstance().player;
                                AtomicReference<Double> amount = new AtomicReference<>(attributeModifier.amount());
                                if (player != null) {
                                    amount.set(switch (attributeModifier.operation()) {
                                        case ADD_VALUE -> attributeModifier.amount() + player.getAttributeBaseValue(holder);
                                        case ADD_MULTIPLIED_BASE, ADD_MULTIPLIED_TOTAL ->
                                                attributeModifier.amount() * player.getAttributeBaseValue(holder);
                                    });
                                }
                                amount.set(SharpnessHelpers.addSharpnessDamage(itemStack, amount.get(), player, attributeModifier));
                                var icon = holder.unwrapKey().orElseThrow().location();
                                if (amount.get()!=0)
                                    addIcon(icon, amount.get(), tooltipFlag, list, component);
                            }
                        });
                    }
                }
                if (itemStack.has(DataComponents.BEES) && CONFIG.iconTooltips.beesTooltip) {
                    var bees = itemStack.get(DataComponents.BEES);
                    if (bees == null) return;
                    addIcon(ResourceLocation.withDefaultNamespace("bees"), bees.bees().size(), tooltipFlag, list, component);

                }
                if (CONFIG.iconTooltips.fuelTooltip) {
                    var level = Minecraft.getInstance().level;
                    if (level != null && level.fuelValues().isFuel(itemStack)) {
                        addIcon(ResourceLocation.withDefaultNamespace("fuel"), level.fuelValues().burnDuration(itemStack) /200f, tooltipFlag, list, component);
                    }
                }
                if (CONFIG.iconTooltips.honeyTooltip && itemStack.has(DataComponents.BLOCK_STATE)) {
                    var state = itemStack.get(DataComponents.BLOCK_STATE);
                    if (state == null) return;
                    var honey = state.get(BeehiveBlock.HONEY_LEVEL);
                    if (honey == null) return;
                    addIcon(ResourceLocation.withDefaultNamespace("honey"), honey, tooltipFlag, list, component);
                }
                // Add icon
                if (!component.equals(Component.empty()))
                    list.add(component);
            }
            if (CONFIG.textTooltips.lodestoneTooltip && itemStack.has(DataComponents.LODESTONE_TRACKER)) {
                var state = itemStack.get(DataComponents.LODESTONE_TRACKER);
                if (state == null || state.target().isEmpty()) return;
                GlobalPos globalPos = state.target().get();
                var pos = globalPos.pos();
                list.add(
                        Component.translatable("gui.inline_tooltips.target").withStyle(ChatFormatting.GRAY).append(
                        Component.literal("X: %d, Y: %d, Z: %d".formatted(pos.getX(), pos.getY(), pos.getZ())).withStyle(ChatFormatting.GOLD)));
                list.add(
                        Component.translatable("gui.inline_tooltips.dimension").withStyle(ChatFormatting.GRAY).append(
                        Component.translatableWithFallback(globalPos.dimension().location().toLanguageKey("dimension"), WordUtils.capitalizeFully(globalPos.dimension().location().getPath())).withStyle(ChatFormatting.GOLD)));
            }
            if (CONFIG.textTooltips.compassTooltip && itemStack.is(Items.COMPASS) && !itemStack.has(DataComponents.LODESTONE_TRACKER)) {
                var pos = Minecraft.getInstance().player.blockPosition();
                list.add(
                        Component.translatable("gui.inline_tooltips.position").withStyle(ChatFormatting.GRAY).append(
                                Component.literal("X: %d, Y: %d, Z: %d".formatted(pos.getX(), pos.getY(), pos.getZ())).withStyle(ChatFormatting.GOLD)));
            }
        }));
    }

    private void addIcon(ResourceLocation icon, double amount, TooltipFlag tooltipFlag, List<Component> list, MutableComponent component) {
        var iconComponent = Component.object(new AtlasSprite(AtlasSprite.DEFAULT_ATLAS, ResourceLocation.fromNamespaceAndPath(icon.getNamespace(), "inline_tooltip_icons/"+ icon.getPath()))).append(ModHelpers.format(amount) + " ");
        if (Minecraft.getInstance().hasAltDown() && InlineTooltips.CONFIG.developerOptions.debugInfo) {
            iconComponent.append(Component.literal(" (%s)".formatted(icon)));
            list.add(iconComponent);
        } else {
            component.append(iconComponent);
        }
    }
}