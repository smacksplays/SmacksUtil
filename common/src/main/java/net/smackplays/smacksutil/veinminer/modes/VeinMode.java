package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

/**
 * Parent class for VeinModes */
public class VeinMode {
    /** ModeName*/
    public String ModeName = "";
    /** MAX_RADIUS*/
    public int MAX_RADIUS = 6;
    /**  maxBlocks*/
    public int maxBlocks = 150;

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

    /** check if Block matches
     * @param isExactMatch isExactMatch
     * @param pos pos
     * @param world world
     * @param player player
     * @param toMatch toMatch
     * @param tag tag
     * @return true if matches*/
    public boolean checkMatch(boolean isExactMatch, BlockPos pos, Level world, Player player, Block toMatch
            , TagKey<Block> tag, ArrayList<BlockPos> result) {
        boolean contains = result.contains(pos);
        boolean isInBlacklist = world.getBlockState(pos).is(ModTags.Blocks.VEIN_BLACKLIST);
        boolean canHarvest = (player.hasCorrectToolForDrops(world.getBlockState(pos)) || player.isCreative());
        if (isExactMatch || tag == null) {
            boolean isToMatch = world.getBlockState(pos).getBlock().equals(toMatch);
            return !contains && !isInBlacklist && canHarvest && isToMatch;
        } else {
            boolean isInTag = world.getBlockState(pos).is(tag);
            return !contains && !isInBlacklist && canHarvest && isInTag;
        }
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
}

