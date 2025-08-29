package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Parent class for VeinModes */
public abstract class VeinMode {
    /** ModeName*/
    public String ModeName = "";
    /** MAX_RADIUS*/
    public int MAX_RADIUS = 6;
    /**  maxBlocks*/
    public int maxBlocks = 150;
    /** queue*/
    public ArrayList<BlockPos> queue;
    /** checked*/
    public ArrayList<BlockPos> checked;
    /** result*/
    public ArrayList<BlockPos> result;
    /** world*/
    public Level world;
    public Player player;
    /** isExactMatch*/
    public boolean isExactMatch;
    /** tag*/
    public TagKey<Block> tag;
    /** block*/
    public Block sourceBlock;
    /** sourcePos*/
    public BlockPos sourcePos;

    /** Constructor*/
    VeinMode() {
    }

    /** Parent function getBlocks
     * @param world world
     * @param player player
     * @param sourcePos sourcePos
     * @param radius radius
     * @param isExactMatch isExactMatch
     * @return Empty ArrayList*/
    public ArrayList<BlockPos> getBlocks(Level world, Player player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        return new ArrayList<>();
    }

    /** Get the Mode Name
     * @return ModeName*/
    public String getName() {
        return ModeName;
    }

    /** check if Rendering is allowed
     * @param radius radius
     * @return true if allowed*/
    public boolean doRender(int radius) {
        return true;
    }


    /** BreathFirstSearch algorithm
     * @return Sorted list of connected blocks*/
    public ArrayList<BlockPos> breathFirstSearch() {
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

    /** Find all connected Blocks using 26-Neighbor method
     * @param curr curr
     * @return List of surrounding matching blocks*/
    public ArrayList<BlockPos> findConnected(BlockPos curr) {
        ArrayList<BlockPos> connected = new ArrayList<>();
        for (int x = -1; x <= 3; x++) {
            for (int y = -1; y <= 3; y++) {
                for (int z = -1; z <= 3; z++) {
                    BlockPos test2 = curr.offset(x, y, z);
                    if (checkConnected(test2)) {
                        connected.add(test2);
                    }
                }
            }
        }
        return connected;
    }

    /** Check if given block matches source block or is already in a list
     * @param curr curr
     * @return true if no match*/
    public boolean checkConnected(BlockPos curr){
        var condition = false;
        if (world.getBlockState(curr).is(ModTags.Blocks.VEIN_BLACKLIST)) return false;
        if (!player.hasCorrectToolForDrops(world.getBlockState(curr)) && !player.isCreative()) return false;
        if (isExactMatch || tag == null) {
            condition = world.getBlockState(curr).is(sourceBlock);
        } else {
            condition = world.getBlockState(curr).is(tag);
        }
        return condition
                && (checked != null && !checked.contains(curr))
                && (queue != null && !queue.contains(curr))
                && (result != null && !result.contains(curr));
    }
}

