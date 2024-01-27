package net.smackplays.smacksutil.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BackpackUpgradeItem extends Item {
    private int multiplier;
    public BackpackUpgradeItem(Properties props, int multiplier) {
        super(props);
    }
    public BackpackUpgradeItem(int multiplier){
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> componentList, TooltipFlag flag) {
        CompoundTag tag = stack.getOrCreateTag();
        componentList.add(1, Component.literal("Multiplier: " + multiplier));
        super.appendHoverText(stack, level, componentList, flag);
    }
}
