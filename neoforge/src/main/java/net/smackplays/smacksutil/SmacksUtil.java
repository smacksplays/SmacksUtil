package net.smackplays.smacksutil;


import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.smackplays.smacksutil.config.ClothConfigNeoForge;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.networking.c2spacket.*;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacketHandler;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.screens.AbstractBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractEnchantingToolScreen;
import net.smackplays.smacksutil.screens.AbstractLargeBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractTeleportationTabletScreen;
import top.theillusivec4.curios.api.SlotTypeMessage;

import static net.smackplays.smacksutil.Constants.*;

@SuppressWarnings({"unused", "EmptyMethod"})
@Mod(MOD_ID)
public class SmacksUtil {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredItem<Item> BACKPACK_ITEM = ITEMS.register(C_BACKPACK_ITEM, BackpackItem::new);
    public static final DeferredItem<Item> LARGE_BACKPACK_ITEM = ITEMS.register(C_LARGE_BACKPACK_ITEM, LargeBackpackItem::new);
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER1_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER1_ITEM, () -> new BackpackUpgradeItem(4));
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER2_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER2_ITEM, () -> new BackpackUpgradeItem(8));
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER3_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER3_ITEM, () -> new BackpackUpgradeItem(16));
    public static final DeferredItem<Item> LIGHT_WAND_ITEM = ITEMS.register(C_LIGHT_WAND_ITEM, () -> new LightWandItem());
    public static final DeferredItem<Item> AUTO_LIGHT_WAND_ITEM = ITEMS.register(C_AUTO_LIGHT_WAND_ITEM, AutoLightWandItem::new);
    public static final DeferredItem<Item> MAGNET_ITEM = ITEMS.register(C_MAGNET_ITEM, () -> new MagnetItem());
    public static final DeferredItem<Item> ADVANCED_MAGNET_ITEM = ITEMS.register(C_ADVANCED_MAGNET_ITEM, AdvancedMagnetItem::new);
    public static final DeferredItem<Item> MOB_CATCHER_ITEM = ITEMS.register(C_MOB_CATCHER_ITEM, MobCatcherItem::new);
    public static final DeferredItem<Item> ADVANCED_MOB_CATCHER_ITEM = ITEMS.register(C_ADVANCED_MOB_CATCHER_ITEM, AdvancedMobCatcherItem::new);
    public static final DeferredItem<Item> ENCHANTING_TOOL_ITEM = ITEMS.register(C_ENCHANTING_TOOL_ITEM, EnchantingToolItem::new);
    public static final DeferredItem<Item> TELEPORTATION_TABLET_ITEM = ITEMS.register(C_TELEPORTATION_TABLET_ITEM, TeleportationTablet::new);
    public static final DeferredHolder<MenuType<?>, MenuType<BackpackMenu>> BACKPACK_MENU =
            MENUS.register(C_BACKPACK_MENU, () -> new MenuType<>(BackpackMenu::createGeneric9x6, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<LargeBackpackMenu>> LARGE_BACKPACK_MENU =
            MENUS.register(C_LARGE_BACKPACK_MENU, () -> new MenuType<>(LargeBackpackMenu::createGeneric13x9, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<EnchantingToolMenu>> ENCHANTING_TOOL_MENU =
            MENUS.register(C_ENCHANTING_TOOL_MENU, () -> new MenuType<>(EnchantingToolMenu::create, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<TeleportationTabletMenu>> TELEPORTATION_TABLET_MENU =
            MENUS.register(C_TELEPORTATION_TABLET_MENU, () -> new MenuType<>(TeleportationTabletMenu::create, FeatureFlags.DEFAULT_FLAGS));


    public SmacksUtil(IEventBus modEventBus, ModContainer modContainer) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        modEventBus.addListener(this::interModEnqueue);
        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        MENUS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        if (FMLEnvironment.dist.isClient()) {
            ClothConfigNeoForge.registerModsPage();
        }
    }
    public void interModEnqueue(InterModEnqueueEvent e){
        if (Services.PLATFORM.isModLoaded("curios")){
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("charm").size(1).build());
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("back").size(1).build());
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("hands").size(1).build());
        }
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BACKPACK_ITEM);
            event.accept(LARGE_BACKPACK_ITEM);
            event.accept(BACKPACK_UPGRADE_TIER1_ITEM);
            event.accept(BACKPACK_UPGRADE_TIER2_ITEM);
            event.accept(BACKPACK_UPGRADE_TIER3_ITEM);
            event.accept(LIGHT_WAND_ITEM);
            event.accept(AUTO_LIGHT_WAND_ITEM);
            event.accept(MAGNET_ITEM);
            event.accept(ADVANCED_MAGNET_ITEM);
            event.accept(MOB_CATCHER_ITEM);
            event.accept(ADVANCED_MOB_CATCHER_ITEM);
            event.accept(ENCHANTING_TOOL_ITEM);
            event.accept(TELEPORTATION_TABLET_ITEM);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM.get(), CauldronInteraction.DYED_ITEM);
        }
        @SubscribeEvent
        public static void colors(RegisterColorHandlersEvent.Item event) {
            event.register((backpack, layer) -> {
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
            }, BACKPACK_ITEM.get(), LARGE_BACKPACK_ITEM.get());
        }
        private static int calcColor(int col){
            int i = MapColor.Brightness.HIGH.modifier;
            return -16777216 | col;
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class RegisterMenuScreens {
        @SubscribeEvent
        public static void register(final RegisterMenuScreensEvent event) {
            event.register(SmacksUtil.BACKPACK_MENU.get(), AbstractBackpackScreen<BackpackMenu>::new);
            event.register(SmacksUtil.LARGE_BACKPACK_MENU.get(), AbstractLargeBackpackScreen<LargeBackpackMenu>::new);
            event.register(SmacksUtil.ENCHANTING_TOOL_MENU.get(), AbstractEnchantingToolScreen<EnchantingToolMenu>::new);
            event.register(SmacksUtil.TELEPORTATION_TABLET_MENU.get(), AbstractTeleportationTabletScreen<TeleportationTabletMenu>::new);
        }

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class PacketEvents {
        @SubscribeEvent
        public static void register(final RegisterPayloadHandlersEvent event) {

            final PayloadRegistrar C2SRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getVersion())
                    .optional();
            C2SRegistrar.playToServer(
                    C2SBackpackSortPacket.TYPE,
                    C2SBackpackSortPacket.STREAM_CODEC,
                    C2SBackpackSortPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2SBackpackOpenPacket.TYPE,
                    C2SBackpackOpenPacket.STREAM_CODEC,
                    C2SBackpackOpenPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2SToggleMagnetItemPacket.TYPE,
                    C2SToggleMagnetItemPacket.STREAM_CODEC,
                    C2SToggleMagnetItemPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2SToggleLightWandItemPacket.TYPE,
                    C2SToggleLightWandItemPacket.STREAM_CODEC,
                    C2SToggleLightWandItemPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2SEnchantPacket.TYPE,
                    C2SEnchantPacket.STREAM_CODEC,
                    C2SEnchantPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2SVeinMinerBreakPacket.TYPE,
                    C2SVeinMinerBreakPacket.STREAM_CODEC,
                    C2SVeinMinerBreakPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2SSetBlockAirPacket.TYPE,
                    C2SSetBlockAirPacket.STREAM_CODEC,
                    C2SSetBlockAirPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2SInteractEntityPacket.TYPE,
                    C2SInteractEntityPacket.STREAM_CODEC,
                    C2SInteractEntityPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2STeleportationPacket.TYPE,
                    C2STeleportationPacket.STREAM_CODEC,
                    C2STeleportationPacketHandler::handle
                    );

            C2SRegistrar.playToServer(
                    C2STeleportationNBTPacket.TYPE,
                    C2STeleportationNBTPacket.STREAM_CODEC,
                    C2STeleportationNBTPacketHandler::handle
                    );

            final PayloadRegistrar S2CRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getVersion())
                    .optional();
            S2CRegistrar.playToClient(
                    S2CBlockBreakPacket.TYPE,
                    S2CBlockBreakPacket.STREAM_CODEC,
                    S2CBlockBreakPacketHandler::handle
            );
        }
    }

}