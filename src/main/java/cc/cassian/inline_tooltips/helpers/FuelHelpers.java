package cc.cassian.inline_tooltips.helpers;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
//? if <26.1
//import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
//? neoforge
//import net.neoforged.neoforge.event.EventHooks;
//? if >26.2 {
/*import cc.cassian.inline_tooltips.compat.ModCompat;
import cc.cassian.inline_tooltips.compat.RRVCompat;
*///?}

public class FuelHelpers {
	public static boolean isFuel(ItemStack itemStack, ClientLevel level) {
		//? if >26.2 {
		/*if (ModCompat.RELIABLE_RECIPE_VIEWER) {
			return RRVCompat.isFuel(itemStack);
		}
		return false;
		*///?} else if >1.21.8 {
		return level.fuelValues().isFuel(itemStack);
		//?} else {
		/*return getFuelValue(itemStack, level)>0;
		 *///?}
	}

	public static double getFuelValue(ItemStack itemStack, Level level) {
		int value = 0;
		//? if >26.2 {
		/*if (ModCompat.RELIABLE_RECIPE_VIEWER) {
			return RRVCompat.getFuelValue(itemStack);
		}
		*///?} else if >1.21.8 && fabric {
		value = level.fuelValues().burnDuration(itemStack);
		 //?} else if fabric {
		/*value = AbstractFurnaceBlockEntity.getFuel().getOrDefault(itemStack.getItem(), value);
		 *///?} else {
            /*value = EventHooks.getItemBurnTime(itemStack, itemStack.getItem().getBurnTime(itemStack, null
					//? if >1.21.8
					, level.fuelValues()
					), null
            //? if >1.21.8
            , level.fuelValues()
            );
            *///?}
		return value;
	}
}
