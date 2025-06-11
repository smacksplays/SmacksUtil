package net.smackplays.smacksutil.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.smackplays.smacksutil.Constants.MOD_ID;

/**
 * class ModTags */
public class ModTags {
    /** Constructor*/
    public ModTags(){

    }
    /** class Blocks*/
    public static class Blocks {
        /**
         * Constructor*/
        public Blocks(){

        }
        /**
         * TagKey CROP_BLOCKS*/
        public static final TagKey<Block> CROP_BLOCKS =
                createTag("crop_blocks");
        /**
         * TagKey CROP_BLOCKS*/
        public static final TagKey<Block> ORE_BLOCKS =
                createTag("ore_blocks");
        /**
         * TagKey VEGETATION_BLOCKS*/
        public static final TagKey<Block> VEGETATION_BLOCKS =
                createTag("vegetation_blocks");
        /**
         * TagKey STONE_BLOCKS*/
        public static final TagKey<Block> STONE_BLOCKS =
                createTag("stone_blocks");
        /**
         * TagKey DIRT_BLOCKS*/
        public static final TagKey<Block> DIRT_BLOCKS =
                createTag("dirt_blocks");
        /**
         * TagKey TREE_BLOCKS*/
        public static final TagKey<Block> TREE_BLOCKS =
                createTag("tree_blocks");
        /**
         * TagKey VEIN_BLACKLIST*/
        public static final TagKey<Block> VEIN_BLACKLIST =
                createTag("veinminer_blacklist");

        public static final TagKey<Block> ORES_TAG =
                getTag("c", "ores");

        public static final TagKey<Block> MINE_ABLE_PICKAXE =
                getTag("minecraft", "mineable/pickaxe");

        /** createTag
         * @param name name
         * @return created Tag*/
        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }

        /** createTag
         * @param name name
         * @return created Tag*/
        private static TagKey<Block> getTag(String namespace, String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }
}

