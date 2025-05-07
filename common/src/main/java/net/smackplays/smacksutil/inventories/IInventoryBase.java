package net.smackplays.smacksutil.inventories;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IInventoryBase extends WorldlyContainer {
    static IInventoryBase of(NonNullList<ItemStack> items) {
        return () -> items;
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
        ItemStack stack = getItem(slot);
        if (count > stack.getMaxStackSize()) {
            if (stack.getMaxStackSize() < 64){
                if (count == Math.round((float) stack.getCount() / 2)) {
                    count = Math.round((float) stack.getMaxStackSize() / 2);
                } else {
                    count = stack.getMaxStackSize();
                }
            } else {
                if (count == Math.round((float) stack.getCount() / 2)) {
                    count = Math.round((float) stack.getMaxStackSize() / 2);
                } else {
                    count = 64;
                }
            }
        }
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

    default void loadAllItems(CompoundTag tag, NonNullList<ItemStack> items, RegistryAccess registryAccess) {
        if (tag.getCompound("Items").isEmpty()) return;
        CompoundTag itemTag = tag.getCompound("Items").get();
        ContainerHelper.loadAllItems(itemTag, items, registryAccess);
        if (tag.getList("counts").isEmpty()) return;
        ListTag countList = tag.getList("counts").get();
        for (int i = 0; i < countList.size(); i++) {
            if (countList.getCompound(i).isEmpty()) return;
            CompoundTag countTag = countList.getCompound(i).get();
            if (countTag.getInt("count").isEmpty()) return;
            int count = countTag.getInt("count").get();
            ItemStack stack = items.get(i);
            stack.setCount(count);
            items.set(i, stack);
        }
    }

    default CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> items, RegistryAccess registryAccess) {
        ListTag countList = new ListTag();
        for (var item : items) {
            CompoundTag t = new CompoundTag();
            t.putInt("count", item.getCount());
            countList.add(t);
            if (item.getCount() > 1){
                item.setCount(1);
            }
        }
        tag.put("counts", countList);
        CompoundTag itemTag = ContainerHelper.saveAllItems(new CompoundTag(), items, registryAccess);
        tag.put("Items", itemTag);
        loadAllItems(tag, items, registryAccess);
        return tag;
    }

}