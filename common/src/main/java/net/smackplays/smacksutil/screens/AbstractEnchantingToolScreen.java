package net.smackplays.smacksutil.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.smackplays.smacksutil.inventories.EnchantmentToolInventory;
import net.smackplays.smacksutil.menus.AbstractEnchantingToolMenu;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.smackplays.smacksutil.Constants.*;

/** class AbstractEnchantingToolScreen
 * @param <T> extends AbstractEnchantingToolMenu */
public class AbstractEnchantingToolScreen<T extends AbstractEnchantingToolMenu> extends AbstractContainerScreen<T> {
    /** scrolling */
    public boolean scrolling;
    /** scrollOffs */
    private float scrollOffs;
    /** backgroundWidth */
    protected final int backgroundWidth = 176;
    /** backgroundHeight */
    protected final int backgroundHeight = 224;
    /** addRemove */
    private boolean addRemove = true;
    /** buttonWidget */
    private Button buttonWidget;
    /** labelList */
    private final List<Label> labelList = new ArrayList<>();
    /** registryAccess */
    private final RegistryAccess registryAccess;

    /** Constructor
     * @param handler handler
     * @param inventory inventory
     * @param title title*/
    public AbstractEnchantingToolScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        registryAccess = inventory.player.registryAccess();
    }

    /** Render Background
     * @param context context
     * @param delta delta
     * @param mouseX mouseX
     * @param mouseY mouseY*/
    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        labelList.clear();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(RenderType::guiTextured, C_ENCHANTING_TOOL_SCREEN_LOCATION_RL,
                x, y, 0.0F, 0.0F, 256, 256, 256, 256);

        Slot enchantSlot = this.menu.slots.getFirst();
        ItemStack stack = enchantSlot.getItem();
        ArrayList<Holder<Enchantment>> list = getEnchantments(stack);
        ResourceLocation scroller = list.size() > 6 ? C_SCROLLER_SPRITE_LOCATION_RL : C_SCROLLER_DISABLED_SPRITE_LOCATION_RL;
        context.blitSprite(RenderType::guiTextured, scroller, x + 137, (y + 15) + (int) this.scrollOffs, 12, 15);
        if (this.scrollOffs > 0 && enchantSlot.hasItem() && list.size() > 6) {
            int slice = list.size() - 6;
            float steps = (float) 99 / slice;
            float offset = steps - 1;
            while (this.scrollOffs > offset) {
                list.removeFirst();
                offset += steps;
            }
        } else {
            this.scrollOffs = 0;
        }
        for (int i = 0; i < 6; i++) {
            if (enchantSlot.hasItem() && list.size() > i) {
                Enchantment ench = list.get(i).value();
                boolean b1 = x + 6 < mouseX;
                boolean b2 = x + 134 > mouseX;
                boolean b3 = y + 13 + 19 * i < mouseY;
                boolean b4 = y + 32 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION_RL,
                            x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                } else {
                    context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_SPRITE_LOCATION_RL,
                            x + 8, y + 15 + 19 * i, 0, 0, 126,19, 126, 19);
                }
                Component enchantString = ench.description();
                labelList.add(new Label(enchantString, 10, 20 + 19 * i, false));
            } else {
                context.blit(RenderType::guiTextured, C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION_RL,
                        x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
            }
        }
    }

    /** Get Enchantments
     * @param stack stack
     * @return List of Enchantments*/
    public ArrayList<Holder<Enchantment>> getEnchantments(ItemStack stack) {
        ArrayList<Holder<Enchantment>> list = new ArrayList<>();
        ArrayList<Holder<Enchantment>> presentEnchantments = new ArrayList<>();

        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        var enchatnList = enchantments.entrySet().stream().toList();
        for(var entry : enchatnList){
            presentEnchantments.add(entry.getKey());
        }
        if(!addRemove) return presentEnchantments;
        Optional<HolderSet.Named<Enchantment>> optional = registryAccess.lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentTags.TOOLTIP_ORDER);
        if (optional.isPresent()){
            var l = optional.get().stream().toList();
            for (Holder<Enchantment> entry : l){
                Enchantment enchantment = entry.value();
                boolean compatible = true;
                int lvl = EnchantmentHelper.getItemEnchantmentLevel(entry, stack);
                for (Holder<Enchantment> e : presentEnchantments) {
                    if (!Enchantment.areCompatible(entry, e)) compatible = false;
                }
                if (enchantment.canEnchant(stack) && lvl == 0 && compatible) list.add(entry);
            }
        }
        return list;
    }

    /** Render
     * @param context context
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param delta delta*/
    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);

        EnchantmentToolInventory inv = (EnchantmentToolInventory) this.menu.inventory;
        ItemStack itemStack = inv.stack;
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null){
            CompoundTag tag = customData.copyTag();
            inv.loadAllItems(tag, inv.getItems());
        }
    }

    /** Render labels
     * @param context context
     * @param mouseX mouseX
     * @param mouseY mouseY*/
    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX - 38, this.titleLabelY - 31, 0x404040, false);
        context.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 1, this.inventoryLabelY + 30, 0x404040, false);
        for (Label l : labelList){
            context.drawString(this.font, l.component, titleLabelX - 45 + l.x, titleLabelY - 34 + l.y, 0x404040, l.shadow);
        }
    }
    /** init */
    @Override
    protected void init() {
        super.init();
        this.addRemove = true;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // Center the title
        titleLabelX = (backgroundWidth - font.width(title)) / 2;
        buttonWidget = Button.builder(Component.literal("Add").withColor(CommonColors.GREEN), (bW) -> onToggleAddRemove()).pos(x + 132, y + 3).size(40, 10).build();
        this.addRenderableWidget(buttonWidget);
    }

    /** Toggle Remove Add */
    public void onToggleAddRemove() {
        this.addRemove = !this.addRemove;
        String text = this.addRemove ? "Add" : "Remove";
        int color = this.addRemove ? CommonColors.GREEN : CommonColors.RED;
        buttonWidget.setMessage(Component.literal(text).withColor(color));
    }

    /** has clicked outside
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param left left
     * @param top top
     * @param button button
     * @return true if clicked outside*/
    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double) (width - backgroundWidth) / 2 - 10
                || mouseX > (double) (width + backgroundWidth) / 2 + 10
                || mouseY < (double) (height - backgroundHeight) / 2 - 10
                || mouseY > (double) (height + backgroundHeight) / 2 + 10;
    }

    /** Check if inside Scrollbar
     * @param x x
     * @param y y
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return true if inside */
    public boolean insideScrollbar(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 136 < mouseX;
        boolean b2 = x + 148 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 127 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    /** Mouse Dragged
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param $$2 $$2
     * @param scrollX scrollX
     * @param scrollY scrollY
     * @return true */
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int $$2, double scrollX, double scrollY) {
        int y = (height - backgroundHeight) / 2;
        if (this.scrolling) {
            this.scrollOffs = (float) mouseY - y - 22;
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 99F);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, $$2, scrollX, scrollY);
        }
    }

    /** Mouse Released
     * @param d d
     * @param e e
     * @param i i
     * @return super */
    @Override
    public boolean mouseReleased(double d, double e, int i) {
        this.scrolling = false;
        return super.mouseReleased(d, e, i);
    }

    /** Mouse Clicked
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param $$2 $$2
     * @return super */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int $$2) {
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;

        Slot enchantSlot = this.menu.slots.getFirst();

        ItemStack stack = enchantSlot.getItem().copy();
        if (!stack.isEmpty()) {
            ArrayList<Holder<Enchantment>> list = getEnchantments(stack);
            int list_size = Math.min(list.size(), 6);

            if (this.scrollOffs > 0 && enchantSlot.hasItem() && list.size() > 6) {
                int slice = list.size() - 6;
                float steps = (float) 99 / slice;
                float offset = steps - 1;
                while (this.scrollOffs > offset) {
                    list.removeFirst();
                    offset += steps;
                }
            }

            for (int i = 0; i < list_size; ++i) {
                boolean b1 = x + 6.5 < mouseX;
                boolean b2 = x + 134 > mouseX;
                boolean b3 = y + 14 + 19 * i < mouseY;
                boolean b4 = y + 33 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    Enchantment enchantment = list.get(i).value();
                    if (Services.C2S_PACKET_SENDER != null) {
                        Services.C2S_PACKET_SENDER.EnchantPacket(enchantment, addRemove);
                    }
                    return true;
                }
            }
            if (list.size() > 6 && insideScrollbar(x, y, mouseX, mouseY)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, $$2);
    }

    /** Mouse scrolled
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param $$2 $$2
     * @param scroll_delta scroll_delta
     * @return super */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double $$2, double scroll_delta) {
        if (!this.scrolling) {
            Slot enchantSlot = this.menu.slots.getFirst();
            ItemStack stack = enchantSlot.getItem().copy();
            ArrayList<Holder<Enchantment>> list = getEnchantments(stack);
            if (list.size() > 6) {
                int slice = list.size() - 6;
                float steps = (float) 99 / slice;

                this.scrollOffs -= (float) scroll_delta * steps;
                if (this.scrollOffs < 0) this.scrollOffs = 0;
                else if (this.scrollOffs > 99) this.scrollOffs = 99;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, $$2, scroll_delta);
    }

    /** Record Label
     * @param component component
     * @param x x
     * @param y y
     * @param shadow shadow */
    private record Label(Component component, int x, int y, boolean shadow) {
    }
}