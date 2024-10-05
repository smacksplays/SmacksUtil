package net.smackplays.smacksutil.networking.s2cpacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.smackplays.smacksutil.networking.s2chandlers.S2CCommonBlockBreakPacketHandler;

public class S2CPlayerBlockBreakPacket {
    private final BlockPos pos;

    public S2CPlayerBlockBreakPacket(BlockPos p) {
        pos = p;
    }

    public S2CPlayerBlockBreakPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> S2CCommonBlockBreakPacketHandler.handle(pos)));
    }
}
