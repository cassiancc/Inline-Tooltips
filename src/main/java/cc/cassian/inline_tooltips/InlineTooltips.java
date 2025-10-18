package cc.cassian.inline_tooltips;

import cc.cassian.inline_tooltips.config.ModConfig;
import cc.cassian.inline_tooltips.helpers.ModHelpers;
import cc.cassian.inline_tooltips.helpers.SharpnessHelpers;
//? if =1.21.1 {
/*import com.samsthenerd.inline.api.InlineAPI;
import com.samsthenerd.inline.api.InlineData;
import com.samsthenerd.inline.api.data.SpriteInlineData;
import com.samsthenerd.inline.impl.InlineStyle;
import com.samsthenerd.inline.utils.TextureSprite;
*///?}
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
//? if >1.21.8
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InlineTooltips {
	public static final String MOD_ID = "inline_tooltips";
    public static final ModConfig CONFIG = ModConfig.createToml(Platform.INSTANCE.getConfigDir(),"", MOD_ID, ModConfig.class);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    public static void addTooltips(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> list) {
        // Add icon tooltips
        if (!ModHelpers.hasShiftDown()) {
            var component = Component.empty();
            // Attribute Modifiers
            addAttributeTooltips(itemStack, list, component);
            addBeeTooltips(itemStack, list, component);
            addFuelTooltips(itemStack, list, component);
            addHoneyTooltips(itemStack, list, component);
            // Add icon
            if (!component.equals(Component.empty()))
                list.add(component);
        }
        // Add text tooltips
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
        if (CONFIG.textTooltips.recoveryCompassTooltip && itemStack.is(Items.RECOVERY_COMPASS)) {
            var lastDeath = Minecraft.getInstance().player.getLastDeathLocation();
            if (lastDeath.isEmpty()) return;
            GlobalPos globalPos = lastDeath.get();
            var pos = globalPos.pos();
            list.add(
                    Component.translatable("gui.inline_tooltips.target").withStyle(ChatFormatting.GRAY).append(
                            Component.literal("X: %d, Y: %d, Z: %d".formatted(pos.getX(), pos.getY(), pos.getZ())).withStyle(ChatFormatting.AQUA)));
            list.add(
                    Component.translatable("gui.inline_tooltips.dimension").withStyle(ChatFormatting.GRAY).append(
                            Component.translatableWithFallback(globalPos.dimension().location().toLanguageKey("dimension"), WordUtils.capitalizeFully(globalPos.dimension().location().getPath())).withStyle(ChatFormatting.AQUA)));
        }
        if (CONFIG.textTooltips.compassTooltip && itemStack.is(Items.COMPASS) && !itemStack.has(DataComponents.LODESTONE_TRACKER)) {
            var pos = Minecraft.getInstance().player.blockPosition();
            list.add(
                    Component.translatable("gui.inline_tooltips.position").withStyle(ChatFormatting.GRAY).append(
                            Component.literal("X: %d, Y: %d, Z: %d".formatted(pos.getX(), pos.getY(), pos.getZ())).withStyle(ChatFormatting.GOLD)));
        }
        if (CONFIG.textTooltips.durabilityTooltip && !tooltipFlag.isAdvanced() && itemStack.isDamaged() && itemStack.has(DataComponents.DAMAGE)) {
            list.add(Component.translatable("item.durability", itemStack.getMaxDamage() - itemStack.getDamageValue(), itemStack.getMaxDamage()).withStyle(ChatFormatting.GRAY));
        }
    }

    private static void addAttributeTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (CONFIG.iconTooltips.attributeTooltips) {
            for (EquipmentSlotGroup equipmentSlotGroup : EquipmentSlotGroup.values()) {
                //? if >1.21.8 {
                itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier, display) -> {
                    //?} else {
                    /*itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier) -> {
                     *///?}
                    //? if >1.21.8
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
                            addIcon(icon, amount.get(), list, component);
                        //? if >1.21.8
                    }
                });
            }
        }
    }

    private static void addBeeTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (itemStack.has(DataComponents.BEES) && CONFIG.iconTooltips.beesTooltip) {
            var bees = itemStack.get(DataComponents.BEES);
            if (bees == null) return;
            addIcon(ResourceLocation.withDefaultNamespace("bees"), bees
                    //? if >1.21.8
                    .bees()
                    .size(), list, component);
        }
    }

    private static void addFuelTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (CONFIG.iconTooltips.fuelTooltip) {
            var level = Minecraft.getInstance().level;
            if (level != null &&
                    //? if >1.21.8 {
                    level.fuelValues()
                            //?} else {
                            /*AbstractFurnaceBlockEntity
                             *///?}
                            .isFuel(itemStack)) {
                addIcon(ResourceLocation.withDefaultNamespace("fuel"),
                        //? if >1.21.8 {
                        level.fuelValues().burnDuration(itemStack)
                                //?} else {
                                /*AbstractFurnaceBlockEntity.getFuel().get(itemStack.getItem())
                                 *///?}
                                /200f, list, component);
            }
        }
    }

    private static void addHoneyTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (CONFIG.iconTooltips.honeyTooltip && itemStack.has(DataComponents.BLOCK_STATE)) {
            var state = itemStack.get(DataComponents.BLOCK_STATE);
            if (state == null) return;
            var honey = state.get(BeehiveBlock.HONEY_LEVEL);
            if (honey == null) return;
            addIcon(ResourceLocation.withDefaultNamespace("honey"), honey, list, component);
        }
    }

    private static void addIcon(ResourceLocation attribute, double amount, List<Component> list, MutableComponent component) {
        //? if >1.21.8 {
        ResourceLocation icon = ResourceLocation.fromNamespaceAndPath(attribute.getNamespace(), "inline_tooltip_icons/"+ attribute.getPath());
        MutableComponent iconComponent = Component.object(new AtlasSprite(AtlasSprite.DEFAULT_ATLAS, icon));
        //?} else {
        /*ResourceLocation icon = ResourceLocation.fromNamespaceAndPath(attribute.getNamespace(), "textures/inline_tooltip_icons/%s.png".formatted(attribute.getPath().replace("generic.", "")));
        var style = InlineStyle.fromInlineData(new SpriteInlineData(new TextureSprite(icon)));;
        MutableComponent iconComponent = Component.literal(".").setStyle(style);
        *///?}
        iconComponent.append(ModHelpers.format(amount) + " ");
        if (ModHelpers.hasAltDown() && InlineTooltips.CONFIG.developerOptions.debugInfo) {
            iconComponent.append(Component.literal(" (%s)".formatted(attribute)));
            list.add(iconComponent);
        } else {
            component.append(iconComponent);
        }
    }

    public static void init() {

    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }
}