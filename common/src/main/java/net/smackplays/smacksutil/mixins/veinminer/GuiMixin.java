package net.smackplays.smacksutil.mixins.veinminer;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Player;
import net.smackplays.smacksutil.CommonClass;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.Services;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(at = @At("TAIL"), method = "renderItemHotbar")
    private void smacksUtil$renderItemHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Services.KEY_HANDLER != null && Services.KEY_HANDLER.isVeinKeyDown()) {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.isShiftKeyDown()) {
                ArrayList<String> toDisplayModes = CommonClass.veinMiner.getModes();

                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Constants.C_VEINMINER_MODE_BOX_LOCATION_RL
                        , 1, 1,0, 0, 110, 40, 110, 40);

                guiGraphics.drawString(this.minecraft.font, toDisplayModes.get(2), 10, 7, CommonColors.WHITE);
                guiGraphics.drawString(this.minecraft.font, "->" + toDisplayModes.get(1), 10, 7 + minecraft.font.lineHeight, CommonColors.WHITE);
                guiGraphics.drawString(this.minecraft.font, toDisplayModes.get(0), 10, 7 + minecraft.font.lineHeight * 2, CommonColors.WHITE);
            }
        }
    }
}
