package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.material.MapColor;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacketHandler;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.screens.AbstractBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractEnchantingToolScreen;
import net.smackplays.smacksutil.screens.AbstractLargeBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractTeleportationTabletScreen;

import static net.smackplays.smacksutil.SmacksUtil.*;

@SuppressWarnings("unused")
public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Services.KEY_HANDLER.register();

        MenuScreens.register(BACKPACK_MENU, AbstractBackpackScreen<BackpackMenu>::new);
        MenuScreens.register(LARGE_BACKPACK_MENU, AbstractLargeBackpackScreen<LargeBackpackMenu>::new);
        MenuScreens.register(ENCHANTING_TOOL_MENU, AbstractEnchantingToolScreen<EnchantingToolMenu>::new);
        MenuScreens.register(TELEPORTATION_TABLET_MENU, AbstractTeleportationTabletScreen<TeleportationTabletMenu>::new);

        ColorProviderRegistry.ITEM.register((backpack, layer) -> {
            if (layer > 1 || !(backpack.getItem() instanceof AbstractBackpackItem)) {
                return -1;
            }
            if (layer == 0) {
                DyedItemColor data = backpack.get(DataComponents.DYED_COLOR);
                if (data != null){
                    return calcColor(data.rgb());
                }
                return calcColor(DyeColor.WHITE.getMapColor().col);
            }
            return -1;
        }, BACKPACK_ITEM, LARGE_BACKPACK_ITEM);

        ClientPlayNetworking.registerGlobalReceiver(S2CBlockBreakPacket.TYPE, S2CBlockBreakPacketHandler::handle);
    }

    public void handleServerBlockBreak (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender){
        client.execute(() -> {
            if (Services.KEY_HANDLER.isVeinKeyDown()){
                Services.VEIN_MINER.veinMiner(client.level, client.player, buf.readBlockPos());
            }
        });
    }
    private static int calcColor(int col){
        int i = MapColor.Brightness.HIGH.modifier;
        return -16777216 | col;
    }

}
