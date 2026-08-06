package cc.cassian.inline_tooltips.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.DisplayName;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Matches;

public class ModConfig extends WrappedConfig {

    public General general = new General();
    public static class General implements Section {

        @Comment("Never show full tooltip details, even when holding Shift.")
        public boolean neverExpanded = false;
        @Comment("Always show full tooltip details without holding Shift.")
        public boolean alwaysExpanded = false;
        @Comment("Amount of space characters between icons.")
        public int spacing = 1;
        @Comment("Amount of space characters between icons when the tooltip is expanded.")
        public int expandedSpacing = 1;
        @Comment("Change attribute tooltips in the 'inline_tooltips:shows_percentage' attribute tag to percentages.")
        public boolean checkPercentageTag = true;
    }

    public IconTooltips iconTooltips = new IconTooltips();
    public static class IconTooltips implements Section {
        @Comment("Add tooltips based on item attributes.")
        public boolean attributeTooltips = true;
        @Matches("#[0-9A-Fa-f]{6}")
        public String attributeTooltipColour = "#12aa00";
        @Comment("Add tooltips based on fuel levels.")
        public boolean fuelTooltip = true;
        @Matches("#[0-9A-Fa-f]{6}")
        public String fuelTooltipColour = "#ffaa00";
        @Comment("Add tooltips based on amount of bees.")
        public boolean beesTooltip = true;
        @Matches("#[0-9A-Fa-f]{6}")
        public String beeTooltipColour = "#ffaa00";
        @Comment("Add tooltips based on amount of honey.")
        public boolean honeyTooltip = true;
        @Matches("#[0-9A-Fa-f]{6}")
        public String honeyTooltipColour = "#ffaa00";
        @Comment("Add tooltips based on light level.")
        public boolean lightLevelTooltip = true;
        @Matches("#[0-9A-Fa-f]{6}")
        public String lightLevelTooltipColour = "#ffaa00";
        public boolean foodTooltip = true;
        @Matches("#[0-9A-Fa-f]{6}")
        public String foodTooltipColour = "#ffaa00";
        public boolean saturationTooltip = true;
        @Matches("#[0-9A-Fa-f]{6}")
        public String saturationTooltipColour = "#ffaa00";
    }

    public DurabilityTooltip durabilityTooltip = new DurabilityTooltip();
    public static class DurabilityTooltip implements Section {
        @Comment("Add tooltips based on durability.")
        public boolean enable = true;
        @Comment("Colour of the durability tooltip.")
        @Matches("#[0-9A-Fa-f]{6}")
        public String text_colour = "#aaaaaa";
        @Comment("Always show durability tooltip.")
        public boolean always_show = false;
    }

    public TextTooltips textTooltips = new TextTooltips();
    public static class TextTooltips implements Section {
        @Comment("Add tooltips to Compasses with the player's coordinates.")
        public boolean compassTooltip = true;
        @Comment("Compass tooltip colour.")
        @Matches("#[0-9A-Fa-f]{6}")
        public String compassTooltipColour = "#ff5555";
        @Comment("Add tooltips based on Lodestone Compass's destination.")
        public boolean lodestoneTooltip = true;
        @Comment("Lodestone Compass tooltip colour.")
        @Matches("#[0-9A-Fa-f]{6}")
        public String lodestoneCompassTooltipColour = "#ffaa00";
        @Comment("Add tooltips based on Recovery Compass's destination.")
        public boolean recoveryCompassTooltip = true;
        @Comment("Recovery Compass tooltip colour.")
        @Matches("#[0-9A-Fa-f]{6}")
        public String recoveryCompassTooltipColour = "#55ffff";
    }

    public ClockTooltip clockTooltip = new ClockTooltip();

    public static class ClockTooltip implements Section {
        @Comment("Add tooltips to Clocks with the current time.")
        public boolean current_time = true;
        @Comment("Add tooltips to Clocks with the current day.")
        public boolean day_count = true;
        @Comment("Whether to show the clock in a 24 hour format.")
        public boolean twenty_four_hour_clock = true;
        @Comment("Text colour of the clock tooltip.")
        @Matches("#[0-9A-Fa-f]{6}")
        public String text_colour = "#ffaa00";
    }

    public DeveloperOptions developerOptions = new DeveloperOptions();
    public static class DeveloperOptions implements Section {
        @Comment("Show debug information when holding Alt.")
        public boolean debugInfo = false;
        @Comment("Food tooltip is hidden when AppleSkin is installed to prevent redundant information.")
        @DisplayName("Show Food Tooltip with AppleSkin installed")
        public boolean showFoodTooltipWithAppleSkinInstalled = false;
    }




}
