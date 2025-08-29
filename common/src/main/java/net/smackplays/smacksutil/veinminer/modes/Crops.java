package net.smackplays.smacksutil.veinminer.modes;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

/**
 * Veinminer Crops Mode */
public class Crops extends VeinMode {
    /** world*/
    private Level world;
    /** result*/
    private ArrayList<BlockPos> result;
    /** Constructor*/
    public Crops() {
        ModeName = "Crops";
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

        for (int x = 0; x < radius * 2 + 1; x++) {
            for (int z = 0; z < radius * 2 + 1; z++) {
                for (int y = 0; y < 5; y++) {
                    BlockState state = world.getBlockState(curr);
                    if (CropBlock.class.isAssignableFrom(state.getBlock().getClass())) {
                        CropBlock crop = (CropBlock) state.getBlock();
                        if (crop.isMaxAge(state) && player.hasCorrectToolForDrops(world.getBlockState(curr))) {
                            result.add(curr);
                        }
                    }
                    curr = curr.offset(0, 1, 0);
                }
                curr = curr.offset(0, -5, 1);
            }
            curr = curr.offset(1, 0, -radius * 2 - 1);
        }

        return result;
    }
}