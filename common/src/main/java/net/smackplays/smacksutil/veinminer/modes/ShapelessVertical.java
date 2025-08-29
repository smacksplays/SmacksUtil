package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Veinminer ShapelessVertical Mode */
public class ShapelessVertical extends Shapeless{
    /** playerPos*/
    private BlockPos playerPos;
    /** Constructor*/
    public ShapelessVertical() {
        ModeName = "ShapelessVertical";
        if (Services.CONFIG != null){
            maxBlocks = Services.CONFIG.getMaxRenderBlocks();
        }
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
        this.isExactMatch = isExactMatch;
        this.sourceBlock = world.getBlockState(sourcePos).getBlock();
        this.queue = new ArrayList<>(Collections.singletonList(sourcePos));
        this.checked = new ArrayList<>();
        this.result = new ArrayList<>();
        this.sourcePos = sourcePos;
        this.playerPos = player.getOnPos();

        if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            this.tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            this.tag = ModTags.Blocks.DIRT_BLOCKS;
        } else {
            tag = null;
        }

        return breathFirstSearch();
    }

    /** Check if given block matches source block or is already in a list
     * @param curr curr
     * @return true if no match*/
    @Override
    public boolean checkConnected(BlockPos curr){
        var condition = false;
        if (isExactMatch || tag == null) {
            condition = world.getBlockState(curr).is(sourceBlock);
        } else {
            condition = world.getBlockState(curr).is(tag);
        }
        return condition
                && !checked.contains(curr)
                && !queue.contains(curr)
                && !result.contains(curr)
                && playerPos.getY() < curr.getY();
    }
}
