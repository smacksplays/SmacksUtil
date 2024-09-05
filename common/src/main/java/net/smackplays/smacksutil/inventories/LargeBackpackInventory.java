package net.smackplays.smacksutil.inventories;


import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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
        if (data != null) {
            CompoundTag tag = data.copyTag().getCompound("enchantment_tool");
            tag = saveAllItems(tag, items);
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
        ListTag listTag = tag.getList("Items", 10);

        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag cTag = listTag.getCompound(i);
            int slot = cTag.getByte("Slot") & 255;
            if (slot < items.size()) {
                ItemStack stack = stackOf(cTag);
                ListTag enchList = (ListTag)cTag.get("Enchantments");
                if (enchList != null && !enchList.isEmpty()){
                    for (Tag t : enchList){
                        String description = ((CompoundTag) t).getString("description");
                        int level = ((CompoundTag) t).getInt("lvl");
                        Optional<HolderSet.Named<Enchantment>> optional = registryAccess.registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.TOOLTIP_ORDER);
                        if (optional.isPresent()){
                            var l = optional.get().stream().toList();
                            for (Holder<Enchantment> entry : l){
                                if (entry.value().description().toString().equals(description)){
                                    stack.enchant(entry, level);
                                }
                            }
                        }
                    }
                }
                items.set(i, stack);
            }
        }
    }
}