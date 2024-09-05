package net.smackplays.smacksutil.networking.s2cpacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.smackplays.smacksutil.platform.Services;

public class S2CBlockBreakPacketHandler {

    @SuppressWarnings("unused")
    public static void handle(S2CBlockBreakPacket data, ClientPlayNetworking.Context context) {
            BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
            Player player = context.player();
            if (Services.KEY_HANDLER.isVeinKeyDown()){
                Services.VEIN_MINER.veinMiner(player.level(), player, pos);
            }
    }
}
