package net.smackplays.smacksutil.networking.s2chandlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.smackplays.smacksutil.CommonClass;
import net.smackplays.smacksutil.platform.Services;

public class S2CCommonBlockBreakPacketHandler {

    public static void handle(BlockPos pos) {
        Player player = Minecraft.getInstance().player;
        assert Services.KEY_HANDLER != null;
        if (Services.KEY_HANDLER.isVeinKeyDown() && player != null){
            CommonClass.veinMiner.veinMiner(player.level(), player, pos);
        }
    }
    public static void handle(LocalPlayer player, BlockPos pos) {
        assert Services.KEY_HANDLER != null;
        if (Services.KEY_HANDLER.isVeinKeyDown() && player != null){
            CommonClass.veinMiner.veinMiner(player.level(), player, pos);
        }
    }
}
