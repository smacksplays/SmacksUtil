package net.smackplays.smacksutil;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Collection of constant values */
public class Constants {
    /** MOD_ID */
    public static final String MOD_ID = "smacksutil";
    /** MOD_NAME */
    public static final String MOD_NAME = "SmacksUtil";
    /** Logger */
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // --------------------------------- KeyMappings
    /** String for KeyMapping Category SMACKSUTIL */
    public static final String KEY_CATEGORY_SMACKSUTIL = "key.category.smacksutil";
    /** String for KeyMapping VEINACTIVATE */
    public static final String KEY_SMACKSUTIL_VEINACTIVATE = "key.smacksutil.veinactivate";
    /** String for KeyMapping VEINPREVIEW */
    public static final String KEY_SMACKSUTIL_VEINPREVIEW = "key.smacksutil.veinpreview";
    /** String for KeyMapping FASTPLACE */
    public static final String KEY_SMACKSUTIL_FASTPLACE = "key.smacksutil.fastplace";
    /** String for KeyMapping EXACTMATCH */
    public static final String KEY_SMACKSUTIL_EXACTMATCH = "key.smacksutil.exactmatch";
    /** String for KeyMapping OPEN_BACKPACK */
    public static final String KEY_SMACKSUTIL_OPEN_BACKPACK = "key.smacksutil.open_backpack";
    /** String for KeyMapping TOGGLE_MAGNET */
    public static final String KEY_SMACKSUTIL_TOGGLE_MAGNET = "key.smacksutil.toggle_magnet";
    /** String for KeyMapping TOGGLE_LIGHT_WAND */
    public static final String KEY_SMACKSUTIL_TOGGLE_LIGHT_WAND = "key.smacksutil.toggle_light_wand";
    
