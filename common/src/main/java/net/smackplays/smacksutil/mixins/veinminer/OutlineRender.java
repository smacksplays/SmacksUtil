package net.smackplays.smacksutil.mixins.veinminer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.CommonClass;
import net.smackplays.smacksutil.platform.Services;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class OutlineRender {
    @Inject(at = @At("HEAD"), method = "renderHitOutline", cancellable = true)
    private void drawBlockOutline(PoseStack matrices, VertexConsumer vertexConsumer,
                                  Entity entity, double cameraX, double cameraY,
                                  double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        assert Services.KEY_HANDLER != null;
        if (Services.KEY_HANDLER.isVeinKeyDown()){
            if (CommonClass.veinMiner.isAcceptUpdate(pos)){
                CommonClass.veinMiner.updateBlocks(entity.level(), (Player) entity, pos);
            }
            if (CommonClass.veinMiner.isRenderPreview() && CommonClass.veinMiner.canRender(entity.level(), pos)){
                CommonClass.veinMiner.drawOutline(matrices, cameraX, cameraY,
                        cameraZ, pos, entity.level());
            }
            ci.cancel();
        }
    }
}
