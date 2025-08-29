package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.util.ModTags;
import net.smackplays.smacksutil.util.PlayerUtil;

import java.util.ArrayList;

/** Class ThreeByThree */
public class ThreeByThree extends VeinMode {
    /** playerDirection*/
    private Direction playerDirection;
    /** radius*/
    private int radius;
    /** Constructor*/
    public ThreeByThree() {
        ModeName = "3x3x1";
        MAX_RADIUS = 12;
    }

    /** Get the Ore Blocks connected to sourcePos if they are Ores.
     * @param world world
     * @param player player
     * @param sourcePos sourcePos
     * @param radius radius
     * @param isExactMatch isExactMatch
     * @return Sorted list of Blocks to break */
    @Override
    public ArrayList<BlockPos> getBlocks(Level world, Player player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        this.world = world;
        this.player = player;
        this.isExactMatch = isExactMatch;
        this.sourceBlock = world.getBlockState(sourcePos).getBlock();
        this.result = new ArrayList<>();
        this.playerDirection = PlayerUtil.getTargetFaceDirection(player, world);
        this.radius = radius;

        if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            this.tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            this.tag = ModTags.Blocks.DIRT_BLOCKS;
        } else {
            tag = null;
        }

        return threeByThree(sourcePos);
    }

    private ArrayList<BlockPos> threeByThree(BlockPos curr) {
        for (int i = 0; i < radius; i++) {
            if (checkConnected(curr)) {
                result.add(curr);
                if (playerDirection.equals(Direction.NORTH) || playerDirection.equals(Direction.SOUTH)){
                    threeByThreeDirection(curr, Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST);
                } else if (playerDirection.equals(Direction.EAST) || playerDirection.equals(Direction.WEST)) {
                    threeByThreeDirection(curr, Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH);
                } else if (playerDirection.equals(Direction.UP) || playerDirection.equals(Direction.DOWN)) {
                    threeByThreeDirection(curr, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
                }
            }
            curr = curr.relative(playerDirection, -1);
        }
        return result;
    }

    private void threeByThreeDirection(BlockPos curr, Direction dir1, Direction dir2
            , Direction dir3, Direction dir4) {
        if (checkConnected(curr.relative(dir1, 1))) {
            result.add(curr.relative(dir1, 1));
        }
        if (checkConnected(curr.relative(dir1, 1).relative(dir3, 1))) {
            result.add(curr.relative(dir1, 1).relative(dir3, 1));
        }
        if (checkConnected(curr.relative(dir1, 1).relative(dir4, 1))) {
            result.add(curr.relative(dir1, 1).relative(dir4, 1));
        }
        if (checkConnected(curr.relative(dir2, 1))) {
            result.add(curr.relative(dir2, 1));
        }
        if (checkConnected(curr.relative(dir2, 1).relative(dir3, 1))) {
            result.add(curr.relative(dir2, 1).relative(dir3, 1));
        }
        if (checkConnected(curr.relative(dir2, 1).relative(dir4, 1))) {
            result.add(curr.relative(dir2, 1).relative(dir4, 1));
        }
        if (checkConnected(curr.relative(dir3, 1))) {
            result.add(curr.relative(dir3, 1));
        }
        if (checkConnected(curr.relative(dir4, 1))) {
            result.add(curr.relative(dir4, 1));
        }
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
