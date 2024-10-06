package net.smackplays.smacksutil.mixins.backpacks;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {

    @Final
    @Shadow
    private Minecraft minecraft;
    @Final
    @Shadow
    private PoseStack pose;
    @Inject(at = @At("HEAD"), method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", cancellable = true)
    public void renderItemDecorations(Font font, ItemStack stack, int offsetX, int offsetY, String yellowString, CallbackInfo ci) {
        GuiGraphics thisObject = (GuiGraphics) (Object) this;
        if (!stack.isEmpty() && AbstractBackpackItem.class.isAssignableFrom(stack.getItem().getClass())) {
            pose.pushPose();
            if (stack.getCount() != 1 || yellowString != null) {
                String countString = yellowString == null ? smacksUtil$getCorrCountString(stack) : yellowString;
                pose.translate(0.0F, 0.0F, 200.0F);
                thisObject.drawString(font, countString, offsetX + 19 - 2 - font.width(countString), offsetY + 6 + 3, 16777215, true);
            }

            if (stack.isBarVisible()) {
                int $$6 = stack.getBarWidth();
                int $$7 = stack.getBarColor();
                int $$8 = offsetX + 2;
                int $$9 = offsetY + 13;
                thisObject.fill(RenderType.guiOverlay(), $$8, $$9, $$8 + 13, $$9 + 2, -16777216);
                thisObject.fill(RenderType.guiOverlay(), $$8, $$9, $$8 + $$6, $$9 + 1, $$7 | 0xFF000000);
            }

            LocalPlayer $$10 = minecraft.player;
            float $$11 = $$10 == null ? 0.0F : $$10.getCooldowns().getCooldownPercent(stack.getItem(), minecraft.getFrameTimeNs());
            if ($$11 > 0.0F) {
                int $$12 = offsetY + Mth.floor(16.0F * (1.0F - $$11));
                int $$13 = $$12 + Mth.ceil(16.0F * $$11);
                thisObject.fill(RenderType.guiOverlay(), offsetX, $$12, offsetX + 16, $$13, Integer.MAX_VALUE);
            }
            pose.popPose();
            ci.cancel();
        }
    }

    @Unique
    @NotNull
    private static String smacksUtil$getCorrCountString(ItemStack stack) {
        String corrCount = String.valueOf(stack.getCount());
        if (stack.getCount() > 999 && stack.getCount() < 1000000) {
            int corr = stack.getCount()/1000;
            corrCount = corr + "k";
        } else if (stack.getCount() > 999999 && stack.getCount() < 1000000000){
            int corr = stack.getCount()/1000;
            corrCount = corr + "M";
        }
        return corrCount;
    }
}