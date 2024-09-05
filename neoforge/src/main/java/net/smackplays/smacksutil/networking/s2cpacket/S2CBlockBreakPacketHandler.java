package net.smackplays.smacksutil.networking.s2cpacket;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class S2CBlockBreakPacketHandler {

    @SuppressWarnings("unused")
    public static void handle(S2CBlockBreakPacket data, IPayloadContext context) {
        // Do something with the data, on the network thread
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            /*BlockPos pos = data.pos();
            Player player = context.player());
            if (Services.KEY_HANDLER.isVeinKeyDown()){
                Services.VEIN_MINER.veinMiner(player.level(), player, pos);
            }*/
        });
    }
}
