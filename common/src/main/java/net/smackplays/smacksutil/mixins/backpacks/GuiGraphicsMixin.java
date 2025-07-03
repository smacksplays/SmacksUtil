package net.smackplays.smacksutil.mixins.backpacks;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;
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
    private Matrix3x2fStack pose;

    @Inject(at = @At("HEAD"), method = "renderItemCount(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", cancellable = true)
    private void smacksUtil$renderItemCount(Font font, ItemStack stack, int x, int y, String text, CallbackInfo ci) {
        GuiGraphics thisObject = (GuiGraphics) (Object) this;
        if (stack.getCount() > 99){
            String s = smacksUtil$getCorrCountString(stack);
            pose.pushMatrix();
            if (stack.getCount() > 99 && stack.getCount() < 999) {
                float scale = 0.8F;
                pose.scale(scale);
                thisObject.drawString(font, s, (int) ((x + 19 + 2 - font.width(s))/scale), (int) ((y + 6 + 4)/scale), -1, true);
            } else if (stack.getCount() > 999 && stack.getCount() < 9999) {
                float scale = 0.9F;
                pose.scale(scale);
                thisObject.drawString(font, s, (int) ((x + 19 - 2 - font.width(s))/scale), (int) ((y + 6 + 3)/scale), -1, true);
            } else if (stack.getCount() > 9999 && stack.getCount() < 99999) {
                float scale = 0.8F;
                pose.scale(scale);
                thisObject.drawString(font, s, (int) ((x + 19 - font.width(s))/scale), (int) ((y + 6 + 3)/scale), -1, true);
            }
            pose.popMatrix();
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