package net.smackplays.smacksutil.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.server.level.ServerPlayer;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.platform.services.IServerPacketSender;
import org.joml.Vector3f;

public class NeoForgeServerPacketSender implements IServerPacketSender {

    @Override
    public void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos) {
        Vector3f pos3f = new Vector3f(pos.getX(), pos.getY(), pos.getZ());
        player.connection.send(new S2CBlockBreakPacket(pos3f));
    }
}
