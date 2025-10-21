package cc.cassian.inline_tooltips.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {

    public IconTooltips iconTooltips = new IconTooltips();
    public static class IconTooltips implements Section {
        @Comment("Add tooltips based on item attributes.")
        public boolean attributeTooltips = true;
        public String attributeTooltipColor = "Dark Green";
        @Comment("Add tooltips based on fuel levels.")
        public boolean fuelTooltip = true;
        public String fuelTooltipColor = "Gold";
        @Comment("Add tooltips based on amount of bees.")
        public boolean beesTooltip = true;
        public String beeTooltipColor = "Gold";
        @Comment("Add tooltips based on amount of honey.")
        public boolean honeyTooltip = true;
        public String honeyTooltipColor = "Gold";
        @Comment("Add tooltips based on light level.")
        public boolean lightLevelTooltip = true;
        public String lightLevelTooltipColor = "Gold";
        public boolean foodTooltip = true;
        public String foodTooltipColor = "Gold";
        public boolean saturationTooltip = true;
        public String saturationTooltipColor = "Gold";
    }

    public TextTooltips textTooltips = new TextTooltips();
    public static class TextTooltips implements Section {
        @Comment("Add tooltips based on durability.")
        public boolean durabilityTooltip = true;
        public String durabilityTooltipColor = "Gray";
        @Comment("Add tooltips to Compasses with the player's coordinates.")
        public boolean compassTooltip = true;
        @Comment("Compass tooltip colour.")
        public String compassTooltipColor = "Red";
        @Comment("Add tooltips based on Lodestone Compass's destination.")
        public boolean lodestoneTooltip = true;
        @Comment("Lodestone Compass tooltip colour.")
        public String lodestoneCompassTooltipColor = "Gold";
        @Comment("Add tooltips based on Recovery Compass's destination.")
        public boolean recoveryCompassTooltip = true;
        @Comment("Recovery Compass tooltip colour.")
        public String recoveryCompassTooltipColor = "Aqua";
    }

    public ClockTooltip clockTooltip = new ClockTooltip();

    public static class ClockTooltip implements Section {
        @Comment("Add tooltips to Clocks with the current time.")
        public boolean current_time = true;
        @Comment("Add tooltips to Clocks with the current day.")
        public boolean day_count = true;
        @Comment("Whether to show the clock in a 24 hour format.")
        public boolean twenty_four_hour_clock = true;
        @Comment("Text color of the clock tooltip.")
        public String text_color = "Gold";
    }

    public DeveloperOptions developerOptions = new DeveloperOptions();
    public static class DeveloperOptions implements Section {
        @Comment("Show debug information when holding Alt.")
        public boolean debugInfo = false;
        @Comment("Food tooltip is hidden when AppleSkin is installed to prevent redundant information.")
        public boolean showFoodTooltipWithAppleSkinInstalled = false;
    }




}
