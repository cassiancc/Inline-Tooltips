package cc.cassian.inline_tooltips.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import org.spongepowered.asm.mixin.Mixin;

import static cc.cassian.inline_tooltips.InlineTooltips.CONFIG;

//? if neoforge {
/*@Mixin(AttributeUtil.class)
*///?} else {
@Mixin(ItemStack.class)
//?}
public class NeoForgeHideAttributesMixin {
    //? if neoforge {

    /*@WrapMethod(method = "lambda$applyModifierTooltips$1")
    private static boolean applyModifiers(GatherSkippedAttributeTooltipsEvent event, AttributeModifier m, Operation<Boolean> original) {
        // Disable default tooltip
        if (Minecraft.getInstance().hasShiftDown() || !CONFIG.iconTooltips.attributeTooltips) {
            return original.call(event, m);
        }
        return false;
    }
    *///?}

}