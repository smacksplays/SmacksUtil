package net.smackplays.smacksutil.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.platform.services.IServerPacketSender;

public class NeoForgeServerPacketSender implements IServerPacketSender {

    @Override
    public void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos) {
        player.connection.send(new S2CBlockBreakPacket(pos));
    }
}
