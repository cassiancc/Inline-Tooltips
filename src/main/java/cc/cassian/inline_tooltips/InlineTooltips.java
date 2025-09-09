package cc.cassian.inline_tooltips;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InlineTooltips implements ClientModInitializer {
	public static final String MOD_ID = "inline_tooltips";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register(((itemStack, tooltipContext, tooltipFlag, list) -> {
            if (!Minecraft.getInstance().hasShiftDown()) {
                var component = Component.empty();
                for (EquipmentSlotGroup equipmentSlotGroup : EquipmentSlotGroup.values()) {
                    itemStack.forEachModifier(equipmentSlotGroup, (holder, attributeModifier, display) -> {
                        if (display != ItemAttributeModifiers.Display.hidden()) {
                            var player = Minecraft.getInstance().player;
                            double base = 0;
                            if (player != null) {
                                base+=player.getAttributeBaseValue(holder);
                            }
                            var icon = holder.unwrapKey().orElseThrow().location();
                            var iconComponent = Component.object(new AtlasSprite(AtlasSprite.DEFAULT_ATLAS, ResourceLocation.fromNamespaceAndPath(icon.getNamespace(), "inline_tooltip_icons/"+ icon.getPath()))).append(ModHelpers.format(attributeModifier.amount() + base) + " ");
                            if (Minecraft.getInstance().hasAltDown() && tooltipFlag.isAdvanced()) {
                                iconComponent.append(Component.literal(" (%s)".formatted(icon)));
                                list.add(iconComponent);
                            } else {
                                component.append(iconComponent);
                            }
                        }
                    });
                }
                if (!component.equals(Component.empty()))
                    list.add(component);
            }
        }));
    }
}