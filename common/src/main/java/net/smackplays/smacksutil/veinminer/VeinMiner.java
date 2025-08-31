package net.smackplays.smacksutil.veinminer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.ModTags;
import net.smackplays.smacksutil.veinminer.modes.*;

import java.util.ArrayList;
import java.util.List;

import static net.smackplays.smacksutil.Constants.C_VEINMINER_UPDATE_RATE;

/**
 * Class VeinMiner */
public class VeinMiner {
    /** MAX_RADIUS*/
    public static int MAX_RADIUS = 6;
    /** lastUpdate*/
    public long lastUpdate = System.currentTimeMillis();
    /** old_toBreak*/
    public static ArrayList<BlockPos> old_toBreak = new ArrayList<>();
    /** toBreak*/
    public static ArrayList<BlockPos> toBreak = new ArrayList<>();
    /** old_lastBlockPos*/
    public BlockPos old_lastBlockPos = new BlockPos(0,0,0);
    /** lastBlockPos*/
    public BlockPos lastBlockPos = new BlockPos(0,0,0);
    /** isCreative*/
    public static boolean isCreative = false;
    /** replaceSeeds*/
    public static boolean replaceSeeds = false;
    /** mode*/
    public static VeinMode mode;
    /** radius*/
    public static int radius = 2;
    /** ShapelessMode*/
    public static final VeinMode ShapelessMode = new Shapeless();
    /** ShapelessVerticalMode*/
    public static final VeinMode ShapelessVerticalMode = new ShapelessVertical();
    /** TunnelMode*/
    public static final VeinMode TunnelMode = new Tunnel();
    /** ThreeByThreeMode*/
    public static final VeinMode ThreeByThreeMode = new ThreeByThree();
    /** MineshaftUPMode*/
    public static final VeinMode MineshaftUPMode = new Mineshaft(true);
    /** MineshaftDOWNMode*/
    public static final VeinMode MineshaftDOWNMode = new Mineshaft(false);
    /** CropsMode*/
    public static final VeinMode CropsMode = new Crops();
    /** OresMode*/
    public static final VeinMode OresMode = new Ores();
    /** VegetationMode*/
    public static final VeinMode VegetationMode = new Vegetation();
    /** TreeMode*/
    public static final VeinMode TreeMode = new Trees();
    /** modeList*/
    public static final  ArrayList<VeinMode> modeList = new ArrayList<>() {{
        add(ShapelessMode);
        add(ShapelessVerticalMode);
        add(TunnelMode);
        add(ThreeByThreeMode);
        add(MineshaftUPMode);
        add(MineshaftDOWNMode);
    }};
    /** currMode*/
    public static int currMode = 0;
    /** renderPreview*/
    public boolean renderPreview = false;
    /** isMining*/
    public boolean isMining = false;
    /** isDrawing*/
    public boolean isDrawing = false;
    /** isExactMatch*/
    public boolean isExactMatch = false;
    /** Constructor*/
    public VeinMiner() {

    }

