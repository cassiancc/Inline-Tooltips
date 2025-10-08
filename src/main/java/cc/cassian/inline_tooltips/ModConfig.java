package cc.cassian.inline_tooltips;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
    @Comment("Add tooltips based on fuel levels.")
    public boolean fuelTooltip = true;
    @Comment("Add tooltips based on amount of bees.")
    public boolean beesTooltip = true;
    @Comment("Add tooltips based on item attributes.")
    public boolean attributeTooltips = true;
    @Comment("Add tooltips based on amount of honey.")
    public boolean honeyTooltip = true;
    @Comment("Add tooltips based on Lodestone Compass's destination.")
    public boolean lodestoneTooltip = true;
    @Comment("Show debug information when holding Alt.")
    public boolean debugInfo = false;
}
