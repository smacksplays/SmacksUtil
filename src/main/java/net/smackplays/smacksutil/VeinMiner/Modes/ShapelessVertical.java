package net.smackplays.smacksutil.VeinMiner.Modes;


import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.smackplays.smacksutil.util.ModTags;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ShapelessVertical extends VeinMode {
    public ShapelessVertical() {
        ModeName = "ShapelessVertical";
        MAX_RADIUS = 5;
    }

    @Override
    public ArrayList<BlockPos> getBlocks(World world, PlayerEntity player, BlockPos sourcePos, int radius, boolean isExactMatch) {
        if (world == null || player == null || sourcePos == null) return (ArrayList<BlockPos>) toBreak.clone();
        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        toBreak.clear();
        checked.clear();
        Block toMatch = world.getBlockState(sourcePos).getBlock();

        if (!oldToBreak.isEmpty() && oldSourcePos.equals(sourcePos)
                && oldRadius == radius && oldToMatch.equals(toMatch)) {
            return (ArrayList<BlockPos>) oldToBreak.clone();
        }

        TagKey<Block> tag = null;
        if (world.getBlockState(sourcePos).isIn(ModTags.Blocks.STONE_BLOCKS)) {
            tag = ModTags.Blocks.STONE_BLOCKS;
        } else if (world.getBlockState(sourcePos).isIn(ModTags.Blocks.DIRT_BLOCKS)) {
            tag = ModTags.Blocks.DIRT_BLOCKS;
        }

        shapeless_vert(sourcePos, sourcePos, radius, player, world, isExactMatch, toMatch, tag);

        toBreak.sort(new BlockPosComparator(player));

        oldToBreak = (ArrayList<BlockPos>) toBreak.clone();
        oldRadius = radius;
        oldSourcePos = sourcePos;
        oldToMatch = toMatch;

        return (ArrayList<BlockPos>) toBreak.clone();
    }

    private void shapeless_vert(BlockPos curr, BlockPos sourcePos, int radius, PlayerEntity player,
                                World world, boolean isExactMatch, Block toMatch, TagKey<Block> tag) {
        if (curr.getX() > sourcePos.getX() + radius
                || curr.getX() < sourcePos.getX() - radius) {
            return;
        }
        if (curr.getY() > sourcePos.getY() + radius
                || curr.getY() < player.getY()) {
            return;
        }
        if (curr.getZ() > sourcePos.getZ() + radius
                || curr.getZ() < sourcePos.getZ() - radius) {
            return;
        }
        if (checked.contains(curr)) {
            return;
        } else {
            checked.add(curr);
        }
        if (checkMatch(isExactMatch, curr, world, player, toMatch, tag)) {
            toBreak.add(curr);
        }
        BlockPos[] surrounding = getSurrounding(curr);

        for (BlockPos pos : surrounding) {
            if (checkMatch(isExactMatch, pos, world, player, toMatch, tag)) {
                shapeless_vert(pos, sourcePos, radius, player, world, isExactMatch, toMatch, tag);
            }
        }
    }
}