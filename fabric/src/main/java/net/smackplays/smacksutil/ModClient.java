package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.smackplays.smacksutil.menus.*;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacketHandler;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.screens.*;

import static net.smackplays.smacksutil.SmacksUtil.*;

@SuppressWarnings("unused")
public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        assert Services.KEY_HANDLER != null;
        Services.KEY_HANDLER.register();

        MenuScreens.register(BACKPACK_MENU, AbstractBackpackScreen<BackpackMenu>::new);
        MenuScreens.register(LARGE_BACKPACK_MENU, AbstractLargeBackpackScreen<LargeBackpackMenu>::new);
        MenuScreens.register(ENCHANTING_TOOL_MENU, AbstractEnchantingToolScreen<EnchantingToolMenu>::new);
        MenuScreens.register(TELEPORTATION_TABLET_MENU, AbstractTeleportationTabletScreen<TeleportationTabletMenu>::new);
        MenuScreens.register(EFFECT_TOTEM_MENU, AbstractEffectTotemScreen<EffectTotemMenu>::new);

//        ColorProviderRegistry.BLOCK.register((backpack, layer) -> {
//            if (layer > 1 || !(backpack.getBlock().asItem() instanceof AbstractBackpackItem)) {
//                return -1;
//            }
//            if (layer == 0) {
//                DyedItemColor data = backpack.get(DataComponents.DYED_COLOR);
//                if (data != null){
//                   return -16777216 | data.rgb();
//                }
//                return -16777216 | DyeColor.WHITE.getMapColor().col;
//            }
//            return -1;
//        }, BACKPACK_ITEM, LARGE_BACKPACK_ITEM);

        PayloadTypeRegistry.playS2C().register(S2CBlockBreakPacket.TYPE, S2CBlockBreakPacket.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(S2CBlockBreakPacket.TYPE, S2CBlockBreakPacketHandler::handle);
    }
}
