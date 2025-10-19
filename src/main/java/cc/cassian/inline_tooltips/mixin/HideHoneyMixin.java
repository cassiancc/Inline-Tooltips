package cc.cassian.inline_tooltips.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.component.BlockItemStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

import static cc.cassian.inline_tooltips.InlineTooltips.CONFIG;


@Mixin(BlockItemStateProperties.class)
public class HideHoneyMixin {
    //? if >1.21.8 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"), method = "addToTooltip")
	private static <T> void init(Consumer instance, T t, Operation<Void> original) {
        // Disable default tooltip
        if (!CONFIG.iconTooltips.honeyTooltip) {
            original.call(instance, t);
        }
    }
    //?}
}