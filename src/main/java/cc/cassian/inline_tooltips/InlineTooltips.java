package cc.cassian.inline_tooltips;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.BeehiveBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InlineTooltips implements ClientModInitializer {
	public static final String MOD_ID = "inline_tooltips";

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
                for (EquipmentSlotGroup equipmentSlotGroup : EquipmentSlotGroup.values()) {
                    itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier, display) -> {
                        if (display != ItemAttributeModifiers.Display.hidden() && attributeModifier.amount() != 0) {
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
                            addIcon(icon, amount.get(), tooltipFlag, list, component);
                        }
                    });
                }
                if (itemStack.has(DataComponents.BEES) && itemStack.get(DataComponents.BEES) != null) {
                    addIcon(ResourceLocation.withDefaultNamespace("bees"), itemStack.get(DataComponents.BEES).bees().size(), tooltipFlag, list, component);
                }
                if (itemStack.has(DataComponents.BLOCK_STATE) && itemStack.get(DataComponents.BLOCK_STATE) != null) {
                    addIcon(ResourceLocation.withDefaultNamespace("honey"), itemStack.get(DataComponents.BLOCK_STATE).get(BeehiveBlock.HONEY_LEVEL), tooltipFlag, list, component);
                }
                // Add icon
                if (!component.equals(Component.empty()))
                    list.add(component);
            }
        }));
    }

    private void addIcon(ResourceLocation icon, double amount, TooltipFlag tooltipFlag, List<Component> list, MutableComponent component) {
        var iconComponent = Component.object(new AtlasSprite(AtlasSprite.DEFAULT_ATLAS, ResourceLocation.fromNamespaceAndPath(icon.getNamespace(), "inline_tooltip_icons/"+ icon.getPath()))).append(ModHelpers.format(amount) + " ");
        if (Minecraft.getInstance().hasAltDown() && tooltipFlag.isAdvanced()) {
            iconComponent.append(Component.literal(" (%s)".formatted(icon)));
            list.add(iconComponent);
        } else {
            component.append(iconComponent);
        }
    }
}