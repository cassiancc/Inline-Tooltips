package cc.cassian.inline_tooltips;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.jetbrains.annotations.Nullable;

public class ModHelpers {
    public static String format(double amount) {
        String format = String.format("%1.1f", amount);
        if (format.endsWith(".0")) {
            return String.valueOf(Math.round(amount));
        }
        return format;
    }

}
