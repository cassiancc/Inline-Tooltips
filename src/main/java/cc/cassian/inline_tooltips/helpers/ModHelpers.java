package cc.cassian.inline_tooltips.helpers;

import cc.cassian.inline_tooltips.InlineTooltips;
import cc.cassian.inline_tooltips.compat.ModCompat;
//? fabric {
import cc.cassian.inline_tooltips.config.ModConfig;
import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
//?}
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.Objects;

import static cc.cassian.inline_tooltips.InlineTooltips.CONFIG;

public class ModHelpers {

    public static TagKey<Attribute> SHOWS_PERCENTAGE = TagKey.create(Registries.ATTRIBUTE, InlineTooltips.id("shows_percentage"));

    public static String format(double amount) {
        return ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(amount);
    }

    public static String format(double amount, Holder<Attribute> attributeHolder) {
        if (!CONFIG.general.checkPercentageTag) return ModHelpers.format(amount);
        if (
            //? if fabric {
                ClientTags.isInWithLocalFallback(SHOWS_PERCENTAGE, attributeHolder)
            //?} else {
                /*attributeHolder.is(SHOWS_PERCENTAGE)
            *///?}
            || (ModCompat.REARM && attributeHolder.value() == Attributes.KNOCKBACK_RESISTANCE)
        ) {
            return ModHelpers.format(amount * 100) + "%";
        }
        return ModHelpers.format(amount);
    }

    public static boolean hasShiftDown() {
        if (CONFIG.general.neverExpanded) return false;
        if (CONFIG.general.alwaysExpanded) return true;
        //? if >1.21.8 {
        return Minecraft.getInstance().hasShiftDown();
        //?} else {
        /*return Screen.hasShiftDown();
        *///?}
    }

    public static boolean hasAltDown() {
        //? if >1.21.8 {
        return Minecraft.getInstance().hasAltDown();
        //?} else {
        /*return Screen.hasAltDown();
        *///?}
    }

    /**
     * Used to check what colour the text should be.
     * Adapted from Item Descriptions.
     */
    public static ChatFormatting getColour(String colour, ChatFormatting fallback) {
        String replacedColour = colour.toLowerCase().replace(" ", "_");
        return switch (replacedColour) {
            case "black", "dark_blue", "dark_green", "dark_red", "dark_purple",
                 "blue", "green", "aqua", "red", "yellow", "white" ->
                    Objects.requireNonNullElse(ChatFormatting.getByName(colour), ChatFormatting.GRAY);
            case "pink", "light_purple" ->
                    Objects.requireNonNullElse(ChatFormatting.getByName("light_purple"), ChatFormatting.GRAY);
            case "dark_gray", "dark_grey" ->
                    Objects.requireNonNullElse(ChatFormatting.getByName("dark_gray"), ChatFormatting.GRAY);
            case "cyan", "dark_aqua" ->
                    Objects.requireNonNullElse(ChatFormatting.getByName("dark_aqua"), ChatFormatting.GRAY);
            case "orange", "gold", "dark_yellow" ->
                    Objects.requireNonNullElse(ChatFormatting.getByName("gold"), ChatFormatting.GRAY);
            default -> fallback;
        };
    }
}
