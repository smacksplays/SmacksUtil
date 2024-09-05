package net.smackplays.smacksutil;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.payload.BreakPayload;
import dev.emi.trinkets.payload.SyncInventoryPayload;
import dev.emi.trinkets.payload.SyncSlotsPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.events.veinminer.PlayerBlockBreak;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.networking.c2spacket.*;
import net.smackplays.smacksutil.networking.s2cpacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.trinkets.Trinkets;

import java.util.Set;

import static net.smackplays.smacksutil.Constants.*;

@SuppressWarnings("unused")
public class SmacksUtil implements ModInitializer {

    public static final Item BACKPACK_ITEM = new BackpackItem();
    public static final Item LARGE_BACKPACK_ITEM = new LargeBackpackItem();
    public static final Item BACKPACK_UPGRADE_TIER1_ITEM = new BackpackUpgradeItem(4);
    public static final Item BACKPACK_UPGRADE_TIER2_ITEM = new BackpackUpgradeItem(8);
    public static final Item BACKPACK_UPGRADE_TIER3_ITEM = new BackpackUpgradeItem(16);
    public static final Item LIGHT_WAND_ITEM = new LightWandItem();
    public static final Item AUTO_LIGHT_WAND_ITEM = new AutoLightWandItem();
    public static final Item MAGNET_ITEM = new MagnetItem();
    public static final Item ADVANCED_MAGNET_ITEM = new AdvancedMagnetItem();
    public static final Item MOB_CATCHER_ITEM = new MobCatcherItem();
    public static final Item ADVANCED_MOB_CATCHER_ITEM = new AdvancedMobCatcherItem();
    public static final Item ENCHANTING_TOOL_ITEM = new FabricEnchantingToolItem();
    public static final Item TELEPORTATION_TABLET_ITEM = new TeleportationTablet();
    public static final MenuType<BackpackMenu> BACKPACK_MENU = new ExtendedScreenHandlerType<>(BackpackMenu::createGeneric9x6, ByteBufCodecs.VECTOR3F);
    public static final MenuType<LargeBackpackMenu> LARGE_BACKPACK_MENU = new ExtendedScreenHandlerType<>(LargeBackpackMenu::createGeneric13x9, ByteBufCodecs.VECTOR3F);
    public static final MenuType<EnchantingToolMenu> ENCHANTING_TOOL_MENU = new ExtendedScreenHandlerType<>(EnchantingToolMenu::create, ByteBufCodecs.VECTOR3F);
    public static final MenuType<TeleportationTabletMenu> TELEPORTATION_TABLET_MENU = new ExtendedScreenHandlerType<>(TeleportationTabletMenu::create, ByteBufCodecs.VECTOR3F);

    @Override
    public void onInitialize() {
        LOG.info("Hello Fabric world!");
        CommonClass.init();
        PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());

        Registry.register(BuiltInRegistries.MENU, C_LARGE_BACKPACK_MENU_RL, LARGE_BACKPACK_MENU);
        Registry.register(BuiltInRegistries.MENU, C_BACKPACK_MENU_RL, BACKPACK_MENU);
        Registry.register(BuiltInRegistries.MENU, C_ENCHANTING_TOOL_MENU_RL, ENCHANTING_TOOL_MENU);
        Registry.register(BuiltInRegistries.MENU, C_TELEPORTATION_TABLET_MENU_RL, TELEPORTATION_TABLET_MENU);

        registerItem(C_BACKPACK_ITEM_RL, BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);

        registerItem(C_LARGE_BACKPACK_ITEM_RL, LARGE_BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);

        registerItem(C_BACKPACK_UPGRADE_TIER1_ITEM_RL, BACKPACK_UPGRADE_TIER1_ITEM);
        registerItem(C_BACKPACK_UPGRADE_TIER2_ITEM_RL, BACKPACK_UPGRADE_TIER2_ITEM);
        registerItem(C_BACKPACK_UPGRADE_TIER3_ITEM_RL, BACKPACK_UPGRADE_TIER3_ITEM);

        registerItem(C_ENCHANTING_TOOL_ITEM_RL, ENCHANTING_TOOL_ITEM);

