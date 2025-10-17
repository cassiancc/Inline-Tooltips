/*
Based on code from Sharper Tooltips
MIT License

Copyright (c) 2025 SylentHuntress

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package cc.cassian.inline_tooltips.helpers;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.jetbrains.annotations.Nullable;

public class SharpnessHelpers {
    public static double addSharpnessDamage(ItemStack stack, double original, @Nullable Player player, AttributeModifier modifier) {
        var enchantments = stack.getEnchantments();
        if (enchantments.isEmpty()) {
            return original;
        }

        if (modifier.is(Item.BASE_ATTACK_DAMAGE_ID)) {
            for (var registryEntry : enchantments.entrySet()) {
                var enchantment = registryEntry.getKey().value();

                for (var effectEntry : enchantment.getEffects(EnchantmentEffectComponents.DAMAGE)) {
                    if (effectEntry.requirements().isPresent()) {
                        continue;
                    }

                    original = effectEntry.effect().process(
                            enchantments.getLevel(registryEntry.getKey()),
                            player != null
                                    ? player.getRandom()
                                    : null,
                            (float) original
                    );
                }
            }
        }

        return original;
    }
}
