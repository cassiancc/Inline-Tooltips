package cc.cassian.inline_tooltips;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {

    public IconTooltips iconTooltips = new IconTooltips();
    public static class IconTooltips implements Section {
        @Comment("Add tooltips based on fuel levels.")
        public boolean fuelTooltip = true;
        @Comment("Add tooltips based on amount of bees.")
        public boolean beesTooltip = true;
        @Comment("Add tooltips based on item attributes.")
        public boolean attributeTooltips = true;
        @Comment("Add tooltips based on amount of honey.")
        public boolean honeyTooltip = true;
    }

    public TextTooltips textTooltips = new TextTooltips();
    public static class TextTooltips implements Section {
        @Comment("Add tooltips to Compasses with the player's coordinates.")
        public boolean compassTooltip = true;
        @Comment("Add tooltips based on Lodestone Compass's destination.")
        public boolean lodestoneTooltip = true;
    }

    public DeveloperOptions developerOptions = new DeveloperOptions();
    public static class DeveloperOptions implements Section {
        @Comment("Show debug information when holding Alt.")
        public boolean debugInfo = false;
    }




}
