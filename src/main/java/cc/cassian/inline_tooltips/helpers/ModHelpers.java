package cc.cassian.inline_tooltips.helpers;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.Objects;

public class ModHelpers {
    public static String format(double amount) {
        String format = String.format("%1.1f", amount);
        if (format.endsWith(".0")) {
            return String.valueOf(Math.round(amount));
        }
        return format;
    }

    public static boolean hasShiftDown() {
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
