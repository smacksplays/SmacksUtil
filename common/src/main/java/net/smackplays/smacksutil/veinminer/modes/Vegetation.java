package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

/**
 * Veinminer Shapeless Mode */
public class Vegetation extends VeinMode {
    /** Constructor*/
    public Vegetation() {
        ModeName = "Vegetation";
    }

    /** Get the Ore Blocks connected to sourcePos if they are Ores.
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
        this.result = new ArrayList<>();

        BlockPos pos = new BlockPos(sourcePos.getX() - radius, sourcePos.getY() - 2, sourcePos.getZ() - radius);
        return crops(pos, radius, player);
    }

    /** tunnel method
     * @param curr curr
     * @param radius radius
     * @param player player
     * @return Sorted list of Blocks to break */
    public ArrayList<BlockPos> crops(BlockPos curr, int radius, Player player) {
        for (int i = 0; i < radius * 2 + 1; i++) {
            for (int j = 0; j < radius * 2 + 1; j++) {
                for (int u = 0; u < 5; u++) {
                    if (world.getBlockState(curr).is(ModTags.Blocks.VEGETATION_BLOCKS) && player.hasCorrectToolForDrops(world.getBlockState(curr))) {
                        result.add(curr);
                    }
                    curr = curr.offset(0, 1, 0);
                }
                curr = curr.offset(0, -5, 1);
            }
            curr = curr.offset(1, 0, -radius * 2 - 1);
        }

        return result;
    }



    /** check if Rendering is allowed
     * @param radius radius
     * @return true if allowed*/
    @Override
    public boolean doRender(int radius) {
        return false;
    }
}