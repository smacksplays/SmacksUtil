package net.smackplays.smacksutil.mixins.fastplace;


import net.minecraft.client.Minecraft;
import net.smackplays.smacksutil.platform.Services;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Class MinecraftClientMixin */
@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    /** Constructor*/
    public MinecraftClientMixin() {

    }

    /** Shadow parameter rightClickDelay*/
    @Shadow
    private int rightClickDelay;

    /** Used to facilitate FastPlace
     * @param info info*/
    @Inject(at = @At("HEAD"), method = "handleKeybinds")
    private void handleKeybinds(CallbackInfo info) {
        if (Services.CONFIG != null && Services.CONFIG.isEnabledFastPlace() && rightClickDelay > 1) {
            rightClickDelay = 1;
        }
    }
}
