package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BackpackUpgradeItem extends Item {
    private int multiplier = 1;
    public BackpackUpgradeItem(Properties props, int multiplier) {
        super(props);
        this.multiplier = multiplier;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        consumer.accept(Component.literal("Multiplier: " + multiplier));
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    public int getMultiplier(){
        return multiplier;
    }
}
