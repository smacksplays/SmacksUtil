package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.util.ModTags;
import net.smackplays.smacksutil.util.PlayerUtil;

import java.util.ArrayList;

/**
 * Veinminer Crops Mode */
public class Mineshaft extends VeinMode {
    /** playerDirection*/
    private Direction playerDirection;
    /** upMode*/
    private final boolean upMode;
    /** Constructor*/
    public Mineshaft(boolean upMode) {
        ModeName = "Mineshaft" + (upMode ? "(up)" : "(down)");
        MAX_RADIUS = 10;
        this.upMode = upMode;
    }

    /** Get the Blocks connected to sourcePos if they are Ores.
     * @param world world
     * @param player player
     * @param sourcePos sourcePos
     * @param radius radius
     * @param isExactMatch isExactMatch
     * @return Sorted list of Blocks to break.*/
    @Override
    public ArrayList<BlockPos> getBlocks(Level world, Player player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        this.world = world;
        this.player = player;
        this.isExactMatch = isExactMatch;
        this.sourceBlock = world.getBlockState(sourcePos).getBlock();
        this.result = new ArrayList<>();
        this.playerDirection = PlayerUtil.getTargetFaceDirection(player, world);
        if (playerDirection.equals(Direction.DOWN) || playerDirection.equals(Direction.UP)) {
            playerDirection = player.getDirection().getOpposite();
        }

        if (world.getBlockState(sourcePos).is(ModTags.Blocks.MINE_ABLE_PICKAXE)) {
            this.tag = ModTags.Blocks.MINE_ABLE_PICKAXE;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            this.tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            this.tag = ModTags.Blocks.DIRT_BLOCKS;
        } else {
            tag = null;
        }

        return mineshaft(sourcePos, radius);
    }

    /** tunnel method
     * @param curr curr
     * @param radius radius
     * @return Sorted list of Blocks to break */
    public ArrayList<BlockPos> mineshaft(BlockPos curr, int radius) {
        for (int i = 0; i < radius; i++) {
            if (checkConnected(curr)) {
                result.add(curr);
                for (int j = 1; j < 4; j++) {
                    if (checkConnected(curr.relative(playerDirection, -j))) {
                        result.add(curr.relative(playerDirection, -j));
                    }
                }
                curr = curr.relative(playerDirection, -1);
            }
            else break;
            if (upMode) {
                curr = curr.above();
            } else {
                curr = curr.below();
            }
        }
        return result;
    }

    @Override
    public boolean checkConnected(BlockPos curr) {
        var condition = false;
        if (world.getBlockState(curr).is(ModTags.Blocks.VEIN_BLACKLIST)) return false;
        if (!player.hasCorrectToolForDrops(world.getBlockState(curr)) && !player.isCreative()) return false;
        if (isExactMatch || tag == null) {
            condition = world.getBlockState(curr).is(sourceBlock);
        } else {
            condition = world.getBlockState(curr).is(tag);
        }
        return condition
                && (result != null && !result.contains(curr));
    }
}