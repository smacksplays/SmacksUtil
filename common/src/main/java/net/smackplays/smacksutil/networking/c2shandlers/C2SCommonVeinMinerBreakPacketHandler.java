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
     * @param sourcePos sourcePos
     * @param curr curr
     * @param isCreative isCreative
     * @param replaceSeeds replaceSeeds*/
    public static void handle(ServerPlayer player, Level world, BlockPos sourcePos, BlockPos curr, boolean isCreative, boolean replaceSeeds) {
        ItemStack stack = player.getMainHandItem();

        BlockState currBlockState = world.getBlockState(curr);
        if (currBlockState.isAir()) return;
        if(stack.getMaxDamage() == stack.getDamageValue() + 1) return;
        world.setBlockAndUpdate(curr, Blocks.AIR.defaultBlockState());
        if (!isCreative) {
            BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(curr) : null;
            Block.dropResources(currBlockState, world, sourcePos, currBlockEntity, null, stack);
            if (stack.isDamageableItem()) {
                stack.hurtAndBreak(1, player.level(), player, c -> {});
            }
        }
        if (replaceSeeds) {
            world.setBlockAndUpdate(curr, currBlockState.getBlock().defaultBlockState());
        }
    }
}
