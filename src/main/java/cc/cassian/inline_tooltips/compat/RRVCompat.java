package cc.cassian.inline_tooltips.compat;

//? if >26.2 {
/*import cc.cassian.inline_tooltips.mixin.BurningClientRecipeAccessor;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.client.recipe.ClientRecipeCache;
import cc.cassian.rrv.common.builtin.burning.BurningClientRecipeType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Optional;
*///?}

public class RRVCompat {
	//? if >26.2 {
	/*static HashMap<Item, Float> FUEL_VALUES =  new HashMap();


	public static float getFuelValue(ItemStack itemStack) {
	 	return FUEL_VALUES.getOrDefault(itemStack.getItem(), 0f);
	}

	public static boolean isFuel(ItemStack itemStack) {
		if (FUEL_VALUES.containsKey(itemStack.getItem())) {
			return FUEL_VALUES.get(itemStack.getItem())>0;
		}
		Optional<ReliableClientRecipe> recipe = ClientRecipeCache.INSTANCE.getRecipes().stream().filter(p -> {
			boolean b = p.getType().equals(BurningClientRecipeType.INSTANCE) && p.getIngredients().stream().anyMatch(content -> content.contains(itemStack.getItem()));
			return b;
		}).findFirst();
		if (recipe.isPresent()) {
			 FUEL_VALUES.put(itemStack.getItem(), ((BurningClientRecipeAccessor) recipe.get()).getBurnTime());
		} else {
			FUEL_VALUES.put(itemStack.getItem(), 0f);
		}
		return recipe.isPresent();
	}
	*///?}
}
