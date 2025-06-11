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

/**
 * Class MouseScroll */
@Mixin(MouseHandler.class)
public class MouseScroll {
    /** Constructor*/
    public MouseScroll() {

    }

    /** shadow final Minecraft instance*/
    @Final
    @Shadow
    private Minecraft minecraft;

    /** Capture mouseScroll
     * @param window window
     * @param horizontal horizontal
     * @param vertical vertical
     * @param ci ci*/
    @Inject(at = @At("HEAD"), method = "onScroll")
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        CommonClass.veinMiner.scroll(vertical, minecraft.player);
    }
}
