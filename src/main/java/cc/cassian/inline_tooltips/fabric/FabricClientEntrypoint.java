package cc.cassian.inline_tooltips.fabric;

//? fabric {
import cc.cassian.inline_tooltips.InlineTooltips;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        InlineTooltips.init();
        ItemTooltipCallback.EVENT.register(InlineTooltips::addTooltips);
    }

}
//?}