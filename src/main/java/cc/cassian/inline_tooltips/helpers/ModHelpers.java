package cc.cassian.inline_tooltips.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

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

}