    // --------------------------------- Items
    /** String for Item BACKPACK_ITEM */
    public static final String C_BACKPACK_ITEM = "backpack_item";
    /** ResourceLocation for Item BACKPACK_ITEM */
    public static final ResourceLocation C_BACKPACK_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_ITEM);
    /** String for Item LARGE_BACKPACK_ITEM */
    public static final String C_LARGE_BACKPACK_ITEM = "large_backpack_item";
    /** ResourceLocation for Item LARGE_BACKPACK_ITEM */
    public static final ResourceLocation C_LARGE_BACKPACK_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_LARGE_BACKPACK_ITEM);
    /** String for Item BACKPACK_UPGRADE_TIER1_ITEM */
    public static final String C_BACKPACK_UPGRADE_TIER1_ITEM = "backpack_upgrade_tier1_item";
    /** ResourceLocation for Item BACKPACK_UPGRADE_TIER1_ITEM */
    public static final ResourceLocation C_BACKPACK_UPGRADE_TIER1_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_UPGRADE_TIER1_ITEM);
    /** String for Item BACKPACK_UPGRADE_TIER2_ITEM */
    public static final String C_BACKPACK_UPGRADE_TIER2_ITEM = "backpack_upgrade_tier2_item";
    /** ResourceLocation for Item BACKPACK_UPGRADE_TIER2_ITEM */
    public static final ResourceLocation C_BACKPACK_UPGRADE_TIER2_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_UPGRADE_TIER2_ITEM);
    /** String for Item BACKPACK_UPGRADE_TIER3_ITEM */
    public static final String C_BACKPACK_UPGRADE_TIER3_ITEM = "backpack_upgrade_tier3_item";
    /** ResourceLocation for Item BACKPACK_UPGRADE_TIER3_ITEM */
    public static final ResourceLocation C_BACKPACK_UPGRADE_TIER3_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_UPGRADE_TIER3_ITEM);
    /** String for Item LIGHT_WAND_ITEM */
    public static final String C_LIGHT_WAND_ITEM = "light_wand_item";
    /** ResourceLocation for Item LIGHT_WAND_ITEM */
    public static final ResourceLocation C_LIGHT_WAND_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_LIGHT_WAND_ITEM);
    /** String for Item AUTO_LIGHT_WAND_ITEM */
    public static final String C_AUTO_LIGHT_WAND_ITEM = "auto_light_wand_item";
    /** ResourceLocation for Item AUTO_LIGHT_WAND_ITEM */
    public static final ResourceLocation C_AUTO_LIGHT_WAND_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_AUTO_LIGHT_WAND_ITEM);
    /** String for Item MAGNET_ITEM */
    public static final String C_MAGNET_ITEM = "magnet_item";
    /** ResourceLocation for Item MAGNET_ITEM */
    public static final ResourceLocation C_MAGNET_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_MAGNET_ITEM);
    /** String for Item ADVANCED_MAGNET_ITEM */
    public static final String C_ADVANCED_MAGNET_ITEM = "advanced_magnet_item";
    /** ResourceLocation for Item ADVANCED_MAGNET_ITEM */
    public static final ResourceLocation C_ADVANCED_MAGNET_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ADVANCED_MAGNET_ITEM);
    /** String for Item MOB_CATCHER_ITEM */
    public static final String C_MOB_CATCHER_ITEM = "mob_catcher_item";
    /** ResourceLocation for Item MOB_CATCHER_ITEM */
    public static final ResourceLocation C_MOB_CATCHER_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_MOB_CATCHER_ITEM);
    /** String for Item ADVANCED_MOB_CATCHER_ITEM */
    public static final String C_ADVANCED_MOB_CATCHER_ITEM = "advanced_mob_catcher_item";
    /** ResourceLocation for Item ADVANCED_MOB_CATCHER_ITEM */
    public static final ResourceLocation C_ADVANCED_MOB_CATCHER_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ADVANCED_MOB_CATCHER_ITEM);
    /** String for Item ENCHANTING_TOOL_ITEM */
    public static final String C_ENCHANTING_TOOL_ITEM = "enchanting_tool_item";
    /** ResourceLocation for Item ENCHANTING_TOOL_ITEM */
    public static final ResourceLocation C_ENCHANTING_TOOL_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANTING_TOOL_ITEM);
    /** String for Item TELEPORTATION_TABLET_ITEM */
    public static final String C_TELEPORTATION_TABLET_ITEM = "teleportation_tablet_item";
    /** ResourceLocation for Item TELEPORTATION_TABLET_ITEM */
    public static final ResourceLocation C_TELEPORTATION_TABLET_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TELEPORTATION_TABLET_ITEM);
    /** String for Item EFFECT_TOTEM_ITEM */
    public static final String C_EFFECT_TOTEM_ITEM = "effect_totem_item";
    /** ResourceLocation for Item EFFECT_TOTEM_ITEM */
    public static final ResourceLocation C_EFFECT_TOTEM_ITEM_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_EFFECT_TOTEM_ITEM);

    // --------------------------------- Item.Properties
    /** Item.Properties for Item BACKPACK_ITEM */
    public static final Item.Properties C_BACKPACK_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_ITEM)))
            .humanoidArmor(ArmorMaterials.LEATHER, ArmorType.CHESTPLATE)
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    /** Item.Properties for Item LARGE_BACKPACK_ITEM */
    public static final Item.Properties C_LARGE_BACKPACK_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_LARGE_BACKPACK_ITEM)))
            .humanoidArmor(ArmorMaterials.LEATHER, ArmorType.CHESTPLATE)
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item BACKPACK_UPGRADE_TIER1_ITEM */
    public static final Item.Properties C_BACKPACK_UPGRADE_TIER1_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_UPGRADE_TIER1_ITEM)))
            .stacksTo(1);
    /** Item.Properties for Item BACKPACK_UPGRADE_TIER2_ITEM */
    public static final Item.Properties C_BACKPACK_UPGRADE_TIER2_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_UPGRADE_TIER2_ITEM)))
            .stacksTo(1);
    /** Item.Properties for Item BACKPACK_UPGRADE_TIER3_ITEM */
    public static final Item.Properties C_BACKPACK_UPGRADE_TIER3_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_UPGRADE_TIER3_ITEM)))
            .stacksTo(1);
    /** Item.Properties for Item LIGHT_WAND_ITEM */
    public static final Item.Properties C_LIGHT_WAND_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_LIGHT_WAND_ITEM)))
            .rarity(Rarity.EPIC)
            .durability(200);
    /** Item.Properties for Item AUTO_LIGHT_WAND_ITEM */
    public static final Item.Properties C_AUTO_LIGHT_WAND_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_AUTO_LIGHT_WAND_ITEM)))
            .rarity(Rarity.EPIC)
            .durability(2000)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item MAGNET_ITEM */
    public static final Item.Properties C_MAGNET_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_MAGNET_ITEM)))
            .rarity(Rarity.EPIC)
            .stacksTo(1)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item ADVANCED_MAGNET_ITEM */
    public static final Item.Properties C_ADVANCED_MAGNET_PROPERTIES = new Item.Properties()
            .setId( ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ADVANCED_MAGNET_ITEM)))
            .rarity(Rarity.EPIC)
            .stacksTo(1)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item MOB_CATCHER_ITEM */
    public static final Item.Properties C_MOB_CATCHER_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_MOB_CATCHER_ITEM)))
            .rarity(Rarity.EPIC)
            .stacksTo(1)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item ADVANCED_MOB_CATCHER_ITEM */
    public static final Item.Properties C_ADVANCED_MOB_CATCHER_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ADVANCED_MOB_CATCHER_ITEM)))
            .rarity(Rarity.EPIC)
            .stacksTo(1)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item ENCHANTING_TOOL_ITEM */
    public static final Item.Properties C_ENCHANTING_TOOL_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANTING_TOOL_ITEM)))
            .rarity(Rarity.EPIC)
            .stacksTo(1)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item TELEPORTATION_TABLET_ITEM */
    public static final Item.Properties C_TELEPORTATION_TABLET_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TELEPORTATION_TABLET_ITEM)))
            .rarity(Rarity.EPIC)
            .stacksTo(1)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    /** Item.Properties for Item EFFECT_TOTEM_ITEM */
    public static final Item.Properties C_EFFECT_TOTEM_PROPERTIES = new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, C_EFFECT_TOTEM_ITEM)))
            .rarity(Rarity.EPIC)
            .stacksTo(1)
            .component(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

    // --------------------------------- Menus
    /** String for Menu BACKPACK_MENU */
    public static final String C_BACKPACK_MENU = "backpack_menu";
    /**ResourceLocation for Menu BACKPACK_MENU*/
    public static final ResourceLocation C_BACKPACK_MENU_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_MENU);
    /** String for Menu LARGE_BACKPACK_MENU */
    public static final String C_LARGE_BACKPACK_MENU = "large_backpack_menu";
    /**ResourceLocation for Menu LARGE_BACKPACK_MENU*/
    public static final ResourceLocation C_LARGE_BACKPACK_MENU_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_LARGE_BACKPACK_MENU);
    /** String for Menu ENCHANTING_TOOL_ITEM */
    public static final String C_ENCHANTING_TOOL_MENU = "enchanting_tool_menu";
    /** ResourceLocation for Menu ENCHANTING_TOOL_ITEM */
    public static final ResourceLocation C_ENCHANTING_TOOL_MENU_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANTING_TOOL_MENU);
    /** String for Menu TELEPORTATION_TABLET_ITEM */
    public static final String C_TELEPORTATION_TABLET_MENU = "teleportation_tablet_menu";
    /** ResourceLocation for Menu TELEPORTATION_TABLET_ITEM */
    public static final ResourceLocation C_TELEPORTATION_TABLET_MENU_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TELEPORTATION_TABLET_MENU);
    /** String for Menu TELEPORTATION_TABLET_ITEM */
    public static final String C_EFFECT_TOTEM_MENU = "effect_totem_menu";
    /** ResourceLocation for Menu TELEPORTATION_TABLET_ITEM */
    public static final ResourceLocation C_EFFECT_TOTEM_MENU_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_EFFECT_TOTEM_MENU);

    // --------------------------------- Networking
    /** * Networking Packet name for SetBlockAirPacket */
    public static final String C_SET_BLOCK_AIR_REQUEST = "set-block-air-request";
    /** ResourceLocation for SetBlockAirPacket */
    public static final ResourceLocation C_SET_BLOCK_AIR_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_SET_BLOCK_AIR_REQUEST);
    /** Networking Packet name for VeinMinerBreakPacket */
    public static final String C_VEINMINER_BREAK_REQUEST = "veinminer-break-request";
    /** ResourceLocation for VeinMinerBreakPacket */
    public static final ResourceLocation C_VEINMINER_BREAK_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_VEINMINER_BREAK_REQUEST);
    /** Networking Packet name for BlockBreakPacket */
    public static final String C_VEINMINER_SERVER_BLOCK_BREAK_REQUEST = "veinminer-server-block-break-request";
    /** ResourceLocation for BlockBreakPacket */
    public static final ResourceLocation C_VEINMINER_SERVER_BLOCK_BREAK_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_VEINMINER_SERVER_BLOCK_BREAK_REQUEST);
    /** Networking Packet name for BackpackSortPacket */
    public static final String C_BACKPACK_SORT_REQUEST = "backpack-sort-request";
    /** ResourceLocation for BackpackSortPacket */
    public static final ResourceLocation C_BACKPACK_SORT_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_SORT_REQUEST);
    /** Networking Packet name for BackpackOpenPacket */
    public static final String C_BACKPACK_OPEN_REQUEST = "backpack-open-request";
    /** ResourceLocation for BackpackOpenPacket */
    public static final ResourceLocation C_BACKPACK_OPEN_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_OPEN_REQUEST);
    /** Networking Packet name for ToggleLightWandPacket */
    public static final String C_TOGGLE_LIGHT_WAND_REQUEST = "toggle-light-wand-request";
    /** ResourceLocation for ToggleLightWandPacket */
    public static final ResourceLocation C_TOGGLE_LIGHT_WAND_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TOGGLE_LIGHT_WAND_REQUEST);
    /** Networking Packet name for ToggleMagnetPacket */
    public static final String C_TOGGLE_MAGNET_ITEM_REQUEST = "toggle-magnet-item-request";
    /** ResourceLocation for ToggleMagnetPacket */
    public static final ResourceLocation C_TOGGLE_MAGNET_ITEM_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TOGGLE_MAGNET_ITEM_REQUEST);
    /** Networking Packet name for InteractEntityPacket */
    public static final String C_INTERACT_ENTITY_REQUEST = "interact-entity-request";
    /** ResourceLocation for InteractEntityPacket */
    public static final ResourceLocation C_INTERACT_ENTITY_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_INTERACT_ENTITY_REQUEST);
    /** Networking Packet name for EnchantPacket */
    public static final String C_ENCHANT_REQUEST = "enchant-request";
    /** ResourceLocation for EnchantPacket */
    public static final ResourceLocation C_ENCHANT_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANT_REQUEST);
    /** Networking Packet name for TeleportPacket */
    public static final String C_TELEPORT_REQUEST = "teleport-request";
    /** ResourceLocation for TeleportPacket */
    public static final ResourceLocation C_TELEPORT_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TELEPORT_REQUEST);
    /** Networking Packet name for TeleportNBTPacket */
    public static final String C_TELEPORT_NBT_REQUEST = "teleport-nbt-request";
    /** ResourceLocation for TeleportNBTPacket */
    public static final ResourceLocation C_TELEPORT_NBT_REQUEST_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TELEPORT_NBT_REQUEST);

    // --------------------------------- Textures
    /** Path String for BACKPACK_SCREEN png */
    public static final String C_BACKPACK_SCREEN_LOCATION = "textures/gui/container/backpack_screen.png";
    /** ResourceLocation for BACKPACK_SCREEN png */
    public static final ResourceLocation C_BACKPACK_SCREEN_LOCATION_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_BACKPACK_SCREEN_LOCATION);
    /** Path String for LARGE_BACKPACK_SCREEN png */
    public static final String C_LARGE_BACKPACK_SCREEN_LOCATION = "textures/gui/container/large_backpack_screen.png";
    /** ResourceLocation for LARGE_BACKPACK_SCREEN png */
    public static final ResourceLocation C_LARGE_BACKPACK_SCREEN_LOCATION_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_LARGE_BACKPACK_SCREEN_LOCATION);
    /** Path String for ENCHANTING_TOOL_SCREEN png */
    public static final String C_ENCHANTING_TOOL_SCREEN_LOCATION = "textures/gui/container/enchanting_tool_screen.png";
    /** ResourceLocation for ENCHANTING_TOOL_SCREEN png */
    public static final ResourceLocation C_ENCHANTING_TOOL_SCREEN_LOCATION_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANTING_TOOL_SCREEN_LOCATION);
    /** Path String for TELEPORTATION_TABLET_SCREEN png */
    public static final String C_TELEPORTATION_TABLET_SCREEN_LOCATION = "textures/gui/container/teleportation_tablet_screen.png";
    /** ResourceLocation for TELEPORTATION_TABLET_SCREEN png */
    public static final ResourceLocation C_TELEPORTATION_TABLET_SCREEN_LOCATION_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_TELEPORTATION_TABLET_SCREEN_LOCATION);
    /** Path String for EFFECT_TOTEM_SCREEN png */
    public static final String C_EFFECT_TOTEM_SCREEN_LOCATION = "textures/gui/container/effect_totem_screen.png";
    /** ResourceLocation for EFFECT_TOTEM_SCREEN png */
    public static final ResourceLocation C_EFFECT_TOTEM_SCREEN_LOCATION_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_EFFECT_TOTEM_SCREEN_LOCATION);


    // --------------------------------- Sprites
    /** Path String for ENCHANTING_SLOT_HIGHLIGHTED_SPRITE png */
    public static final String C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION
            = "textures/gui/sprites/enchanting_slot_highlighted.png";
    /** ResourceLocation for ENCHANTING_SLOT_HIGHLIGHTED_SPRITE png */
    public static final ResourceLocation C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION_RL
            = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION);
    /** Path String for ENCHANTING_SLOT_SPRITE png */
    public static final String C_ENCHANTING_SLOT_SPRITE_LOCATION
            = "textures/gui/sprites/enchanting_slot.png";
    /** ResourceLocation for ENCHANTING_SLOT_SPRITE png */
    public static final ResourceLocation C_ENCHANTING_SLOT_SPRITE_LOCATION_RL
            = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANTING_SLOT_SPRITE_LOCATION);
    /** Path String for ENCHANTING_SLOT_DISABLED_SPRITE png */
    public static final String C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION
            = "textures/gui/sprites/enchanting_slot_disabled.png";
    /** ResourceLocation for ENCHANTING_SLOT_DISABLED_SPRITE png */
    public static final ResourceLocation C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION_RL
            = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION);
    /** Path String for C_VEINMINER_MODE_BOX_LOCATION png */
    public static final String C_VEINMINER_MODE_BOX_LOCATION
            = "textures/gui/hud/veinminer_mode_box.png";
    /** ResourceLocation for C_VEINMINER_MODE_BOX_LOCATION_RL png */
    public static final ResourceLocation C_VEINMINER_MODE_BOX_LOCATION_RL
            = ResourceLocation.fromNamespaceAndPath(MOD_ID, C_VEINMINER_MODE_BOX_LOCATION);
    /** ResourceLocation for SCROLLER_SPRITE png */
    public static final ResourceLocation C_SCROLLER_SPRITE_LOCATION_RL
            = ResourceLocation.withDefaultNamespace("container/creative_inventory/scroller");
    /** ResourceLocation for SCROLLER_DISABLED_SPRITE png */
    public static final ResourceLocation C_SCROLLER_DISABLED_SPRITE_LOCATION_RL
            = ResourceLocation.withDefaultNamespace("container/creative_inventory/scroller_disabled");

    // --------------------------------- VEINMINER
    /** C_VEINMINER_UPDATE_RATE */
    public static final int C_VEINMINER_UPDATE_RATE = 128;

    // --------------------------------- Backpacks
    /** BACKPACK_ITEM row number */
    public static final int C_BACKPACK_ROW_NUM = 6;
    /** BACKPACK_ITEM column number */
    public static final int C_BACKPACK_COL_NUM = 9;
    /** LARGE_BACKPACK_ITEM row number */
    public static final int C_LARGE_BACKPACK_ROW_NUM = 9;
    /** LARGE_BACKPACK_ITEM column number */
    public static final int C_LARGE_BACKPACK_COL_NUM = 13;

    /** Constructor */
    public Constants(){

    }
}
