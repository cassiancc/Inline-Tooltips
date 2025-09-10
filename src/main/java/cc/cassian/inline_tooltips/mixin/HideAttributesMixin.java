package cc.cassian.inline_tooltips.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;


@Mixin(ItemStack.class)
public class HideAttributesMixin {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/mutable/MutableBoolean;isTrue()Z"), method = "method_57370")
	private static boolean init(MutableBoolean instance, Operation<Boolean> original, @Local Consumer<Component> consumer, @Local AttributeModifier attributeModifier) {
        // Disable default tooltip
        if (Minecraft.getInstance().hasShiftDown()) {
            return original.call(instance);
        }
        return false;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers$Display;apply(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V"), method = "method_57370")
    private static void init(ItemAttributeModifiers.Display instance, Consumer<Component> consumer, @Nullable Player player, Holder<Attribute> attributeHolder, AttributeModifier attributeModifier, Operation<Void> original) {
        // Enable our tooltip
        if (Minecraft.getInstance().hasShiftDown()) {
            original.call(instance, consumer, player, attributeHolder, attributeModifier);
        }
    }

}