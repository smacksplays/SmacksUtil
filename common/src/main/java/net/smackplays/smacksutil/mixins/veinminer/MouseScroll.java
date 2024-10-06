package net.smackplays.smacksutil.mixins.veinminer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.smackplays.smacksutil.CommonClass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MouseHandler.class)
public class MouseScroll {
    @Final
    @Shadow
    private Minecraft minecraft;
    @Inject(at = @At("HEAD"), method = "onScroll")
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        CommonClass.veinMiner.scroll(vertical, minecraft.player);
    }
}
