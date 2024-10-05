package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleLightWandItemPacketHandler;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonVeinMinerBreakPacketHandler;

public class C2SVeinMinerBreakPacket {
    private final BlockPos pos;
    private final boolean isCreative;
    private final boolean replaceSeeds;

    public C2SVeinMinerBreakPacket(BlockPos p, boolean isC, boolean isR) {
        pos = p;
        isCreative = isC;
        replaceSeeds = isR;
    }

    public C2SVeinMinerBreakPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
        isCreative = buffer.readBoolean();
        replaceSeeds = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(isCreative);
        buffer.writeBoolean(replaceSeeds);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonVeinMinerBreakPacketHandler.handle(context.getSender(), context.getSender().level(), pos, isCreative, replaceSeeds);
        }
    }
}
