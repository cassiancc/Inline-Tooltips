package cc.cassian.inline_tooltips.neoforge;

//? neoforge {
/*import cc.cassian.inline_tooltips.InlineTooltips;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static cc.cassian.inline_tooltips.InlineTooltips.CONFIG;

@Mod(value = InlineTooltips.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber // sample_content
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        InlineTooltips.init();
    }

    @SubscribeEvent
    public static void onPlayerDamage(ItemTooltipEvent event) {
        InlineTooltips.addTooltips(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
    }

    @SubscribeEvent
    public static void onPlayerDamage(GatherSkippedAttributeTooltipsEvent event) {
        if (!Minecraft.getInstance().hasShiftDown() && CONFIG.iconTooltips.attributeTooltips) {
            event.setSkipAll(true);
        }
    }

}
*///?}