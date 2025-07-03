package net.smackplays.smacksutil.platform;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.smackplays.smacksutil.platform.services.IKeyHandler;

import static net.smackplays.smacksutil.Constants.MOD_ID;

public class NeoForgeKeyHandler implements IKeyHandler {
    public static KeyMapping veinKey;
    public static KeyMapping veinPreviewKey;
    public static KeyMapping fastPlaceKey;
    public static KeyMapping exactMatchKey;
    public static KeyMapping openBackpackKey;
    public static KeyMapping toggleMagnetKey;
    public static KeyMapping toggleLightWandKey;

    public static boolean veinKeyDown;

    @Override
    public void toggleMagnetConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
//            if (Services.PLATFORM.isModLoaded("curios")){
//                List<SlotResult> results = CuriosApi.getCuriosHelper().findCurios(player, "charm");
//                if (!results.isEmpty()){
//                    ItemStack stack = results.getFirst().stack();
//                    if ((stack.is(Services.PLATFORM.getMagnetItem()) || stack.is(Services.PLATFORM.getAdvancedMagnetItem())) && Services.C2S_PACKET_SENDER != null){
//                        Services.C2S_PACKET_SENDER.ToggleMagnetItemPacket(-1);
//                        return;
//                    }
//                }
//            }
            NonNullList<Slot> slots = player.inventoryMenu.slots;
            for (int i = slots.size() - 1; i >= 0; i--){
                ItemStack stack = slots.get(i).getItem();
                if ((stack.is(Services.PLATFORM.getAdvancedMagnetItem()) || stack.is(Services.PLATFORM.getMagnetItem()))  && Services.C2S_PACKET_SENDER != null){
                    Services.C2S_PACKET_SENDER.ToggleMagnetItemPacket(i);
                    return;
                }
            }
        }
    }

    @Override
    public void toggleLightWandConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
//            if (Services.PLATFORM.isModLoaded("curios")){
//                List<SlotResult> results = CuriosApi.getCuriosHelper().findCurios(player, "hands");
//                if (!results.isEmpty()){
//                    ItemStack stack = results.getFirst().stack();
//                    if (stack.is(Services.PLATFORM.getAutoWandItem()) && Services.C2S_PACKET_SENDER != null){
//                        Services.C2S_PACKET_SENDER.ToggleLightWandItemPacket(-1);
//                        return;
//                    }
//                }
//            }
            NonNullList<Slot> slots = player.inventoryMenu.slots;
            for (int i = slots.size() - 1; i >= 0; i--){
                ItemStack stack = slots.get(i).getItem();
                if (stack.is(Services.PLATFORM.getAutoWandItem()) && Services.C2S_PACKET_SENDER != null){
                    Services.C2S_PACKET_SENDER.ToggleLightWandItemPacket(i);
                    return;
                }
            }
        }
    }

    @Override
    public void openBackpackConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
//            if (Services.PLATFORM.isModLoaded("curios")){
//                List<SlotResult> results = CuriosApi.getCuriosHelper().findCurios(player, "back");
//                if (!results.isEmpty()){
//                    ItemStack stack = results.getFirst().stack();
//                    if ((stack.is(Services.PLATFORM.getLargeBackackItem()) || stack.is(Services.PLATFORM.getBackackItem())) && Services.C2S_PACKET_SENDER != null){
//                        Services.C2S_PACKET_SENDER.BackpackOpenPacket(-1);
//                        return;
//                    }
//                }
//            }
            NonNullList<Slot> slots = player.inventoryMenu.slots;
            for (int i = 0; i <= 8; i++){
                ItemStack stack = slots.get(i).getItem();
                if ((stack.is(Services.PLATFORM.getLargeBackpackItem()) || stack.is(Services.PLATFORM.getBackpackItem())) && Services.C2S_PACKET_SENDER != null){
                    Services.C2S_PACKET_SENDER.BackpackOpenPacket(i);
                    return;
                }
            }
            for (int i = slots.size() - 1; i >= 0; i--){
                ItemStack stack = slots.get(i).getItem();
                if ((stack.is(Services.PLATFORM.getLargeBackpackItem()) || stack.is(Services.PLATFORM.getBackpackItem())) && Services.C2S_PACKET_SENDER != null){
                    Services.C2S_PACKET_SENDER.BackpackOpenPacket(i);
                    return;
                }
            }
        }
    }

    @Override
    public boolean isVeinKeyDown() {
        return veinKeyDown;
    }

    @Override
    public void register() {
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

            veinKey = IVeinKey;
            veinPreviewKey = IVeinPreviewKey;
            fastPlaceKey = IFastPlaceKey;
            exactMatchKey = IExactMatchKey;
            openBackpackKey = IOpenBackpackKey;
            toggleMagnetKey = IToggleMagnetKey;
            toggleLightWandKey = IToggleLightWandKey;


            event.register(veinKey);
            event.register(veinPreviewKey);
            event.register(fastPlaceKey);
            event.register(exactMatchKey);
            event.register(openBackpackKey);
            event.register(toggleMagnetKey);
            event.register(toggleLightWandKey);
        }
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            assert Services.KEY_HANDLER != null;
            Services.KEY_HANDLER.veinPreviewConsume(veinPreviewKey, player);
            Services.KEY_HANDLER.fastPlaceConsume(fastPlaceKey, player);
            Services.KEY_HANDLER.exactMatchConsume(exactMatchKey, player);
            Services.KEY_HANDLER.openBackpackConsume(openBackpackKey, player);
            Services.KEY_HANDLER.toggleMagnetConsume(toggleMagnetKey, player);
            Services.KEY_HANDLER.toggleLightWandConsume(toggleLightWandKey, player);
        }
        @SubscribeEvent
        public static void onKeyInput(InputEvent.MouseButton.Post event) {
            veinKeyDown = veinKey.isDown();
        }

    }
}