        registerItem(C_LIGHT_WAND_ITEM_RL, LIGHT_WAND_ITEM);
        registerItem(C_AUTO_LIGHT_WAND_ITEM_RL, AUTO_LIGHT_WAND_ITEM);
        registerItem(C_MAGNET_ITEM_RL, MAGNET_ITEM);
        registerItem(C_ADVANCED_MAGNET_ITEM_RL, ADVANCED_MAGNET_ITEM);
        registerItem(C_MOB_CATCHER_ITEM_RL, MOB_CATCHER_ITEM);
        registerItem(C_ADVANCED_MOB_CATCHER_ITEM_RL, ADVANCED_MOB_CATCHER_ITEM);
        registerItem(C_TELEPORTATION_TABLET_ITEM_RL, TELEPORTATION_TABLET_ITEM);

        PayloadTypeRegistry.playC2S().register(C2SEnchantPacket.TYPE, C2SEnchantPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SEnchantPacket.TYPE, C2SEnchantPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2SBackpackSortPacket.TYPE, C2SBackpackSortPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SBackpackSortPacket.TYPE, C2SBackpackSortPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2SBackpackOpenPacket.TYPE, C2SBackpackOpenPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SBackpackOpenPacket.TYPE, C2SBackpackOpenPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2SSetBlockAirPacket.TYPE, C2SSetBlockAirPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SSetBlockAirPacket.TYPE, C2SSetBlockAirPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2STeleportationPacket.TYPE, C2STeleportationPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2STeleportationPacket.TYPE, C2STeleportationPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2STeleportationNBTPacket.TYPE, C2STeleportationNBTPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2STeleportationNBTPacket.TYPE, C2STeleportationNBTPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2SInteractEntityPacket.TYPE, C2SInteractEntityPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SInteractEntityPacket.TYPE, C2SInteractEntityPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2SVeinMinerBreakPacket.TYPE, C2SVeinMinerBreakPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SVeinMinerBreakPacket.TYPE, C2SVeinMinerBreakPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2SToggleMagnetItemPacket.TYPE, C2SToggleMagnetItemPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SToggleMagnetItemPacket.TYPE, C2SToggleMagnetItemPacketHandler::handle);
        PayloadTypeRegistry.playC2S().register(C2SToggleLightWandItemPacket.TYPE, C2SToggleLightWandItemPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SToggleLightWandItemPacket.TYPE, C2SToggleLightWandItemPacketHandler::handle);

        PayloadTypeRegistry.playS2C().register(S2CBlockBreakPacket.TYPE, S2CBlockBreakPacket.STREAM_CODEC);

