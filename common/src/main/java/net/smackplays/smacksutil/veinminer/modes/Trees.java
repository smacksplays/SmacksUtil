package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Veinminer Trees Mode */
public class Trees extends VeinMode{
    /** Constructor*/
    public Trees() {
        ModeName = "Trees";
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
        this.tag = ModTags.Blocks.TREE_BLOCKS;

        return breathFirstSearch();
    }
}
