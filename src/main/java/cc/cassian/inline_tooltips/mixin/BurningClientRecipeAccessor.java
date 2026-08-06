package cc.cassian.inline_tooltips.mixin;

import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@org.spongepowered.asm.mixin.Mixin(targets = "cc.cassian.rrv.common.builtin.burning.BurningClientRecipe")
public interface BurningClientRecipeAccessor {
	@Accessor("burnTime")
	float getBurnTime();
}
