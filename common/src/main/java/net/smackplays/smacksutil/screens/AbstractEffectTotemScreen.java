package net.smackplays.smacksutil.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.smackplays.smacksutil.menus.AbstractEffectTotemMenu;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static net.smackplays.smacksutil.Constants.*;

public class AbstractEffectTotemScreen<T extends AbstractEffectTotemMenu> extends AbstractContainerScreen<T> {
    private final int scrollbarHeight_left = 232;
    private final int scrollbarHeight_right = 232;
    public boolean scrolling_left;
    public boolean scrolling_right;
    private float scrollOffs_left;
    private float scrollOffs_right;
    protected final int backgroundWidth = 308;
    protected final int backgroundHeight = 267;
    private final List<Label> LabelList_left = new ArrayList<>();
    private final List<Label> LabelList_right = new ArrayList<>();
    public AbstractEffectTotemScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        LabelList_left.clear();
        LabelList_right.clear();

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(RenderType::guiTextured, C_EFFECT_TOTEM_SCREEN_LOCATION_RL,
                x, y, 0.0F, 0.0F, 512, 512, 512, 512);

        Player player = this.menu.playerInventory.player;
        ItemStack effectTotem = player.getInventory().getSelectedItem();

        renderBgLeft(x, y, context, effectTotem, mouseX, mouseY);
        renderBgRight(x, y, context, effectTotem, mouseX, mouseY);
    }

    private void renderBgLeft(int x, int y, GuiGraphics context, ItemStack effectTotem, int mouseX, int mouseY) {
        Map<String, Integer> currentEffectList = getCurrentEffectList(effectTotem);
        ResourceLocation scroller_left = currentEffectList.size() > 13 ? C_SCROLLER_SPRITE_LOCATION_RL : C_SCROLLER_DISABLED_SPRITE_LOCATION_RL;
        context.blitSprite(RenderType::guiTextured, scroller_left, x + 137, (y + 15) + (int) this.scrollOffs_left, 12, 15);
        List<String> currentkeyList = new ArrayList<>(currentEffectList.keySet().stream().toList());
        currentkeyList.sort(Comparator.comparing((String s) -> s));

        if (this.scrollOffs_left > 0 && currentEffectList.size() > 13) {
            int slice = currentEffectList.size() - 13;
            float steps = (float) scrollbarHeight_left / slice;
            float offset = steps - 1;
            while (this.scrollOffs_left > offset) {
                currentEffectList.remove(currentkeyList.getFirst());
                currentkeyList.removeFirst();
                offset += steps;
            }
        } else {
            this.scrollOffs_left = 0;
        }
        for (int i = 0; i < 13; i++) {
            if (currentEffectList.size() > i) {
                String name = currentkeyList.get(i);
                boolean b1 = x + 6 < mouseX;
                boolean b2 = x + 134 > mouseX;
                boolean b3 = y + 13 + 19 * i < mouseY;
                boolean b4 = y + 32 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION_RL,
                            x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                } else {
                    context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_SPRITE_LOCATION_RL,
                            x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                }
                LabelList_left.add(new Label(name, 1, 10, 20 + 19 * i));
            } else {
                context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION_RL,
                        x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
            }
        }
        LabelList_left.sort(Comparator.comparing((Label l) -> l.name));
    }

    private void renderBgRight(int x, int y, GuiGraphics context, ItemStack effectTotem, int mouseX, int mouseY) {
        Map<String, Integer> availableEffectList = getAllEffects();
        ResourceLocation scroller_right = availableEffectList.size() > 13 ? C_SCROLLER_SPRITE_LOCATION_RL : C_SCROLLER_DISABLED_SPRITE_LOCATION_RL;
        context.blitSprite(RenderType::guiTextured, scroller_right, x + 137 + 151, (y + 15) + (int) this.scrollOffs_right, 12, 15);
        List<String> availableKeyList = new ArrayList<>(availableEffectList.keySet().stream().toList());
        availableKeyList.sort(Comparator.comparing((String s) -> s));

        Map<String, Integer> enchMap_left = getCurrentEffectList(effectTotem);
        List<String> keyList_left = enchMap_left.keySet().stream().toList();

        for (String key : keyList_left) {
            availableKeyList.remove(key);
            availableEffectList.remove(key);
        }

        if (this.scrollOffs_right > 0 && availableEffectList.size() > 13) {
            int slice = availableEffectList.size() - 13;
            float steps = (float) scrollbarHeight_right / slice;
            float offset = steps - 1;
            while (this.scrollOffs_right > offset) {
                availableEffectList.remove(availableKeyList.getFirst());
                availableKeyList.removeFirst();
                offset += steps;
            }
        } else {
            this.scrollOffs_right = 0;
        }
        for (int i = 0; i < 13; i++) {
            if (availableEffectList.size() > i) {
                String name = availableKeyList.get(i);
                boolean b1 = x + 151 + 6 < mouseX;
                boolean b2 = x + 151 + 134 > mouseX;
                boolean b3 = y + 13 + 19 * i < mouseY;
                boolean b4 = y + 32 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION_RL,
                            x + 8 + 151, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                } else {
                    context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_SPRITE_LOCATION_RL,
                            x + 8 + 151, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                }
                LabelList_right.add(new Label(name, 1, 151, 20 + 19 * i));
            } else {
                context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION_RL,
                        x + 8 + 151, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX - 65, this.titleLabelY - 51, 0x404040, false);
        for (Label l : LabelList_left){
            context.drawString(this.font,
                    Component.translatable("effect.minecraft." + l.name),
                    titleLabelX + l.x - 65 + 2, titleLabelY + l.y - 55, 0x404040, false);
        }
        for (Label l : LabelList_right){
            context.drawString(this.font,
                    Component.translatable("effect.minecraft." + l.name),
                    titleLabelX + l.x - 65 + 2, titleLabelY + l.y - 55, 0x404040, false);
        }
    }

    @Override
    protected void init() {
        super.init();
    }

    private Map<String, Integer> getCurrentEffectList(ItemStack effectTotem){
        Map<String, Integer> map = new HashMap<>();
        CustomData data = effectTotem.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            if (tag.contains("effect_list")){
                Tag tag1 = tag.get("effect_list");
                if (tag1 != null && tag1.asList().isPresent()){
                    ListTag effectList = tag1.asList().get();
                    for (Tag t : effectList){
                        CompoundTag cTag = (CompoundTag) t;
                        if (cTag.getString("name").isPresent() && cTag.getInt("amplifier").isPresent()){
                            String name = cTag.getString("name").get();
                            int amplifier = cTag.getInt("amplifier").get();
                            map.put(name, amplifier);
                        }
                    }
                }
            }
        }
        return map;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int lrClick) {
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;
        ItemStack stack = this.menu.playerInventory.player.getInventory().getSelectedItem();
        if (lrClick == 0){
            if (!stack.isEmpty()) {
                Map<String, Integer> enchMap_left = getCurrentEffectList(stack);
                List<String> keyList_left = new ArrayList<>(enchMap_left.keySet().stream().toList());

                Map<String, Integer> enchMap_right = getAllEffects();
                for (String key : keyList_left){
                    enchMap_right.remove(key);
                }

                List<String> keyList_right = new ArrayList<>(enchMap_right.keySet().stream().toList());
                keyList_right.sort(Comparator.comparing((String s) -> s));
                keyList_left.sort(Comparator.comparing((String s) -> s));

                int list_offset_left = 0;
                if (this.scrollOffs_left > 0 && keyList_left.size() > 13) {
                    int slice = keyList_left.size() - 13;
                    float steps = (float) scrollbarHeight_left / slice;
                    float offset = steps - 1;
                    while (this.scrollOffs_left > offset) {
                        offset += steps;
                        list_offset_left ++;
                    }
                }
                int list_offset_right = 0;
                if (this.scrollOffs_right > 0 && keyList_right.size() > 13) {
                    int slice = keyList_right.size() - 13;
                    float steps = (float) scrollbarHeight_right / slice;
                    float offset = steps - 1;
                    while (this.scrollOffs_right > offset) {
                        offset += steps;
                        list_offset_right ++;
                    }
                }
                for (int i = 0; i < Math.min(enchMap_left.size(), 13); ++i) {
                    boolean b1 = x + 6.5 < mouseX;
                    boolean b2 = x + 134 > mouseX;
                    boolean b3 = y + 14 + 19 * i < mouseY;
                    boolean b4 = y + 33 + 19 * i >= mouseY;
                    if (b1 && b2 && b3 && b4) {
                        String name = keyList_left.get(i + list_offset_left);
                        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                        if (data != null) {
                            ListTag listTag = (ListTag)data.copyTag().get("effect_list");
                            CompoundTag enchantment = new CompoundTag();
                            enchantment.putString("name", name);
                            enchantment.putInt("amplifier", 1);
                            CompoundTag tag = new CompoundTag();
                            if (listTag != null){
                                listTag.remove(enchantment);
                                tag.put("effect_list", listTag);
                            } else {
                                tag.put("effect_list", new ListTag());
                            }
                            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                        }
                        return true;
                    }
                }
                for (int i = 0; i < Math.min(enchMap_right.size(), 13); ++i){
                    boolean b1 = x + 151 + 6.5 < mouseX;
                    boolean b2 = x + 151 + 134 > mouseX;
                    boolean b3 = y + 14 + 19 * i < mouseY;
                    boolean b4 = y + 33 + 19 * i >= mouseY;
                    if (b1 && b2 && b3 && b4){
                        String name = keyList_right.get(i + list_offset_right);
                        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                        if (data != null) {
                            ListTag listTag = (ListTag)data.copyTag().get("effect_list");
                            CompoundTag enchantment = new CompoundTag();
                            enchantment.putString("name", name);
                            enchantment.putInt("amplifier", 1);
                            CompoundTag tag = new CompoundTag();
                            if (listTag != null && !listTag.contains(enchantment)){
                                listTag.add(enchantment);
                                tag.put("effect_list", listTag);
                            } else {
                                tag.put("effect_list", new ListTag());
                            }
                            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                        }
                    }
                }
                if (enchMap_left.size() > 13 && insideScrollbar_left(x, y, mouseX, mouseY)) {
                    this.scrolling_left = true;
                }
                if (enchMap_right.size() > 13 && insideScrollbar_right(x, y, mouseX, mouseY)) {
                    this.scrolling_left = true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, lrClick);
    }

    private Map<String, Integer> getAllEffects(){
        Map<String, Integer> map = new HashMap<>();
        for (Label label : getAllLabels()){
            map.put(label.name, label.amplifier);
        }
        return map;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int $$2, double scrollX, double scrollY) {
        int y = (height - backgroundHeight) / 2;
        if (this.scrolling_left) {
            this.scrollOffs_left = (float) mouseY - y - 22;
            this.scrollOffs_left = Mth.clamp(this.scrollOffs_left, 0.0F, scrollbarHeight_left);
            return true;
        } else if (this.scrolling_right) {
            this.scrollOffs_right = (float) mouseY - y - 22;
            this.scrollOffs_right = Mth.clamp(this.scrollOffs_right, 0.0F, scrollbarHeight_right);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, $$2, scrollX, scrollY);
        }
    }

    @Override
    public boolean mouseReleased(double d, double e, int i) {
        this.scrolling_left = false;
        this.scrolling_right = false;
        return super.mouseReleased(d, e, i);
    }

    public boolean insideScrollbar_left(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 136 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 205 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    public boolean insideField_left(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 8  < mouseX;
        boolean b2 = x + 8 + 148.5 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 205 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    public boolean insideScrollbar_right(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 151 < mouseX;
        boolean b2 = x + 151 + 148.5 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 205 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    public boolean insideField_right(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 8 + 151 < mouseX;
        boolean b2 = x + 8 + 151 + 126 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 205 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double $$2, double scroll_delta) {
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;
        boolean scroll_left = insideField_left(x, y, mouseX, mouseY);
        boolean scroll_right = insideField_right(x, y, mouseX, mouseY);
        if (!this.scrolling_left && scroll_left) {
            Player player = this.menu.playerInventory.player;
            ItemStack effectTotem = player.getInventory().getSelectedItem();
            Map<String, Integer> posMap = getCurrentEffectList(effectTotem);
            if (posMap.size() > 13) {
                int slice = posMap.size() - 13;
                float steps = (float) scrollbarHeight_left / slice;
                this.scrollOffs_left -= (float) scroll_delta * steps;
                if (this.scrollOffs_left < 0) this.scrollOffs_left = 0;
                else if (this.scrollOffs_left > scrollbarHeight_left) this.scrollOffs_left = scrollbarHeight_left;
            }
        }
        if (!this.scrolling_right && scroll_right) {
            Player player = this.menu.playerInventory.player;
            ItemStack effectTotem = player.getInventory().getSelectedItem();
            Map<String, Integer> currentEffectList = getCurrentEffectList(effectTotem);
            Map<String, Integer> availableEffectList = getAllEffects();

            List<String> keyList_left = currentEffectList.keySet().stream().toList();

            for (String key : keyList_left){
                availableEffectList.remove(key);
            }

            if (availableEffectList.size() > 13) {
                int slice = availableEffectList.size() - 13;
                float steps = (float) scrollbarHeight_right / slice;

                this.scrollOffs_right -= (float) scroll_delta * steps;
                if (this.scrollOffs_right < 0) this.scrollOffs_right = 0;
                else if (this.scrollOffs_right > scrollbarHeight_right) this.scrollOffs_right = scrollbarHeight_right;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, $$2, scroll_delta);
    }

    private List<Label> getAllLabels() {
        List<Label> list = new ArrayList<>();
        list.add(new Label("speed", 1, 0, 0));
        list.add(new Label("slowness", 1, 0, 0));
        list.add(new Label("haste", 1, 0, 0));
        list.add(new Label("mining_fatigue", 1, 0, 0));
        list.add(new Label("strength", 1, 0, 0));
        list.add(new Label("instant_health", 1, 0, 0));
        list.add(new Label("instant_damage", 1, 0, 0));
        list.add(new Label("jump_boost", 1, 0, 0));
        list.add(new Label("nausea", 1, 0, 0));
        list.add(new Label("regeneration", 1, 0, 0));
        list.add(new Label("resistance", 1, 0, 0));
        list.add(new Label("fire_resistance", 1, 0, 0));
        list.add(new Label("water_breathing", 1, 0, 0));
        list.add(new Label("invisibility", 1, 0, 0));
        list.add(new Label("blindness", 1, 0, 0));
        list.add(new Label("night_vision", 1, 0, 0));
        list.add(new Label("hunger", 1, 0, 0));
        list.add(new Label("weakness", 1, 0, 0));
        list.add(new Label("poison", 1, 0, 0));
        list.add(new Label("wither", 1, 0, 0));
        list.add(new Label("health_boost", 1, 0, 0));
        list.add(new Label("absorption", 1, 0, 0));
        list.add(new Label("saturation", 1, 0, 0));
        list.add(new Label("glowing", 1, 0, 0));
        list.add(new Label("levitation", 1, 0, 0));
        list.add(new Label("luck", 1, 0, 0));
        list.add(new Label("unluck", 1, 0, 0));
        list.add(new Label("slow_falling", 1, 0, 0));
        list.add(new Label("conduit_power", 1, 0, 0));
        list.add(new Label("dolphins_grace", 1, 0, 0));
        list.add(new Label("bad_omen", 1, 0, 0));
        list.add(new Label("hero_of_the_village", 1, 0, 0));
        list.add(new Label("darkness", 1, 0, 0));
        list.add(new Label("trial_omen", 1, 0, 0));
        list.add(new Label("raid_omen", 1, 0, 0));
        list.add(new Label("wind_charged", 1, 0, 0));
        list.add(new Label("weaving", 1, 0, 0));
        list.add(new Label("oozing", 1, 0, 0));
        list.add(new Label("infested", 1, 0, 0));
        list.sort(Comparator.comparing((Label l) -> l.name));
        return list;
    }

    public record Label(String name, int amplifier, int x, int y) {
    }
}