    /** Draw Outline
     * @param poseStack poseStack
     * @param vertexConsumer vertexConsumer
     * @param shape shape
     * @param offsetX offsetX
     * @param offsetY offsetY
     * @param offsetZ offsetZ*/
    public static void drawCuboidShapeOutline(PoseStack poseStack, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ) {
        PoseStack.Pose pose = poseStack.last();
        shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
            float k = (float) (maxX - minX);
            float l = (float) (maxY - minY);
            float m = (float) (maxZ - minZ);
            float n = Mth.sqrt(k * k + l * l + m * m);
            k /= n;
            l /= n;
            m /= n;
            vertexConsumer.addVertex(pose.pose(),
                            (float) (minX + offsetX),
                            (float) (minY + offsetY),
                            (float) (minZ + offsetZ))
                    .setColor(1.0F, 1.0F, 1.0F, 0.8F)
                    .setNormal(pose, k, l, m);
            vertexConsumer.addVertex(pose.pose(),
                            (float) (maxX + offsetX),
                            (float) (maxY + offsetY),
                            (float) (maxZ + offsetZ))
                    .setColor(1.0F, 1.0F, 1.0F, 0.8F)
                    .setNormal(pose, k, l, m);
        });
    }

    /** Prairie drawing
     * @param pose pose
     * @param cameraX cameraX
     * @param cameraY cameraY
     * @param cameraZ cameraZ
     * @param pos pos
     * @param world world*/
    public void drawOutline(PoseStack pose, double cameraX, double cameraY, double cameraZ, BlockPos pos,
                                     Level world){
        if (isDrawing) return;
        isDrawing = true;
        int maxRenderBlocks = 150;
        if (Services.CONFIG != null){
            maxRenderBlocks = Services.CONFIG.getMaxRenderBlocks();
        }
        if (toBreak.size() > maxRenderBlocks) {
            toBreak = new ArrayList<>(toBreak.subList(0, maxRenderBlocks));
        }

        VoxelShape shape = combine(world, pos, new ArrayList<>(toBreak));
        VertexConsumer vertex = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.lines());

        drawCuboidShapeOutline(pose, vertex, shape,
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ);
        isDrawing = false;
    }

    /** Get connected blocks depending on mode
     * @param worldIn worldIn
     * @param playerIn playerIn
     * @param sourcePosIn sourcePosIn
     * @return Sorted list of Blocks to break.*/
    public ArrayList<BlockPos> getBlocks(Level worldIn, Player playerIn, BlockPos sourcePosIn) {
        BlockState sourceBlockState = worldIn.getBlockState(sourcePosIn);
        ArrayList<BlockPos> matching;
        setMode();
        if (sourceBlockState.is(ModTags.Blocks.CROP_BLOCKS)) {
            matching = new ArrayList<>(CropsMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch));
        } else if (sourceBlockState.is(ModTags.Blocks.ORE_BLOCKS)) {
            matching = new ArrayList<>(OresMode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch));
        } else if (sourceBlockState.is(ModTags.Blocks.VEGETATION_BLOCKS)) {
            matching = new ArrayList<>(VegetationMode.getBlocks(worldIn, playerIn, sourcePosIn, 10, isExactMatch));
        } else if (sourceBlockState.is(ModTags.Blocks.TREE_BLOCKS)) {
            matching = new ArrayList<>(TreeMode.getBlocks(worldIn, playerIn, sourcePosIn, 10, isExactMatch));
        } else {
            matching = new ArrayList<>(mode.getBlocks(worldIn, playerIn, sourcePosIn, radius, isExactMatch));
        }

        return matching;
    }

    /** veinMiner trigger function
     * @param world world
     * @param player player
     * @param sourcePos sourcePos*/
    public void veinMiner(Level world, Player player, BlockPos sourcePos) {
        if (isMining) return;
        isMining = true;
        if (sourcePos.equals(old_lastBlockPos)) toBreak = old_toBreak;

        ItemStack mainHandStack = player.getMainHandItem();
        boolean mainHandIsTool = mainHandStack.isCorrectToolForDrops(world.getBlockState(sourcePos)) && !mainHandStack.isEmpty();

        int maxDMG = mainHandStack.getMaxDamage();

        for (BlockPos curr : toBreak) {
            BlockState currBlockState = world.getBlockState(curr);

            if (mainHandIsTool && mainHandStack.getDamageValue() == maxDMG - 1) {
                isMining = false;
                player.displayClientMessage(Component.literal("Mining stopped! Tool would break ;)"), true);
                return;
            }

            boolean canHarvest = (player.hasCorrectToolForDrops(currBlockState) || player.isCreative());
            if (canHarvest) {
                if (Services.C2S_PACKET_SENDER != null) {
                    Services.C2S_PACKET_SENDER.VeinMinerBreakPacket(curr, isCreative, replaceSeeds);
                }
            }
        }

        isMining = false;
    }

    /** Change mode*/
    public void setMode() {
        mode = modeList.get(currMode);
        MAX_RADIUS = mode.MAX_RADIUS;
        if (radius > MAX_RADIUS) radius = MAX_RADIUS;
    }

    /** Toggle Preview*/
    public void togglePreview() {
        renderPreview = !renderPreview;
    }

    /** Getter
     * @return renderPreview*/
    public boolean isRenderPreview() {
        return renderPreview;
    }

    /** Combine a list of Blocks to VoxelShape
     * @param world world
     * @param pos pos
     * @param toRender toRender
     * @return shape*/
    public VoxelShape combine(Level world, BlockPos pos, List<BlockPos> toRender) {
        VoxelShape shape = Shapes.empty();
        for (BlockPos pos1 : toRender) {
            VoxelShape cubeShape = world.getBlockState(pos1).getShape(world, pos1);
            double offsetX = pos1.getX() - pos.getX();
            double offsetY = pos1.getY() - pos.getY();
            double offsetZ = pos1.getZ() - pos.getZ();
            shape = Shapes.or(shape, cubeShape.move(offsetX, offsetY, offsetZ));
        }
        return shape;
    }

    /** Toggle isExactMatch*/
    public void toggleExactMatch() {
        isExactMatch = !isExactMatch;
    }

    /** Getter isExactMatch
     * @return isExactMatch*/
    public boolean isExactMatch() {
        return isExactMatch;
    }

    /** getMode
     * @param world world
     * @param pos pos
     * @return VeinMode*/
    public VeinMode getMode(Level world, BlockPos pos) {
        BlockState sourceBlockState = world.getBlockState(pos);
        if (Services.CONFIG != null){
            if (sourceBlockState.is(ModTags.Blocks.CROP_BLOCKS)) {
                return CropsMode;
            } else if (sourceBlockState.is(ModTags.Blocks.ORE_BLOCKS)) {
                return OresMode;
            } else if (sourceBlockState.is(ModTags.Blocks.VEGETATION_BLOCKS)) {
                return VegetationMode;
            } else if (sourceBlockState.is(ModTags.Blocks.TREE_BLOCKS)) {
                return TreeMode;
            } else if (mode.equals(ShapelessMode)) {
                return ShapelessMode;
            } else if (mode.equals(ShapelessVerticalMode) && Services.CONFIG.isEnabledShapelessVerticalMode()) {
                return ShapelessVerticalMode;
            } else if (mode.equals(TunnelMode) && Services.CONFIG.isEnabledTunnelMode()) {
                return TunnelMode;
            } else if (mode.equals(MineshaftUPMode) && Services.CONFIG.isEnabledMineshaftMode()) {
                return MineshaftUPMode;
            } else if (mode.equals(MineshaftDOWNMode) && Services.CONFIG.isEnabledMineshaftMode()) {
                return MineshaftDOWNMode;
            }
        }
        return mode;
    }

    /** Hande MouseScroll
     * @param vertical vertical
     * @param player player*/
    public void scroll(double vertical, Player player) {

        if (Services.CONFIG != null) {
            ShapelessMode.MAX_RADIUS = Services.CONFIG.getMaxShapelessRadius();
            if (Services.CONFIG.isEnabledShapelessVerticalMode() && !modeList.contains(ShapelessVerticalMode)) {
                modeList.add(ShapelessVerticalMode);
            } else if (!Services.CONFIG.isEnabledShapelessVerticalMode()) {
                modeList.remove(ShapelessVerticalMode);
            }
            ShapelessVerticalMode.MAX_RADIUS = Services.CONFIG.getMaxShapelessVerticalRadius();
            if (Services.CONFIG.isEnabledTunnelMode() && !modeList.contains(TunnelMode)) {
                modeList.add(TunnelMode);
            } else if (!Services.CONFIG.isEnabledTunnelMode()) {
                modeList.remove(TunnelMode);
            }
            if (Services.CONFIG.isEnabledMineshaftMode() && !modeList.contains(MineshaftUPMode)) {
                modeList.add(MineshaftUPMode);
            } else if (!Services.CONFIG.isEnabledMineshaftMode()) {
                modeList.remove(MineshaftUPMode);
            }
            if (Services.CONFIG.isEnabledMineshaftMode() && !modeList.contains(MineshaftDOWNMode)) {
                modeList.add(MineshaftDOWNMode);
            } else if (!Services.CONFIG.isEnabledMineshaftMode()) {
                modeList.remove(MineshaftDOWNMode);
            }

        }
        if (player != null && Services.KEY_HANDLER != null && Services.KEY_HANDLER.isVeinKeyDown()) {
            int slot = player.getInventory().getSelectedSlot() + (int) vertical;
            if (slot >= 0 && slot < 9) {
                player.getInventory().setSelectedSlot(slot);
            } else if (slot < 0){
                player.getInventory().setSelectedSlot(8);
            } else {
                player.getInventory().setSelectedSlot(0);
            }
            if (player.isCrouching()) {
                currMode += (int) vertical;
                if (currMode > modeList.size() - 1) currMode = 0;
                else if (currMode < 0) currMode = modeList.size() - 1;
                setMode();
            } else {
                radius += (int) vertical;
                if (radius > MAX_RADIUS) radius = MAX_RADIUS;
                else if (radius < 1) radius = 1;
                player.displayClientMessage(Component.literal("Radius: " + radius), true);
            }
        }
    }

    /** Getter radius
     * @return radius*/
    public int getRadius() {
        return radius;
    }

    /** Check if mode can render
     * @param world world
     * @param pos pos
     * @return ture if you can render*/
    public boolean canRender(Level world, BlockPos pos){
        return getMode(world, pos).doRender(getRadius());
    }

    /** Check if update is acceptable
     * @param pos pos
     * @return true if acceptable*/
    public boolean isAcceptUpdate(BlockPos pos) {
        if (!lastBlockPos.equals(pos)) return true;
        if (isMining) return false;
        return System.currentTimeMillis() - lastUpdate > C_VEINMINER_UPDATE_RATE;
    }

    /** Update Blocks
     * @param world world
     * @param player player
     * @param sourcePos sourcePos*/
    public void updateBlocks(Level world, Player player, BlockPos sourcePos){
        old_toBreak = new ArrayList<>(toBreak);
        old_lastBlockPos = lastBlockPos;
        lastBlockPos = sourcePos;
        lastUpdate = System.currentTimeMillis();
        isCreative = player.isCreative();
        BlockState sourceBlockState = world.getBlockState(sourcePos);
        replaceSeeds = sourceBlockState.is(ModTags.Blocks.CROP_BLOCKS);
        toBreak = new ArrayList<>(getBlocks(world, player, sourcePos));
    }

    public ArrayList<String> getModes() {
        ArrayList<String> modes = new ArrayList<>();
        if (currMode == 0) {
            modes.add(modeList.getLast().getName());
            modes.add(modeList.get(currMode).getName());
            modes.add(modeList.get(currMode + 1).getName());
        } else if (currMode >= modeList.size() - 1) {
            modes.add(modeList.get(currMode - 1).getName());
            modes.add(modeList.get(currMode).getName());
            modes.add(modeList.getFirst().getName());
        }else {
            modes.add(modeList.get(currMode - 1).getName());
            modes.add(modeList.get(currMode).getName());
            modes.add(modeList.get(currMode + 1).getName());
        }
        return modes;
    }
}
