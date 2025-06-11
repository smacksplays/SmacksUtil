package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Veinminer Ores Mode */
public class Ores extends VeinMode {
    /** world*/
    private Level world;
    /** isExactMatch*/
    private boolean isExactMatch;
    /** tag*/
    private TagKey<Block> ore_tag;
    /** block*/
    private Block sourceBlock;
    /** queue*/
    private ArrayList<BlockPos> queue;
    /** checked*/
    private ArrayList<BlockPos> checked;
    /** result*/
    private ArrayList<BlockPos> result;
    /** sourcePos*/
    private BlockPos sourcePos;
    /** Constructor*/
    public Ores() {
        ModeName = "Ores";
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
        this.isExactMatch = isExactMatch;
        this.sourceBlock = world.getBlockState(sourcePos).getBlock();
        this.queue = new ArrayList<>(Collections.singletonList(sourcePos));
        this.checked = new ArrayList<>();
        this.result = new ArrayList<>();
        this.sourcePos = sourcePos;
        this.ore_tag = ModTags.Blocks.ORES_TAG;
        /*if (world.getBlockState(sourcePos).is(ModTags.Blocks.STONE_BLOCKS)) {
            this.tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).is(ModTags.Blocks.DIRT_BLOCKS)) {
            this.tag = ModTags.Blocks.DIRT_BLOCKS;
        }*/

        return breathFirstSearch();
    }

    /** BreathFirstSearch algorithm
     * @return Sorted list of connected blocks*/
    private ArrayList<BlockPos> breathFirstSearch() {
        while (!queue.isEmpty()) {
            BlockPos pos = queue.removeFirst();
            if (!checked.contains(pos)){
                checked.add(pos);
            }
            for (BlockPos p : findConnected(pos)){
                if (!queue.contains(p)) {
                    queue.add(p);
                }
            }
            if (!result.contains(pos)){
                result.add(pos);
            }
            queue.sort(Comparator.comparing(p -> p.getCenter().distanceTo(sourcePos.getCenter())));
            if (result.size() >= maxBlocks){
                return result;
            }
        }
        return result;
    }

    /** Find all connected Blocks using 22-Neighbor method
     * @param curr curr
     * @return List of surrounding matching blocks*/
    private ArrayList<BlockPos> findConnected(BlockPos curr) {
        ArrayList<BlockPos> connected = new ArrayList<>();
        if (checkConnected(curr.above())) {
            connected.add(curr.above());
        }
        if (checkConnected(curr.north())) {
            connected.add(curr.north());
        }
        if (checkConnected(curr.east())) {
            connected.add(curr.east());
        }
        if (checkConnected(curr.south())) {
            connected.add(curr.south());
        }
        if (checkConnected(curr.west())) {
            connected.add(curr.west());
        }
        if (checkConnected(curr.below())) {
            connected.add(curr.below());
        }
        if (checkConnected(curr.above().north())) {
            connected.add(curr.above().north());
        }
        if (checkConnected(curr.above().east())) {
            connected.add(curr.above().east());
        }
        if (checkConnected(curr.above().south())) {
            connected.add(curr.above().south());
        }
        if (checkConnected(curr.above().west())) {
            connected.add(curr.above().west());
        }
        if (checkConnected(curr.above().north().east())) {
            connected.add(curr.above().north().east());
        }
        if (checkConnected(curr.above().north().west())) {
            connected.add(curr.above().north().west());
        }
        if (checkConnected(curr.above().south().east())) {
            connected.add(curr.above().south().east());
        }
        if (checkConnected(curr.above().south().west())) {
            connected.add(curr.above().south().west());
        }
        if (checkConnected(curr.below().north())) {
            connected.add(curr.below().north());
        }
        if (checkConnected(curr.below().east())) {
            connected.add(curr.below().east());
        }
        if (checkConnected(curr.below().south())) {
            connected.add(curr.below().south());
        }
        if (checkConnected(curr.below().west())) {
            connected.add(curr.below().west());
        }
        if (checkConnected(curr.below().north().east())) {
            connected.add(curr.below().north().east());
        }
        if (checkConnected(curr.below().north().west())) {
            connected.add(curr.below().north().west());
        }
        if (checkConnected(curr.below().south().east())) {
            connected.add(curr.below().south().east());
        }
        if (checkConnected(curr.below().south().west())) {
            connected.add(curr.below().south().west());
        }
        return connected;
    }

    /** Check if given block matches source block or is already in a list
     * @param curr curr
     * @return true if no match*/
    private boolean checkConnected(BlockPos curr){
        var condition = false;
        if (isExactMatch || ore_tag == null) {
            condition = world.getBlockState(curr).is(sourceBlock);
        } else {
            condition = world.getBlockState(curr).is(ore_tag);
        }

        return condition
                && !checked.contains(curr)
                && !queue.contains(curr)
                && !result.contains(curr);
    }
}
