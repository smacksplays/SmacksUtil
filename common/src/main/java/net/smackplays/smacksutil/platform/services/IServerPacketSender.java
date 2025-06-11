package net.smackplays.smacksutil.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

/**
 * Interface IServerPacketSender */
public interface IServerPacketSender {
    /** Interface method sendToPlayerBlockBreakPacket
     * @param player player
     * @param pos pos*/
    void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos);
}
