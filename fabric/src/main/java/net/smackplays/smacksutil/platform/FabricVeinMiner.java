package net.smackplays.smacksutil.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.platform.services.IVeinMiner;
import net.smackplays.smacksutil.util.CustomRenderLayer;

import java.util.ArrayList;

public class FabricVeinMiner extends IVeinMiner {

    public FabricVeinMiner() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void drawOutline(PoseStack pose, double cameraX, double cameraY, double cameraZ, BlockPos pos,
                            Level world, Player player) {
        if (isDrawing) return;
        isDrawing = true;
        //toBreak = (ArrayList<BlockPos>) Services.VEIN_MINER.getBlocks(world, player, pos).clone();
        int maxRenderBlocks = 0;
        if (Services.CONFIG != null) {
            maxRenderBlocks = Services.CONFIG.getMaxRenderBlocks();
        }
        ArrayList<BlockPos> toRender = (ArrayList<BlockPos>) toBreak.clone();
        if (toRender.size() > maxRenderBlocks) {
            toRender = new ArrayList<>(toRender.subList(0, maxRenderBlocks));
        }

        VoxelShape shape = combine(world, pos, toRender);

        VertexConsumer vertex = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(CustomRenderLayer.LINES);

        drawCuboidShapeOutline(pose, vertex, shape,
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ);
        isDrawing = false;
    }
}
