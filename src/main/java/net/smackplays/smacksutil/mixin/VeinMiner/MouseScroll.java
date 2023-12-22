package net.smackplays.smacksutil.mixin.VeinMiner;

import net.minecraft.client.Mouse;
import net.smackplays.smacksutil.VeinMiner.Miner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Mouse.class)
public class MouseScroll {
    @Inject(at = @At("HEAD"), method = "onMouseScroll")
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        Miner.scroll(vertical);
    }
}
