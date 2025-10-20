package cc.cassian.inline_tooltips;

import cc.cassian.inline_tooltips.compat.ModCompat;
import cc.cassian.inline_tooltips.config.ModConfig;
import cc.cassian.inline_tooltips.helpers.ModHelpers;
import cc.cassian.inline_tooltips.helpers.SharpnessHelpers;
//? if =1.21.1 || 1.20.1 {
/*import com.samsthenerd.inline.api.InlineAPI;
import com.samsthenerd.inline.api.InlineData;
import com.samsthenerd.inline.api.data.SpriteInlineData;
import com.samsthenerd.inline.impl.InlineStyle;
import com.samsthenerd.inline.utils.TextureSprite;
*///?}
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
//? if >1.21.8
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.resources.ResourceLocation;
//? if >1.21 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.component.ItemAttributeModifiers;
//?}

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
//? if <1.21.8 {
/*import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
*///?}
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class InlineTooltips {
	public static final String MOD_ID = "inline_tooltips";
    public static final ModConfig CONFIG = ModConfig.createToml(Platform.INSTANCE.getConfigDir(),"", MOD_ID, ModConfig.class);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    public static void addTooltips(ItemStack itemStack,
                                   //? if >1.21
                                   Item.TooltipContext tooltipContext,
                                   TooltipFlag tooltipFlag, List<Component> list) {
        var player = Minecraft.getInstance().player;
        // Add icon tooltips
        var component = Component.empty();
        // Attribute Modifiers
        addAttributeTooltips(itemStack, list, component);
        addBeeTooltips(itemStack, list, component);
        addFoodTooltips(itemStack, list, component);
        addFuelTooltips(itemStack, list, component);
        addHoneyTooltips(itemStack, list, component);
        addLightLevelTooltips(itemStack, list, component);
        // Add icon
        if (!component.equals(Component.empty()))
            list.add(component);
        // Add text tooltips
        if (CONFIG.textTooltips.lodestoneTooltip &&
                //? if >1.21 {
                 itemStack.has(DataComponents.LODESTONE_TRACKER)
                //?} else {
                /*CompassItem.isLodestoneCompass(itemStack)
                *///?}
        ) {
            //? if >1.21 {
            var state = itemStack.get(DataComponents.LODESTONE_TRACKER);
            if (state == null || state.target().isEmpty()) return;
            var pos = state.target().get();
            //?} else {
            /*var pos = CompassItem.getLodestonePosition(itemStack.getTag());
            *///?}
            addCoordinates(pos, list, "target", ModHelpers.getColour(CONFIG.textTooltips.lodestoneCompassTooltipColor, ChatFormatting.GOLD));
        }
        if (CONFIG.textTooltips.recoveryCompassTooltip && itemStack.is(Items.RECOVERY_COMPASS) && player != null) {
            var lastDeath = player.getLastDeathLocation();
            if (lastDeath.isEmpty()) return;
            addCoordinates(lastDeath.get(), list, "target", ModHelpers.getColour(CONFIG.textTooltips.recoveryCompassTooltipColor, ChatFormatting.AQUA));
        }
        if (CONFIG.textTooltips.compassTooltip && itemStack.is(Items.COMPASS) && !
                //? if >1.21 {
                itemStack.has(DataComponents.LODESTONE_TRACKER)
                //?} else {
                /*CompassItem.isLodestoneCompass(itemStack)
                *///?}
                && player != null) {
            var pos = player.blockPosition();
            addCoordinates(pos, list, "position", ModHelpers.getColour(CONFIG.textTooltips.compassTooltipColor, ChatFormatting.RED));
        }
        if (CONFIG.textTooltips.durabilityTooltip && !tooltipFlag.isAdvanced() && itemStack.isDamaged()
                //? if >1.21
                && itemStack.has(DataComponents.DAMAGE)
        ) {
            list.add(Component.translatable("item.durability", itemStack.getMaxDamage() - itemStack.getDamageValue(), itemStack.getMaxDamage()).withStyle(ModHelpers.getColour(CONFIG.textTooltips.durabilityTooltipColor, ChatFormatting.GRAY)));
        }
        if ((CONFIG.clockTooltip.current_time || CONFIG.clockTooltip.day_count) && itemStack.is(Items.CLOCK) && player != null) {
            list.add(Component.literal(getTime(Minecraft.getInstance().level.getDayTime())).withStyle(ModHelpers.getColour(CONFIG.clockTooltip.text_color, ChatFormatting.GOLD)));
        }
    }

    private static void addCoordinates(GlobalPos globalPos, List<Component> list, String target, ChatFormatting colour) {
        addCoordinates(globalPos.pos(), list, target, colour);
        list.add(
                Component.translatable("gui.inline_tooltips.dimension").withStyle(ChatFormatting.GRAY).append(
                Component.translatableWithFallback(globalPos.dimension().location().toLanguageKey("dimension"), WordUtils.capitalizeFully(globalPos.dimension().location().getPath())).withStyle(colour))
        );
    }

    private static void addCoordinates(BlockPos pos, List<Component> list, String target, ChatFormatting colour) {
        list.add(
                Component.translatable("gui.inline_tooltips."+target).withStyle(ChatFormatting.GRAY).append(
                Component.literal("X: %d, Y: %d, Z: %d".formatted(pos.getX(), pos.getY(), pos.getZ())).withStyle(colour))
        );
    }

    // This code was originally authored by MehVadVukaar for Supplementaries.
    // It is adapted here for our clock overlay as authorized by the
    // Supplementaries Team License, as Inline Tooltips is not designed
    // to compete with Supplementaries.
    public static String getTime(float dayTime) {
        StringBuilder currentTime = new StringBuilder();
        if (InlineTooltips.CONFIG.clockTooltip.day_count) {
            int day = (int) (dayTime/24000);
            currentTime.append(I18n.get("gui.c.day", day));
            if (InlineTooltips.CONFIG.clockTooltip.current_time) {
                currentTime.append(", ");
            }
        }
        if (InlineTooltips.CONFIG.clockTooltip.current_time) {
            int time = (int)(dayTime + 6000L) % 24000;
            int m = (int)((float)time % 1000.0F / 1000.0F * 60.0F);
            int hour = time / 1000;
            String a = "";
            if (InlineTooltips.CONFIG.clockTooltip.twenty_four_hour_clock) {
                a = time < 12000 ? " AM" : " PM";
                hour %= 12;
                if (hour == 0) {
                    hour = 12;
                }
            }
            currentTime.append(hour).append(":").append(m < 10 ? "0" : "").append(m).append(a);
        }
        return currentTime.toString();
    }

    private static void addAttributeTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (CONFIG.iconTooltips.attributeTooltips) {
            //? if >1.21 {
            for (EquipmentSlotGroup equipmentSlotGroup : EquipmentSlotGroup.values()) {
            //?} else {
            /*for (EquipmentSlot equipmentSlotGroup : EquipmentSlot.values()) {
            *///?}
                //? if >1.21.8 {
                itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier, display) -> {
                    //?} else if >1.21 {
                    /*itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier) -> {
                                            *///?} else {
                    /*var i = itemStack.getAttributeModifiers(equipmentSlotGroup);
                    i.forEach((holder, attributeModifier) -> {
                     *///?}
                    //? if >1.21.8
                    if (display != ItemAttributeModifiers.Display.hidden()) {
                        var player = Minecraft.getInstance().player;
                        var attributeModifierAmount = attributeModifier.
                        //? if >1.21 {
                        amount();
                         //?} else {
                        /*getAmount();
                        *///?}

                        AtomicReference<Double> amount = new AtomicReference<>(attributeModifierAmount);
                        if (player != null) {
                            amount.set(switch (attributeModifier.
                                    //? if >1.21 {
                                    operation
                                    //?} else {
                                    /*getOperation
                                    *///?}
                                            ()) {
                                case
                                        //? if >1.21 {
                                        ADD_VALUE
                                        //?} else {
                                        /*ADDITION
                                        *///?}
                                        -> attributeModifierAmount + player.getAttributeBaseValue(holder);
                                case
                                    //? if >1.21 {
                                    ADD_MULTIPLIED_BASE, ADD_MULTIPLIED_TOTAL
                                    //?} else {
                                    /*MULTIPLY_BASE, MULTIPLY_TOTAL
                                    *///?}
                                         ->
                                        attributeModifierAmount * player.getAttributeBaseValue(holder);
                            });
                        }
                        amount.set(SharpnessHelpers.addSharpnessDamage(itemStack, amount.get(), player, attributeModifier));
                        //? if >1.21 {
                        var icon = holder.unwrapKey().orElseThrow().location();
                        //?} else {
                        /*var icon = new ResourceLocation(holder.getDescriptionId().replace("attribute.name.", ""));
                        *///?}
                        if (amount.get()!=0)
                            addIcon(icon, amount.get(), list, component, Component.translatable("item.modifiers."+equipmentSlotGroup.name().toLowerCase(Locale.ROOT)), ModHelpers.getColour(CONFIG.iconTooltips.attributeTooltipColor, ChatFormatting.DARK_GREEN));
                    //? if >1.21.8
                    }
                });
            }
        }
    }

    private static void addBeeTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        //? if >1.21 {
        if (itemStack.has(DataComponents.BEES) && CONFIG.iconTooltips.beesTooltip) {
            var bees = itemStack.get(DataComponents.BEES);
            if (bees == null) return;
            addIcon(id("bees"), bees
                    //? if >1.21.8
                    .bees()
                    .size(), list, component, null, ModHelpers.getColour(CONFIG.iconTooltips.beeTooltipColor, ChatFormatting.GOLD));
        }
        //?} else {
        /*CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("BlockEntityTag")) {
            CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");
            if (blockEntityTag.contains("Bees")) {
                ListTag bees = blockEntityTag.getList("Bees", Tag.TAG_COMPOUND);
                addIcon(id("bees"), bees.size(), list, component, null, ModHelpers.getColour(CONFIG.iconTooltips.honeyTooltipColor, ChatFormatting.GOLD));
            }
        }
        *///?}
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
                addIcon(id("fuel"), getFuelValue(level, itemStack) /200f, list, component, Component.translatable("item.modifiers.furnace"), ModHelpers.getColour(CONFIG.iconTooltips.fuelTooltipColor, ChatFormatting.GOLD));
            }
        }
    }

    private static void addFoodTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (itemStack.has(DataComponents.FOOD) && (!ModCompat.APPLE_SKIN || CONFIG.developerOptions.showFoodTooltipWithAppleSkinInstalled) && CONFIG.iconTooltips.foodTooltip) {
            var foodProperties = itemStack.get(DataComponents.FOOD);
            if (foodProperties == null) return;
            if (CONFIG.iconTooltips.foodTooltip)
                addIcon(id("food"), foodProperties.nutrition(), list, component, Component.translatable("item.modifiers.eaten"), ModHelpers.getColour(CONFIG.iconTooltips.foodTooltipColor, ChatFormatting.GOLD));
            if (CONFIG.iconTooltips.saturationTooltip)
                addIcon(id("saturation"), foodProperties.saturation(), list, component, Component.translatable("item.modifiers.eaten"), ModHelpers.getColour(CONFIG.iconTooltips.saturationTooltipColor, ChatFormatting.GOLD));
        }
    }

    private static double getFuelValue(Level level, ItemStack itemStack) {
        //? if >1.21.8 && fabric {
        int value = level.fuelValues().burnDuration(itemStack)
        //?} else if fabric {
        /*Integer value = AbstractFurnaceBlockEntity.getFuel().get(itemStack.getItem());
        *///?} else {
                                /*int value = itemStack.getBurnTime(null
                                //? if >1.21.8
                                , level.fuelValues()
                                )
                                *///?}
        ;
        //? if =1.21.1 && fabric {
        /*if (value != null)
        *///?}
            return value;
        //? if =1.21.1 && fabric {
        /*return 0;
        *///?}
    }

    private static void addHoneyTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        //? if >1.21 {
        if (CONFIG.iconTooltips.honeyTooltip && itemStack.has(DataComponents.BLOCK_STATE)) {
            var state = itemStack.get(DataComponents.BLOCK_STATE);
            if (state == null) return;
            var honey = state.get(BeehiveBlock.HONEY_LEVEL);
            if (honey == null) return;
            addIcon(id("honey"), honey, list, component, null, ModHelpers.getColour(CONFIG.iconTooltips.honeyTooltipColor, ChatFormatting.GOLD));
        }
        //?} else {
        /*CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("BlockStateTag")) {
            CompoundTag blockEntityTag = tag.getCompound("BlockStateTag");
            if (blockEntityTag.contains("honey_level")) {
                var honey = blockEntityTag.getString("honey_level");
                addIcon(id("honey"), Double.parseDouble(honey), list, component, null, ModHelpers.getColour(CONFIG.iconTooltips.honeyTooltipColor, ChatFormatting.GOLD));
            }
        }
        *///?}
    }

    private static void addLightLevelTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (CONFIG.iconTooltips.lightLevelTooltip) {
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                //? if >1.21 {
                if (blockItem.getBlock() == Blocks.LIGHT) {
                    var stateComponent =  itemStack.get(DataComponents.BLOCK_STATE);
                    Integer light = stateComponent != null ? stateComponent.get(LightBlock.LEVEL) : null;
                    if (light != null && light != 0)
                        addIcon(id("light"), light, list, component, Component.translatable("item.modifiers.placed"), ModHelpers.getColour(CONFIG.iconTooltips.lightLevelTooltipColor, ChatFormatting.GOLD));
                } else {
                //?}
                    var state = blockItem.getBlock().defaultBlockState();
                    int light = state.getLightEmission();
                    if (light != 0)
                        addIcon(id("light"), light, list, component, Component.translatable("item.modifiers.placed"), ModHelpers.getColour(CONFIG.iconTooltips.lightLevelTooltipColor, ChatFormatting.GOLD));
                //? if >1.21
                }
            }
        }
    }

    private static void addIcon(ResourceLocation attribute, double amount, List<Component> list, MutableComponent component, MutableComponent usedText, ChatFormatting attributeColor) {
        if (ModHelpers.hasShiftDown() && usedText != null && !list.contains(usedText.withStyle(ChatFormatting.GRAY))) {
            list.add(Component.empty());
            list.add(usedText.withStyle(ChatFormatting.GRAY));
        }

        //? if >1.21.8 {
        ResourceLocation icon = id(attribute.getNamespace(), "inline_tooltip_icons/"+ attribute.getPath());
        MutableComponent iconComponent = Component.object(new AtlasSprite(AtlasSprite.DEFAULT_ATLAS, icon));
        //?} else {
        /*ResourceLocation icon = id(attribute.getNamespace(), "textures/inline_tooltip_icons/%s.png".formatted(attribute.getPath().replace("generic.", "")));
        var style = InlineStyle.fromInlineData(new SpriteInlineData(new TextureSprite(icon)));;
        MutableComponent iconComponent = Component.empty().append(Component.literal(".").setStyle(style));
        *///?}

        if (ModHelpers.hasAltDown() && InlineTooltips.CONFIG.developerOptions.debugInfo) {
            iconComponent.append(ModHelpers.format(amount) + " ");
            iconComponent.append(Component.literal(" (%s)".formatted(attribute)));
            list.add(iconComponent);
        } else if (ModHelpers.hasShiftDown()) {
            var key = attribute.toLanguageKey("tooltip").replace("generic.", "");
            if (I18n.exists(key)) {
                iconComponent.append(Component.translatable(key, ModHelpers.format(amount)).withStyle(attributeColor));
            } else if (!InlineTooltips.CONFIG.developerOptions.debugInfo) {
                iconComponent.append(Component.literal("%s %s".formatted(ModHelpers.format(amount), WordUtils.capitalizeFully(attribute.getPath().replace("_", " ")))).withStyle(attributeColor));
            } else {
                iconComponent.append(Component.literal("%s %s".formatted(ModHelpers.format(amount), key)).withStyle(attributeColor));
            }
            list.add(iconComponent);
        } else {
            iconComponent.append(ModHelpers.format(amount) + " ");
            component.append(iconComponent);
        }
    }

    public static void init() {

    }

    public static ResourceLocation id(String id) {
        return id(MOD_ID, id);
    }

    public static ResourceLocation id(String namespace, String id) {
        //? if >1.21 {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
        //?} else {
        /*return new ResourceLocation(namespace, id);
        *///?}
    }
}