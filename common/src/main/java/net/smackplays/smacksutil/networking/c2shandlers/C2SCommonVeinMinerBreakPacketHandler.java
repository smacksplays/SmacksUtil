package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class C2SCommonVeinMinerBreakPacketHandler {
    public static void handle(ServerPlayer player, Level world, BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        ItemStack stack = player.getMainHandItem();

        BlockState currBlockState = world.getBlockState(pos);

        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (!isCreative) {
            BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(pos) : null;
            Block.dropResources(currBlockState, world, pos, currBlockEntity, null, stack);
            if (stack.isDamageableItem()) {
                stack.hurtAndBreak(1, (ServerLevel)player.level(), player, c -> {});
            }
        }
        if (replaceSeeds) {
            world.setBlockAndUpdate(pos, currBlockState.getBlock().defaultBlockState());
        }
    }
}
