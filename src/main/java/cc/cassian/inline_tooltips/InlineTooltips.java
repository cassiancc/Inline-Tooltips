package cc.cassian.inline_tooltips;

import cc.cassian.inline_tooltips.compat.ModCompat;
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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.packs.resources.Resource;
//? if >26 {
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
//?} else {
/*import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
*///?}
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class InlineTooltips {
	public static final String MOD_ID = "inline_tooltips";
    public static final ModConfig CONFIG = ModConfig.createToml(Platform.INSTANCE.getConfigDir(),"", MOD_ID, ModConfig.class);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Identifier UNDEFINED = id(MOD_ID, "inline_tooltip_icons/empty");


    public static void addTooltips(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> list) {
        var player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        if (player == null || level == null) return;
        var inInventory = player.getInventory().contains(itemStack) || player.isCreative();
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
        if (CONFIG.textTooltips.lodestoneTooltip && itemStack.has(DataComponents.LODESTONE_TRACKER) && inInventory) {
            var state = itemStack.get(DataComponents.LODESTONE_TRACKER);
            if (state == null || state.target().isEmpty()) return;
            var pos = state.target().get();
            addCoordinates(pos, list, "target", ModHelpers.getColour(CONFIG.textTooltips.lodestoneCompassTooltipColor, ChatFormatting.GOLD));
        }
        if (CONFIG.textTooltips.recoveryCompassTooltip && itemStack.is(Items.RECOVERY_COMPASS) && inInventory) {
            var lastDeath = player.getLastDeathLocation();
            if (lastDeath.isEmpty()) return;
            addCoordinates(lastDeath.get(), list, "target", ModHelpers.getColour(CONFIG.textTooltips.recoveryCompassTooltipColor, ChatFormatting.AQUA));
        }
        if (CONFIG.textTooltips.compassTooltip && itemStack.is(Items.COMPASS) && !itemStack.has(DataComponents.LODESTONE_TRACKER) && inInventory) {
            var pos = player.blockPosition();
            addCoordinates(pos, list, "position", ModHelpers.getColour(CONFIG.textTooltips.compassTooltipColor, ChatFormatting.RED));
        }
        if (CONFIG.durabilityTooltip.enable && !tooltipFlag.isAdvanced() && (CONFIG.durabilityTooltip.always_show || itemStack.isDamaged()) && itemStack.isDamageableItem() && (itemStack.has(DataComponents.DAMAGE) || CONFIG.durabilityTooltip.always_show)
        ) {
            list.add(Component.translatable("item.durability", itemStack.getMaxDamage() - itemStack.getDamageValue(), itemStack.getMaxDamage()).withColor(ModHelpers.getColour(CONFIG.durabilityTooltip.text_color, ChatFormatting.GRAY)));
        }
        if ((CONFIG.clockTooltip.current_time || CONFIG.clockTooltip.day_count) && itemStack.is(Items.CLOCK) && inInventory) {
            //? if <26
            //float dayTime = level.getDayTime();
            //? if >26
            float dayTime = level.clockManager().getTotalTicks(level.registryAccess().getOrThrow(WorldClocks.OVERWORLD));
            list.add(Component.literal(getTime(dayTime)).withColor(ModHelpers.getColour(CONFIG.clockTooltip.text_color, ChatFormatting.GOLD)));
        }
    }

    private static void addCoordinates(GlobalPos globalPos, List<Component> list, String target, int colour) {
        addCoordinates(globalPos.pos(), list, target, colour);
        list.add(
                Component.translatable("gui.inline_tooltips.dimension").withStyle(ChatFormatting.GRAY).append(
                Component.translatableWithFallback(globalPos.dimension()
                        //? if >1.21.10 {
                        .identifier()
                        //?} else {
                        /*.location()
                        *///?}
                        .toLanguageKey("dimension"), WordUtils.capitalizeFully(globalPos.dimension()
                        //? if >1.21.10 {
                        .identifier()
                        //?} else {
                        /*.location()
                        *///?}
                        .getPath())).withColor(colour))
        );
    }

    private static void addCoordinates(BlockPos pos, List<Component> list, String target, int colour) {
        list.add(
                Component.translatable("gui.inline_tooltips."+target).withStyle(ChatFormatting.GRAY).append(
                Component.literal("X: %d, Y: %d, Z: %d".formatted(pos.getX(), pos.getY(), pos.getZ())).withColor(colour))
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
            for (EquipmentSlotGroup equipmentSlotGroup : EquipmentSlotGroup.values()) {
                itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier
                    //? if >1.21.8 {
                    , display
                    //?}
                ) -> {
                    //? if >1.21.8
                    if (display != ItemAttributeModifiers.Display.hidden()) {
                        var player = Minecraft.getInstance().player;
                        var attributeModifierAmount = attributeModifier.
                        amount();

                        AtomicReference<Double> amount = new AtomicReference<>(attributeModifierAmount);
                        if (player != null && player.getAttributes().hasAttribute(holder)) {
                            amount.set(switch (attributeModifier.operation()) {
                                case ADD_VALUE -> attributeModifierAmount + player.getAttributeBaseValue(holder);
                                case ADD_MULTIPLIED_BASE, ADD_MULTIPLIED_TOTAL -> attributeModifierAmount * player.getAttributeBaseValue(holder);
                            });
                        }
                        amount.set(SharpnessHelpers.addSharpnessDamage(itemStack, amount.get(), player, attributeModifier));
                        var icon = holder.unwrapKey().orElseThrow()
                                //? if >1.21.10 {
                                .identifier()
                                //?} else {
                                /*.location()
                                *///?}
                                ;
                        if (amount.get()!=0)
                            addIcon(icon, ModHelpers.format(amount.get(), holder), list, component, Component.translatable("item.modifiers."+equipmentSlotGroup.name().toLowerCase(Locale.ROOT)), ModHelpers.getColour(CONFIG.iconTooltips.attributeTooltipColor, ChatFormatting.DARK_GREEN));
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
            addIcon(id("bees"), bees
                    //? if >1.21.8
                    .bees()
                    .size(), list, component, null, ModHelpers.getColour(CONFIG.iconTooltips.beeTooltipColor, ChatFormatting.GOLD));
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
        /*if (value != null) {
            return value;
        } else return 0;
        *///?} else
        return value;
    }

    private static void addHoneyTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (CONFIG.iconTooltips.honeyTooltip && itemStack.has(DataComponents.BLOCK_STATE)) {
            var state = itemStack.get(DataComponents.BLOCK_STATE);
            if (state == null) return;
            var honey = state.get(BeehiveBlock.HONEY_LEVEL);
            if (honey == null) return;
            addIcon(id("honey"), honey, list, component, null, ModHelpers.getColour(CONFIG.iconTooltips.honeyTooltipColor, ChatFormatting.GOLD));
        }
    }

    private static void addLightLevelTooltips(ItemStack itemStack, List<Component> list, MutableComponent component) {
        if (CONFIG.iconTooltips.lightLevelTooltip) {
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                if (blockItem.getBlock() == Blocks.LIGHT) {
                    var stateComponent =  itemStack.get(DataComponents.BLOCK_STATE);
                    Integer light = stateComponent != null ? stateComponent.get(LightBlock.LEVEL) : null;
                    if (light != null && light != 0)
                        addIcon(id("light"), light, list, component, Component.translatable("item.modifiers.placed"), ModHelpers.getColour(CONFIG.iconTooltips.lightLevelTooltipColor, ChatFormatting.GOLD));
                } else {
                    var state = blockItem.getBlock().defaultBlockState();
                    int light = state.getLightEmission();
                    if (light != 0)
                        addIcon(id("light"), light, list, component, Component.translatable("item.modifiers.placed"), ModHelpers.getColour(CONFIG.iconTooltips.lightLevelTooltipColor, ChatFormatting.GOLD));
                }
            }
        }
    }

    private static void addIcon(Identifier attribute, double amount, List<Component> list, MutableComponent component, MutableComponent usedText, int attributeColor) {
        addIcon(attribute, ModHelpers.format(amount), list, component, usedText, attributeColor);
    }

    private static void addIcon(Identifier attribute, String amount, List<Component> list, MutableComponent component, MutableComponent usedText, int attributeColor) {
        if (ModHelpers.hasShiftDown() && usedText != null && !list.contains(usedText.withStyle(ChatFormatting.GRAY))) {
            list.add(Component.empty());
            list.add(usedText.withStyle(ChatFormatting.GRAY));
        }


        //? if >1.21.8 {
        Identifier icon = id(attribute.getNamespace(), "inline_tooltip_icons/"+ attribute.getPath());
        Optional<Resource> resource = Minecraft.getInstance().getResourceManager().getResource(id(attribute.getNamespace(), "textures/inline_tooltip_icons/%s.png".formatted(attribute.getPath())));
        MutableComponent iconComponent;
        if (resource.isPresent()) {
			iconComponent = Component.object(new AtlasSprite(AtlasSprite.DEFAULT_ATLAS, icon));
		} else {
			iconComponent = Component.empty();
		}
        //?} else {
        /*Identifier icon = id(attribute.getNamespace(), "textures/inline_tooltip_icons/%s.png".formatted(attribute.getPath().replace("generic.", "").replace("zombie.", "")));
        var style = InlineStyle.fromInlineData(new SpriteInlineData(new TextureSprite(icon)));
        Optional<Resource> resource = Minecraft.getInstance().getResourceManager().getResource(icon);
        MutableComponent iconComponent = Component.empty();
        if (resource.isPresent()) {
            iconComponent.append(Component.literal(".").setStyle(style));
        }
        *///?}

        String spacing = new String(new char[CONFIG.general.spacing]).replace("\0", " ");
        String expandedSpacing = new String(new char[CONFIG.general.expandedSpacing]).replace("\0", " ");

        if (ModHelpers.hasAltDown() && InlineTooltips.CONFIG.developerOptions.debugInfo) {
            iconComponent.append(amount + " ");
            iconComponent.append(Component.literal(" (%s | %s)".formatted(attribute, icon)));
            list.add(iconComponent);
        } else if (ModHelpers.hasShiftDown()) {
            iconComponent.append(expandedSpacing);
            var key = attribute.toLanguageKey("tooltip")
                    //? if =1.21.1
                    //.replace("generic.", "").replace("zombie.", "")
                    ;
            if (Language.getInstance().has(key)) {
                iconComponent.append(Component.translatable(key, amount).withColor(attributeColor));
            } else if (!InlineTooltips.CONFIG.developerOptions.debugInfo) {
                iconComponent.append(Component.literal("%s %s".formatted(amount, WordUtils.capitalizeFully(attribute.getPath().replace("_", " ")))).withColor(attributeColor));
            } else {
                iconComponent.append(Component.literal("%s %s".formatted(amount, key)).withColor(attributeColor));
            }
            list.add(iconComponent);
        } else if (resource.isPresent()) {
            iconComponent.append(amount + spacing);
            component.append(iconComponent);
        }
    }

    public static void init() {

    }

    public static Identifier id(String id) {
        return id(MOD_ID, id);
    }

    public static Identifier id(String namespace, String id) {
        return Identifier.fromNamespaceAndPath(namespace, id);
    }
}