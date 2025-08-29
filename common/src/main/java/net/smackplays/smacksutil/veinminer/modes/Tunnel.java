package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.util.ModTags;
import net.smackplays.smacksutil.util.PlayerUtil;

import java.util.ArrayList;

/** Class Tunnel */
public class Tunnel extends VeinMode {
    /** playerDirection*/
    private Direction playerDirection;
    /** Constructor*/
    public Tunnel() {
        ModeName = "Tunnel";
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

        if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            this.tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            this.tag = ModTags.Blocks.DIRT_BLOCKS;
        } else {
            tag = null;
        }

        return tunnel(sourcePos, radius, player);
    }

    /** tunnel method
     * @param curr curr
     * @param radius radius
     * @param player player
     * @return Sorted list of Blocks to break */
    public ArrayList<BlockPos> tunnel(BlockPos curr, int radius, Player player) {
        for (int i = 0; i < radius; i++) {
            if (checkConnected(curr)) {
                result.add(curr);
                if (playerDirection.equals(Direction.UP)) {
                    if (checkConnected(curr.relative(player.getDirection(), -1))) {
                        result.add(curr.relative(player.getDirection(), -1));
                    }
                } else if (playerDirection.equals(Direction.DOWN)) {
                    if (checkConnected(curr.relative(player.getDirection()))) {
                        result.add(curr.relative(player.getDirection(), 1));
                    }
                }else {
                    if (checkConnected(curr.below())) {
                        result.add(curr.below());
                    }
                }
                curr = curr.relative(playerDirection, -1);
            }
        }
        return result;
    }
}