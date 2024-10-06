package net.smackplays.smacksutil.inventories;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public interface IBackpackInventory extends WorldlyContainer {
    static IBackpackInventory of(NonNullList<ItemStack> items) {
        return () -> items;
    }

    @SuppressWarnings("unused")
    static IBackpackInventory ofSize(int size) {
        return of(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    NonNullList<ItemStack> getItems();

    @Override
    default int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        int[] result = new int[getItems().size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    default boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack stack, Direction side) {
        return true;
    }

    @Override
    default boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction side) {
        return true;
    }

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);

            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    default @NotNull ItemStack getItem(int slot) {
        return getItems().get(slot);
    }

    @Override
    default @NotNull ItemStack removeItem(int slot, int count) {
        if (count > 64) count = 64;
        if (!getItem(slot).isStackable()) count = 1;
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);

        if (!result.isEmpty()) {
            setChanged();
        }

        return result;
    }

    @Override
    default @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(getItems(), slot);
    }

    @Override
    default void setItem(int slot, @NotNull ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    default void clearContent() {
        getItems().clear();
    }

    @Override
    default void setChanged() {
    }

    @Override
    default boolean stillValid(@NotNull Player player) {
        return true;
    }


    @Override
    default int getMaxStackSize() {
        int baseStackSize = 64;
        ArrayList<ItemStack> upgrades = new ArrayList<>(){{
            add(getItem(0));
            add(getItem(1));
            add(getItem(2));
            add(getItem(3));
        }};
        for (int i = 0; i < 4; i++){
            ItemStack upgrade = upgrades.get(i);
            if (!upgrade.isEmpty()){
                Item upgradeItem = upgrade.getItem();
                if (upgradeItem.equals(Services.PLATFORM.getUpgrade1Item())) {
                    baseStackSize *= 4;
                } else if (upgradeItem.equals(Services.PLATFORM.getUpgrade2Item())) {
                    baseStackSize *= 8;
                } else if (upgradeItem.equals(Services.PLATFORM.getUpgrade3Item())){
                    baseStackSize *= 16;
                }
            }
        }
        return baseStackSize;
    }

    default boolean checkRemoveUpgrade(int corrCount) {
        for (int i = 0; i < getItems().size(); i++){
            if (getItems().get(i).getCount() > corrCount){
                return false;
            }
        }
        return true;
    }


    default void loadAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
        ListTag listTag = tag.getList("Items", 10);

        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag cTag = listTag.getCompound(i);
            int slot = cTag.getByte("Slot") & 255;
            if (slot < items.size()) {
                ItemStack stack = stackOf(cTag);
                ItemEnchantments ench = stack.getEnchantments();
                ListTag enchList = (ListTag)cTag.get("Enchantments");
                if (enchList != null && !enchList.isEmpty()){
                    for (Tag t : enchList){

                    }
                }
                items.set(i, stackOf(cTag));
            }
        }
    }

    default CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
        ListTag listtag = new ListTag();
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            CompoundTag compoundTag = new CompoundTag();
            ResourceLocation location = BuiltInRegistries.ITEM.getKey(stack.getItem());
            compoundTag.putString("id", location.toString());
            compoundTag.putFloat("Count", (float)stack.getCount());
            if (stack.get(DataComponents.ENCHANTMENTS) != null){
                ListTag enchantList = new ListTag();
                ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
                var enchatnList = enchantments.entrySet().stream().toList();
                for(var entry : enchatnList){
                    Enchantment ench = entry.getKey().value();
                    CompoundTag tag1 = new CompoundTag();
                    tag1.putString("description", ench.description().toString());
                    tag1.putInt("lvl", EnchantmentHelper.getItemEnchantmentLevel(entry.getKey(), stack));
                    enchantList.add(tag1);
                }
                compoundTag.put("Enchantments", enchantList);
            }
            listtag.add(compoundTag);
        }
        tag.put("Items", listtag);
        return tag;

    }
    default ItemStack stackOf(CompoundTag tag) {
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(tag.getString("id")));
        ItemStack stack = new ItemStack(item);
        stack.setCount((int) tag.getFloat("Count"));
        if (tag.contains("tag", 10)) {
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            stack.getItem().verifyComponentsAfterLoad(stack);
        }

        if (stack.get(DataComponents.CAN_BREAK) != null) {
            stack.setDamageValue(stack.getDamageValue());
        }
        return stack;
    }

    //TODO fix
    default void saveStack(ItemStack stack, CompoundTag tag) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(stack.getItem());
        tag.putString("id", location.toString());
        tag.putFloat("Count", (float)stack.getCount());
        //if (stack.getTag() != null) {
        //    tag.put("tag", stack.getTag().copy());
        //}

    }
}