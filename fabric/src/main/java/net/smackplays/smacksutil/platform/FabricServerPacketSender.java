package net.smackplays.smacksutil.platform;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.platform.services.IServerPacketSender;
import org.joml.Vector3f;

public class FabricServerPacketSender implements IServerPacketSender {
    @Override
    public void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos) {
        Vector3f pos3f = new Vector3f(pos.getX(), pos.getY(), pos.getZ());
        if (ServerPlayNetworking.canSend(player, Constants.C_VEINMINER_SERVER_BLOCK_BREAK_REQUEST_RL)){
            ServerPlayNetworking.send(player, new S2CBlockBreakPacket(pos3f));
        }
    }
}
