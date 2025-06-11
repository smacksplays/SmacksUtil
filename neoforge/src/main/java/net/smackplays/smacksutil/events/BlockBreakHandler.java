package net.smackplays.smacksutil.events;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.smackplays.smacksutil.platform.Services;

import static net.smackplays.smacksutil.Constants.MOD_ID;

public class BlockBreakHandler {

    @EventBusSubscriber(modid = MOD_ID)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onBreakBlock(BlockEvent.BreakEvent event) {
            Services.S2C_PACKET_SENDER.sendToPlayerBlockBreakPacket((ServerPlayer) event.getPlayer(), event.getPos());
        }
    }
}