        if (Services.PLATFORM.isModLoaded("trinkets")){
            Trinkets.init();
        }

    }

    private void registerItem(ResourceLocation resourceLocation, Item item) {
        Registry.register(BuiltInRegistries.ITEM, resourceLocation, item);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(item));
    }

    private void handleEnchantRequest(MinecraftServer server, ServerPlayer player,
                                      ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            AbstractContainerMenu screenHandler = player.containerMenu;
            //ItemStack item = buf.readItem();
            //screenHandler.slots.getFirst().set(item);
        });
    }

    private void handleBackpackSortRequest(MinecraftServer server, ServerPlayer player,
                                   ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            AbstractContainerMenu screenHandler = player.containerMenu;
            /*ItemStack stack = buf.readItem();
            if (stack.getItem() instanceof LargeBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu) {
                lBackpackMenu.sort();
            } else if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu) {
                backpackMenu.sort();
            }*/
        });
    }

    private void handleBackpackOpenRequest(MinecraftServer server, ServerPlayer player,
                                   ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            int slot = buf.readInt();
            ItemStack stack = player.inventoryMenu.slots.get(slot).getItem();
            if (stack.getItem() instanceof AbstractBackpackItem item) {
                player.openMenu(item.createScreenHandlerFactory(stack));
            }
        });
    }

    private void handleSetBlockAirRequest(MinecraftServer server, ServerPlayer player,
                                          ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            Level world = player.level();
            BlockPos pos = buf.readBlockPos();
            if (world.isClientSide) return;
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        });
    }

    private void handleTeleportNBTRequest(MinecraftServer server, ServerPlayer player,
                                          ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            /*ItemStack stack = buf.readItem();
            Vec3 pos = buf.readVec3();
            float xRot = buf.readFloat();
            float yRot = buf.readFloat();
            String name = buf.readUtf();
            String dim = buf.readUtf();
            boolean remove = buf.readBoolean();


            CompoundTag tag = stack.getOrCreateTag();
            ListTag posTag = (ListTag)tag.get("Positions");
            if (posTag == null) {
                posTag = new ListTag();
            }
            CompoundTag teleportTag = new CompoundTag();
            teleportTag.putString("name", name);
            teleportTag.putDouble("x_pos", pos.x());
            teleportTag.putDouble("y_pos", pos.y());
            teleportTag.putDouble("z_pos", pos.z());
            teleportTag.putDouble("x_rot", xRot);
            teleportTag.putDouble("y_rot", yRot);
            teleportTag.putString("dim", dim);
            if (remove){
                for (int i = 0; i < posTag.size(); i++){
                    CompoundTag t = (CompoundTag) posTag.get(i);
                    if (t.contains("name")){
                        String n = t.getString("name");
                        if (n.equals(name)){
                            posTag.remove(i);
                            tag.put("Positions", posTag);
                            stack.setTag(tag);
                            player.getInventory().setItem(player.getInventory().selected, stack);
                            player.inventoryMenu.broadcastChanges();
                            return;
                        }
                    }
                }
            } else {
                posTag.add(teleportTag);
            }
            tag.put("Positions", posTag);
            stack.setTag(tag);
            player.getInventory().setItem(player.getInventory().selected, stack);
            player.inventoryMenu.broadcastChanges();*/
        });
    }

    private void handleTeleportRequest(MinecraftServer server, ServerPlayer player,
                                       ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            ResourceKey<?> tempKey = buf.readRegistryKey();
            ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
            Vec3 pos = buf.readVec3();
            float xRot = buf.readFloat();
            float yRot = buf.readFloat();

            ServerLevel serverLevel = server.getLevel(levelKey);
            //player.teleportTo(pos.x, pos.y, pos.z);
            if (serverLevel != null) {
                player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
            }
        });
    }

    private void handleInteractEntityRequest(MinecraftServer server, ServerPlayer player,
                                             ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            /*Level world = player.level();
            ItemStack stack = buf.readItem();
            UUID entityUUID = buf.readUUID();
            AABB aabb = new AABB(player.position().add(-5,-5,-5), player.position().add(5,5,5));
            List<LivingEntity> entityList = world.getEntitiesOfClass(LivingEntity.class, aabb, entity -> true);
            LivingEntity livingEntity = null;
            for  (LivingEntity e : entityList){
                if (e.getUUID().equals(entityUUID)){
                    livingEntity = e;
                }
            }
            if (livingEntity == null) return;
            InteractionHand hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            if (stack.getItem() instanceof MobCatcherItem mobItem){
                if (mobItem.pickupLivingEntity(stack, player, livingEntity, hand)){
                    livingEntity.remove(Entity.RemovalReason.KILLED);
                }
            }
            if (stack.getItem() instanceof AdvancedMobCatcherItem mobItem){
                if (mobItem.pickupLivingEntity(stack, player, livingEntity, hand)){
                    livingEntity.remove(Entity.RemovalReason.KILLED);
                }
            }*/
        });
    }

    private void handleVeinMinerBreakRequest(MinecraftServer server, ServerPlayer player,
                                             ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            /*Level world = player.level();
            ItemStack mainHandStack = buf.readItem();
            BlockPos curr = buf.readBlockPos();
            boolean isCreative = buf.readBoolean();
            boolean replaceSeeds = buf.readBoolean();
            BlockState currBlockState = world.getBlockState(curr);

            world.setBlockAndUpdate(curr, Blocks.AIR.defaultBlockState());
            if (!isCreative) {
                BlockEntity currBlockEntity = currBlockState.hasBlockEntity() ? world.getBlockEntity(curr) : null;
                Block.dropResources(currBlockState, world, curr, currBlockEntity, null, ItemStack.EMPTY);
                if (mainHandStack.isDamageableItem()) {
                    mainHandStack.hurt(1, player.getRandom(), player);
                }
            }
            if (replaceSeeds) {
                world.setBlockAndUpdate(curr, currBlockState.getBlock().defaultBlockState());
            }*/
        });
    }

    private void handleToggleMagnetRequest(MinecraftServer server, ServerPlayer player,
                                           ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            int slot = buf.readInt();
            ItemStack stack = player.inventoryMenu.slots.get(slot).getItem();
            if (stack.getItem() instanceof MagnetItem item) {
                item.toggle(stack, player);
            }
        });
    }

    private void handleToggleLightWandRequest(MinecraftServer server, ServerPlayer player,
                                           ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            int slot = buf.readInt();
            ItemStack stack = player.inventoryMenu.slots.get(slot).getItem();
            if (stack.getItem() instanceof AutoLightWandItem item) {
                item.toggle(stack, player);
            }
        });
    }
}
