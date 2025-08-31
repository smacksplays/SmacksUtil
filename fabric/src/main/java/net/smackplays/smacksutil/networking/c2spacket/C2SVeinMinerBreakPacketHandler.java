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
        BlockPos sourcePos = new BlockPos((int)data.sourcePos().x, (int)data.sourcePos().y, (int)data.sourcePos().z);
        BlockPos curr = new BlockPos((int)data.curr().x, (int)data.curr().y, (int)data.curr().z);
        C2SCommonVeinMinerBreakPacketHandler.handle(context.player(), context.player().level(), sourcePos, curr, data.isCreative(), data.replaceSeeds());
    }
}
