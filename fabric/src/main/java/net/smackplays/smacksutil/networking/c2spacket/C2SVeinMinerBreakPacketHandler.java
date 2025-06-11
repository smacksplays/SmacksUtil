package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonVeinMinerBreakPacketHandler;

/**
 * Class C2SVeinMinerBreakPacketHandler */
public class C2SVeinMinerBreakPacketHandler {
    /** Constructor*/
    public C2SVeinMinerBreakPacketHandler(){

    }
    /** Handle VeinMinerBreakPacket
     * @param data data
     * @param context context*/
    public static void handle(C2SVeinMinerBreakPacket data, ServerPlayNetworking.Context context) {
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);

        C2SCommonVeinMinerBreakPacketHandler.handle(context.player(), context.player().level(), pos, data.isCreative(), data.replaceSeeds());
    }
}
