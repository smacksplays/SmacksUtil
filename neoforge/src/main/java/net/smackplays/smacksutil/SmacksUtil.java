package net.smackplays.smacksutil;


import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
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
import net.smackplays.smacksutil.menus.*;
import net.smackplays.smacksutil.networking.c2spacket.*;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacketHandler;
import net.smackplays.smacksutil.screens.*;

import static net.smackplays.smacksutil.Constants.*;

@Mod(MOD_ID)
public class SmacksUtil {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredItem<Item> BACKPACK_ITEM = ITEMS.register(C_BACKPACK_ITEM,
            () -> new BackpackItem(C_BACKPACK_PROPERTIES));
    public static final DeferredItem<Item> LARGE_BACKPACK_ITEM = ITEMS.register(C_LARGE_BACKPACK_ITEM,
            () -> new LargeBackpackItem(C_LARGE_BACKPACK_PROPERTIES));
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER1_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER1_ITEM,
            () -> new BackpackUpgradeItem(C_BACKPACK_UPGRADE_TIER1_PROPERTIES, 4));
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER2_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER2_ITEM,
            () -> new BackpackUpgradeItem(C_BACKPACK_UPGRADE_TIER2_PROPERTIES, 8));
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER3_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER3_ITEM,
            () -> new BackpackUpgradeItem(C_BACKPACK_UPGRADE_TIER3_PROPERTIES, 16));
    public static final DeferredItem<Item> LIGHT_WAND_ITEM = ITEMS.register(C_LIGHT_WAND_ITEM,
            () -> new LightWandItem(C_LIGHT_WAND_PROPERTIES));
    public static final DeferredItem<Item> AUTO_LIGHT_WAND_ITEM = ITEMS.register(C_AUTO_LIGHT_WAND_ITEM,
            () -> new AutoLightWandItem(C_AUTO_LIGHT_WAND_PROPERTIES));
    public static final DeferredItem<Item> MAGNET_ITEM = ITEMS.register(C_MAGNET_ITEM,
            () -> new MagnetItem(C_MAGNET_PROPERTIES));
    public static final DeferredItem<Item> ADVANCED_MAGNET_ITEM = ITEMS.register(C_ADVANCED_MAGNET_ITEM,
            () -> new AdvancedMagnetItem(C_ADVANCED_MAGNET_PROPERTIES));
    public static final DeferredItem<Item> MOB_CATCHER_ITEM = ITEMS.register(C_MOB_CATCHER_ITEM,
            () -> new MobCatcherItem(C_MOB_CATCHER_PROPERTIES));
    public static final DeferredItem<Item> ADVANCED_MOB_CATCHER_ITEM = ITEMS.register(C_ADVANCED_MOB_CATCHER_ITEM,
            () -> new AdvancedMobCatcherItem(C_ADVANCED_MOB_CATCHER_PROPERTIES));
    public static final DeferredItem<Item> ENCHANTING_TOOL_ITEM = ITEMS.register(C_ENCHANTING_TOOL_ITEM,
            () -> new EnchantingToolItem(C_ENCHANTING_TOOL_PROPERTIES));
    public static final DeferredItem<Item> TELEPORTATION_TABLET_ITEM = ITEMS.register(C_TELEPORTATION_TABLET_ITEM,
            () -> new TeleportationTablet(C_TELEPORTATION_TABLET_PROPERTIES));
    public static final DeferredItem<Item> EFFECT_TOTEM_ITEM = ITEMS.register(C_EFFECT_TOTEM_ITEM,
            () -> new EffectTotemItem(C_EFFECT_TOTEM_PROPERTIES));
    public static final DeferredHolder<MenuType<?>, MenuType<BackpackMenu>> BACKPACK_MENU =
            MENUS.register(C_BACKPACK_MENU, () -> new MenuType<>(BackpackMenu::createGeneric9x6, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<LargeBackpackMenu>> LARGE_BACKPACK_MENU =
            MENUS.register(C_LARGE_BACKPACK_MENU, () -> new MenuType<>(LargeBackpackMenu::createGeneric13x9, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<EnchantingToolMenu>> ENCHANTING_TOOL_MENU =
            MENUS.register(C_ENCHANTING_TOOL_MENU, () -> new MenuType<>(EnchantingToolMenu::create, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<TeleportationTabletMenu>> TELEPORTATION_TABLET_MENU =
            MENUS.register(C_TELEPORTATION_TABLET_MENU, () -> new MenuType<>(TeleportationTabletMenu::create, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<EffectTotemMenu>> EFFECT_TOTEM_MENU =
            MENUS.register(C_EFFECT_TOTEM_MENU, () -> new MenuType<>(EffectTotemMenu::create, FeatureFlags.DEFAULT_FLAGS));

    public SmacksUtil(IEventBus modEventBus, ModContainer modContainer) {
        CommonClass.init();
        LOG.info(modContainer.getModId());

        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        MENUS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        if (FMLEnvironment.dist.isClient()) {
            ClothConfigNeoForge.registerModsPage();
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
            event.accept(EFFECT_TOTEM_ITEM);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM.get(), ClientModEvents::dyedItemIteration);
            CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM.get(), ClientModEvents::dyedItemIteration);
        }

        private static InteractionResult dyedItemIteration(
                BlockState p_367064_, Level p_365282_, BlockPos p_365414_, Player p_364718_, InteractionHand p_362544_, ItemStack p_368695_
        ) {
            if (!p_368695_.is(ItemTags.DYEABLE)) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            } else if (!p_368695_.has(DataComponents.DYED_COLOR)) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            } else {
                if (!p_365282_.isClientSide) {
                    p_368695_.remove(DataComponents.DYED_COLOR);
                    p_364718_.awardStat(Stats.CLEAN_ARMOR);
                    LayeredCauldronBlock.lowerFillLevel(p_367064_, p_365282_, p_365414_);
                }

                return InteractionResult.SUCCESS;
            }
        }

        @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
        public static class RegisterMenuScreens {
            @SubscribeEvent
            public static void register(final RegisterMenuScreensEvent event) {
                event.register(BACKPACK_MENU.get(), AbstractBackpackScreen<BackpackMenu>::new);
                event.register(LARGE_BACKPACK_MENU.get(), AbstractLargeBackpackScreen<LargeBackpackMenu>::new);
                event.register(ENCHANTING_TOOL_MENU.get(), AbstractEnchantingToolScreen<EnchantingToolMenu>::new);
                event.register(TELEPORTATION_TABLET_MENU.get(), AbstractTeleportationTabletScreen<TeleportationTabletMenu>::new);
                event.register(EFFECT_TOTEM_MENU.get(), AbstractEffectTotemScreen<EffectTotemMenu>::new);
            }

        }

        @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
        public static class PacketEvents {
            @SubscribeEvent
            public static void register(final RegisterPayloadHandlersEvent event) {
                final PayloadRegistrar C2SRegistrar = event.registrar(MOD_ID)
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

                final PayloadRegistrar S2CRegistrar = event.registrar(MOD_ID)
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
}