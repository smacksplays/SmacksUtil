package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Common Handler for the VeinMinerBreakPacket */
public class C2SCommonVeinMinerBreakPacketHandler {
    /** Constructor*/
    public C2SCommonVeinMinerBreakPacketHandler(){

    }
    /** Break block and hurt tool. Also apply enchantments
     * @param player player
     * @param world world
     * @param pos pos
     * @param isCreative isCreative
     * @param replaceSeeds replaceSeeds*/
    public static void handle(ServerPlayer player, Level world, BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        ItemStack stack = player.getMainHandItem();

        BlockState currBlockState = world.getBlockState(pos);
        if (currBlockState.isAir()) return;
        if(stack.getMaxDamage() == stack.getDamageValue() + 1) return;
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (!isCreative) {
            BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(pos) : null;
            Block.dropResources(currBlockState, world, pos, currBlockEntity, null, stack);
            if (stack.isDamageableItem()) {
                stack.hurtAndBreak(1, player.level(), player, c -> {});
            }
        }
        if (replaceSeeds) {
            world.setBlockAndUpdate(pos, currBlockState.getBlock().defaultBlockState());
        }
    }
}
