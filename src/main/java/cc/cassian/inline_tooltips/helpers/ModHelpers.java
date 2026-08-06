package cc.cassian.inline_tooltips.helpers;

import cc.cassian.inline_tooltips.InlineTooltips;
import cc.cassian.inline_tooltips.compat.ModCompat;
//? fabric {
/*import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
*///?}
import com.mojang.serialization.DataResult;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.TextColor;
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
                /*ClientTags.isInWithLocalFallback(SHOWS_PERCENTAGE, attributeHolder)
            *///?} else {
                attributeHolder.is(SHOWS_PERCENTAGE)
            //?}
            || (ModCompat.REARM && attributeHolder == Attributes.KNOCKBACK_RESISTANCE)
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
    public static int getColour(String colour, ChatFormatting fallback) {
        DataResult<TextColor> parsedcolor = TextColor.parseColor(colour);
        if (parsedcolor.isSuccess()) {
            return parsedcolor.getOrThrow().getValue();
        }
        return Objects.requireNonNull(TextColor.fromLegacyFormat(fallback)).getValue();

    }
}
