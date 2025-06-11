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

/**
 * Class OutlineRender */
@Mixin(LevelRenderer.class)
public abstract class OutlineRender {
    /** Constructor*/
    public OutlineRender() {

    }

    /** facilitate outline rendering
     * @param poseStack poseStack
     * @param buffer buffer
     * @param entity entity
     * @param cameraX cameraX
     * @param cameraY cameraY
     * @param cameraZ cameraZ
     * @param pos pos
     * @param state state
     * @param color color
     * @param ci ci*/
    @Inject(at = @At("HEAD"), method = "renderHitOutline")
    private void drawBlockOutline(PoseStack poseStack, VertexConsumer buffer,
                                  Entity entity, double cameraX, double cameraY,
                                  double cameraZ, BlockPos pos, BlockState state, int color, CallbackInfo ci) {
        assert Services.KEY_HANDLER != null;
        if (Services.KEY_HANDLER.isVeinKeyDown()){
            if (CommonClass.veinMiner.isAcceptUpdate(pos)){
                CommonClass.veinMiner.updateBlocks(entity.level(), (Player) entity, pos);
            }
            if (CommonClass.veinMiner.isRenderPreview() && CommonClass.veinMiner.canRender(entity.level(), pos)){
                CommonClass.veinMiner.drawOutline(poseStack, cameraX, cameraY,
                        cameraZ, pos, entity.level());
            }
        }
    }
}
