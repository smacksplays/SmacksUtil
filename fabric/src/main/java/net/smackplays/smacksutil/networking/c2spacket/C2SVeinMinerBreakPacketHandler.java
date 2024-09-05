package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class C2SVeinMinerBreakPacketHandler {
    public static void handle(C2SVeinMinerBreakPacket data, ServerPlayNetworking.Context context) {
        ServerLevel level = (ServerLevel) context.player().level();
        ServerPlayer player = context.player();
        Level world = player.level();
        ItemStack stack = player.getMainHandItem();
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);

        BlockState currBlockState = world.getBlockState(pos);

        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (!data.isCreative()) {
            BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(pos) : null;
            Block.dropResources(currBlockState, world, pos, currBlockEntity, null, ItemStack.EMPTY);
            if (stack.isDamageableItem()) {
                stack.hurtAndBreak(1, (ServerLevel)player.level(), player, c -> {});
            }
        }
        if (data.replaceSeeds()) {
            world.setBlockAndUpdate(pos, currBlockState.getBlock().defaultBlockState());
        }
    }
}
