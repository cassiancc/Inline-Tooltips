package cc.cassian.inline_tooltips.mixin;

import cc.cassian.inline_tooltips.helpers.ModHelpers;
import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
//? if >1.21
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

import static cc.cassian.inline_tooltips.InlineTooltips.CONFIG;


@Mixin(ItemStack.class)
public class HideAttributesMixin {
    //? if fabric {
    //? if >1.21 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/mutable/MutableBoolean;isTrue()Z"), method = "method_57370")
	private static boolean init(MutableBoolean instance, Operation<Boolean> original, @Local Consumer<Component> consumer, @Local AttributeModifier attributeModifier) {
        // Disable default tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            return original.call(instance);
        }
        return false;
    }
    //?} else {
    /*@WrapOperation(at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;isEmpty()Z"), method = "getTooltipLines")
    private static boolean init(Multimap instance, Operation<Boolean> original) {
        // Disable default tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            return original.call(instance);
        }
        return true;
    }
    *///?}

    //? if >1.21.8 {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers$Display;apply(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V"), method = "method_57370")
    private static void init(ItemAttributeModifiers.Display instance, Consumer<Component> consumer, Player player, Holder<Attribute> attributeHolder, AttributeModifier attributeModifier, Operation<Void> original) {
        // Enable our tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            original.call(instance, consumer, player, attributeHolder, attributeModifier);
        }
    }
    //?} else if >1.21 {
    /*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V"), method = "method_57370")
    private static void removeAttribute(ItemStack instance, Consumer consumer, Player player, Holder holder, AttributeModifier attributeModifier, Operation<Void> original) {
        // Enable our tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            original.call(instance, consumer, player, holder, attributeModifier);
        }
    }
    *///?}
    //FIXME 1.20.1 CASE


    //?} else if neoforge && >1.21.8 {
    /*@WrapOperation(at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/mutable/MutableBoolean;isTrue()Z"), method = "lambda$addAttributeTooltips$19")
    private static boolean init(MutableBoolean instance, Operation<Boolean> original) {
        // Disable default tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            original.call(instance);
        }
        return false;
    }


    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers$Display;apply(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V"), method = "lambda$addAttributeTooltips$19")
    private static void init(ItemAttributeModifiers.Display instance, Consumer<Component> consumer, Player player, Holder<Attribute> attributeHolder, AttributeModifier attributeModifier, Operation<Void> original) {
        // Enable our tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            original.call(instance, consumer, player, attributeHolder, attributeModifier);
        }
    }
    *///?} else {
    /*@WrapOperation(at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/mutable/MutableBoolean;isTrue()Z"), method = "lambda$addAttributeTooltips$21")
    private static boolean init(MutableBoolean instance, Operation<Boolean> original) {
        // Disable default tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            original.call(instance);
        }
        return false;
    }


    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V"), method = "lambda$addAttributeTooltips$21")
    private static void init(ItemStack instance, Consumer consumer, Player player, Holder holder, AttributeModifier attributeModifier, Operation<Void> original) {
        // Enable our tooltip
        if (!CONFIG.iconTooltips.attributeTooltips) {
            original.call(instance, consumer, player, holder, attributeModifier);
        }
    }
    *///?}

}