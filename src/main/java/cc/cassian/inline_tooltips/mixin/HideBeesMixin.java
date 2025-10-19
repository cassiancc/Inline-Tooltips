package cc.cassian.inline_tooltips.mixin;

import cc.cassian.inline_tooltips.helpers.ModHelpers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.ItemStack;
//? if >1.21.8
import net.minecraft.world.item.component.Bees;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

import static cc.cassian.inline_tooltips.InlineTooltips.CONFIG;

//? if >1.21.8 {
@Mixin(Bees.class)
//?} else {
/*@Mixin(ItemStack.class)
*///?}
public class HideBeesMixin {
    //? if >1.21.8 {
    @WrapOperation(at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"), method = "addToTooltip")
    private static <T> void init(Consumer instance, T t, Operation<Void> original) {
        // Disable default tooltip
        if (!CONFIG.iconTooltips.beesTooltip) {
            original.call(instance, t);
        }
    }
    //?}
}