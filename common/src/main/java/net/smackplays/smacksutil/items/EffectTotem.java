package net.smackplays.smacksutil.items;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import javax.lang.model.element.ModuleElement;
import java.util.List;

public class EffectTotem extends Item {
    private static int counter = 0;
    private static final String LIST_NAME = "effect_list";
    private static final int EFFECT_TIME = 30 * 20;
    public EffectTotem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).component(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag())));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int $$3, boolean $$4) {
        if (counter == 100){
            Player player = (Player) entity;
            CustomData data = stack.get(DataComponents.CUSTOM_DATA);
            assert data != null;
            CompoundTag c_tag = data.copyTag();
            ListTag effectList = (ListTag)c_tag.get(LIST_NAME);
            if(effectList == null){
                effectList = new ListTag();
                c_tag.put(LIST_NAME, effectList);
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(c_tag));
            }
            for (Tag tag_1 : effectList){
                CompoundTag c_tag_1 = (CompoundTag) tag_1;
                MobEffectInstance mobeffectinstance = MobEffectInstance.load(c_tag_1);
                if (mobeffectinstance != null) {
                    player.addEffect(mobeffectinstance);
                }
            }
            counter = 0;
        }
        counter ++;
    }

    @Override
    public InteractionResult useOn(UseOnContext $$0) {
        ItemStack stack = $$0.getItemInHand();
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        assert data != null;
        CompoundTag tag = data.copyTag();
        ListTag listTag = (ListTag)tag.get(LIST_NAME);
        assert listTag != null;
        if (listTag.isEmpty()){
            MobEffectInstance i = new MobEffectInstance(MobEffects.ABSORPTION, EFFECT_TIME, 0);
            listTag.add(i.save());
        } else {
            listTag.clear();
        }
        tag.put(LIST_NAME, tag);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return super.useOn($$0);
    }
}
