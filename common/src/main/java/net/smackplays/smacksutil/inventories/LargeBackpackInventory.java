package net.smackplays.smacksutil.inventories;


import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;

public class LargeBackpackInventory implements IBackpackInventory {
    public final ItemStack stack;
    private final RegistryAccess registryAccess;
    private final NonNullList<ItemStack> items = NonNullList.withSize(13 * 9 + 4, ItemStack.EMPTY);

    public LargeBackpackInventory(ItemStack stack, RegistryAccess registryAccess) {
        this.stack = stack;
        this.registryAccess = registryAccess;
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            loadAllItems(tag, items);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setChanged() {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.copyTag().getCompound("items").isPresent()) {
            CompoundTag tag = data.copyTag().getCompound("items").get();
            tag = saveAllItems(tag, items);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        } else if (data != null) {
            CompoundTag tag = new CompoundTag();
            CompoundTag item_tag = new CompoundTag();
            item_tag = saveAllItems(item_tag, items);
            tag.put("items", item_tag);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }


    @Override
    public void loadAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
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

    @Override
    public CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
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
        loadAllItems(tag, items);
        return tag;
    }
}