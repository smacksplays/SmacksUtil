package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class C2SSetBlockAirPacketHandler {
    public static void handle(C2SSetBlockAirPacket data, IPayloadContext context) {
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            Level world = context.player().level();
            BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        });
    }
}
