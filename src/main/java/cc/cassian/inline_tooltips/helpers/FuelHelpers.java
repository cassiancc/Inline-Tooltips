package cc.cassian.inline_tooltips.helpers;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
//? if <26.1
//import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
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
		/*return AbstractFurnaceBlockEntity.isFuel(itemStack);
		 *///?}
	}

	public static double getFuelValue(Level level, ItemStack itemStack) {
		//? if >26.2 {
		/*if (ModCompat.RELIABLE_RECIPE_VIEWER) {
			return RRVCompat.getFuelValue(itemStack);
		}
		int value = 0;
		*///?} else if >1.21.8 && fabric {
		/*int value = level.fuelValues().burnDuration(itemStack)
		 *///?} else if fabric {
		/*Integer value = AbstractFurnaceBlockEntity.getFuel().get(itemStack.getItem());
		 *///?} else {
            int value = itemStack.getBurnTime(null
            //? if >1.21.8
            , level.fuelValues()
            )
            //?}
		;
		//? if =1.21.1 && fabric {
        /*if (value != null) {
            return value;
        } else return 0;
        *///?} else
		return value;
	}
}
