package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class C2SSetBlockAirPacketHandler {
    public static void handle(C2SSetBlockAirPacket data, IPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            Level world = context.player().level();
            //world.setBlockAndUpdate(data.pos(), Blocks.AIR.defaultBlockState());
        });
    }
}
