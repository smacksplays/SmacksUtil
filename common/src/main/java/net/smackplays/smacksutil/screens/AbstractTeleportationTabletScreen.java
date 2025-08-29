package net.smackplays.smacksutil.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.menus.AbstractTeleportationTabletMenu;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.util.MapUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.smackplays.smacksutil.Constants.*;

/** class AbstractTeleportationTabletScreen
 * @param <T> extends AbstractTeleportationTabletMenu */
public class AbstractTeleportationTabletScreen<T extends AbstractTeleportationTabletMenu> extends AbstractContainerScreen<T> {
    /** scrolling */
    public boolean scrolling;
    /** scrollOffs */
    private float scrollOffs;
    /** backgroundWidth */
    protected final int backgroundWidth = 157;
    /** backgroundHeight */
    protected final int backgroundHeight = 295;
    /** scrollbarHeight */
    private final int scrollbarHeight = 175;
    /** editBoxX */
    public EditBox editBoxX;
    /** editBoxY */
    public EditBox editBoxY;
    /** editBoxZ */
    public EditBox editBoxZ;
    /** editBoxName */
    public EditBox editBoxName;
    /** removeButtonWidget */
    public Button removeButtonWidget;
    /** isRemove */
    public boolean isRemove = false;
    /** labelList */
    private final List<Label> labelList = new ArrayList<>();

    /** Constructor
     * @param handler handler
     * @param inventory inventory
     * @param title title */
    public AbstractTeleportationTabletScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    /** Render Background
     * @param context context
     * @param delta delta
     * @param mouseX mouseX
     * @param mouseY mouseY */
    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        labelList.clear();

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, C_TELEPORTATION_TABLET_SCREEN_LOCATION_RL,
                x, y, 0.0F, 0.0F, 512, 512, 512, 512);

        Player player = this.menu.playerInventory.player;
        ItemStack telTool = player.getInventory().getSelectedItem();
        Map<String, TeleportationData> posMap = getTeleportationList(telTool);
        ResourceLocation scroller = posMap.size() > 10 ? C_SCROLLER_SPRITE_LOCATION_RL : C_SCROLLER_DISABLED_SPRITE_LOCATION_RL;
        context.blitSprite(RenderPipelines.GUI_TEXTURED, scroller, x + 137, (y + 15) + (int) this.scrollOffs, 12, 15);
        List<String> keyList = new ArrayList<>(posMap.keySet().stream().toList());

        if (this.scrollOffs > 0 && posMap.size() > 10) {
            int slice = posMap.size() - 10;
            float steps = (float) scrollbarHeight / slice;
            float offset = steps - 1;
            while (this.scrollOffs > offset) {
                posMap.remove(keyList.getFirst());
                keyList.removeFirst();
                offset += steps;
            }
        } else {
            this.scrollOffs = 0;
        }
        for (int i = 0; i < 10; i++) {
            if (posMap.size() > i) {
                String name = keyList.get(i);
                boolean b1 = x + 6 < mouseX;
                boolean b2 = x + 134 > mouseX;
                boolean b3 = y + 13 + 19 * i < mouseY;
                boolean b4 = y + 32 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    context.blit(RenderPipelines.GUI_TEXTURED, C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION_RL,
                            x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                } else {
                    context.blit(RenderPipelines.GUI_TEXTURED, C_ENCHANTING_SLOT_SPRITE_LOCATION_RL,
                            x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                }
                labelList.add(new Label(Component.literal(name), 10, 20 + 19 * i, -12566464, false));
            } else {
                context.blit(RenderPipelines.GUI_TEXTURED, C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION_RL,
                        x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
            }
        }
    }

    /** Render
     * @param context context
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param delta delta */
    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    /** Render labels
     * @param context context
     * @param mouseX mouseX
     * @param mouseY mouseY */
    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX + 10, this.titleLabelY - 65, -12566464, false);
        for (Label l : labelList){
            context.drawString(this.font, l.component, titleLabelX + 2 + l.x, titleLabelY - 70 + l.y, l.color, l.shadow);
        }
    }

    /** init */
    @Override
    protected void init() {
        super.init();
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;
        Player player = this.menu.playerInventory.player;

        removeButtonWidget = Button.builder(Component.literal("").withColor(CommonColors.GREEN),
                (bW) -> onToggleRemove()).pos(x + 110, y + 3).size(40, 10).build();
        this.addRenderableWidget(removeButtonWidget);


        StringWidget stringWidgetX = new StringWidget(x - 13, y + 208, 50, 18, Component.literal("X:"), this.font);
        this.addRenderableWidget(stringWidgetX);

        editBoxX = new EditBox(this.font, x + 18, y + 208, 132, 18, Component.literal("X Pos"));
        editBoxX.setValue(String.valueOf(player.position().x));
        this.addRenderableWidget(editBoxX);

        StringWidget stringWidgetY = new StringWidget(x - 13, y + 228, 50, 18, Component.literal("Y:"), this.font);
        this.addRenderableWidget(stringWidgetY);

        editBoxY = new EditBox(this.font, x + 18, y + 228, 132, 18, Component.literal("Y Pos"));
        editBoxY.setValue(String.valueOf(player.position().y));
        this.addRenderableWidget(editBoxY);

        StringWidget stringWidgetZ = new StringWidget(x - 13, y + 248, 50, 18, Component.literal("Z:"), this.font);
        this.addRenderableWidget(stringWidgetZ);

        editBoxZ = new EditBox(this.font, x + 18, y + 248, 132, 18, Component.literal("Z Pos"));
        editBoxZ.setValue(String.valueOf(player.position().z));
        this.addRenderableWidget(editBoxZ);

        editBoxName = new EditBox(this.font, x + 18, y + 268, 100, 18, Component.literal("Z Pos"));
        editBoxName.setValue("Name");
        this.addRenderableWidget(editBoxName);

        Button buttonWidget = Button.builder(Component.literal("Add"), (bW) -> onButtonWidgetPressed()).pos(x + 120, y + 268).size(30, 18).build();
        this.addRenderableWidget(buttonWidget);
    }

    /** onButtonWidgetPressed */
    private void onButtonWidgetPressed(){
        if (editBoxX.getValue().isBlank() || editBoxY.getValue().isBlank() || editBoxZ.getValue().isBlank() || editBoxName.getValue().isBlank()) return;
        if (NumberUtils.isParsable(editBoxX.getValue())
                && NumberUtils.isParsable(editBoxY.getValue())
                && NumberUtils.isParsable(editBoxZ.getValue())){
            double posX = NumberUtils.createDouble(editBoxX.getValue());
            double posY = NumberUtils.createDouble(editBoxY.getValue());
            double posZ = NumberUtils.createDouble(editBoxZ.getValue());
            String name = editBoxName.getValue();
            Vec3 pos = new Vec3(posX, posY, posZ);
            float xRot = this.menu.playerInventory.player.getXRot();
            float yRot = this.menu.playerInventory.player.getYRot();
            Level level = this.menu.playerInventory.player.level();
            String dim = level.dimension().location().getPath();

            if (Services.C2S_PACKET_SENDER != null) {
                Services.C2S_PACKET_SENDER.TeleportNBTPacket(pos, xRot, yRot, name, dim, false);
            }
        }
    }

    /** onToggleRemove */
    private void onToggleRemove(){
        this.isRemove = !this.isRemove;
        String text = this.isRemove ? "Remove" : "";
        int color = this.isRemove ? CommonColors.RED : CommonColors.GREEN;
        removeButtonWidget.setMessage(Component.literal(text).withColor(color));
    }

    /** getTeleportationList
     * @param stack stack
     * @return Map of Enchantments on stack */
    private Map<String, TeleportationData> getTeleportationList(ItemStack stack){
        Map<String, TeleportationData> map = new HashMap<>();
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {

            CompoundTag tag = data.copyTag();
            if (tag.contains("Positions")){
                ListTag listTag = (ListTag) tag.get("Positions");
                if (listTag != null){
                    for (Tag value : listTag) {
                        CompoundTag p = (CompoundTag) value;
                        double x = p.getDouble("x_pos").orElseThrow();
                        double y = p.getDouble("y_pos").orElseThrow();
                        double z = p.getDouble("z_pos").orElseThrow();
                        float xRot = p.getFloat("x_rot").orElseThrow();
                        float yRot = p.getFloat("y_rot").orElseThrow();
                        String name = p.getString("name").orElseThrow();
                        String dim = p.getString("dim").orElseThrow();
                        ResourceKey<Level> levelKey = Level.OVERWORLD;
                        if (dim.equals("the_nether")){
                            levelKey = Level.NETHER;
                        } else if (dim.equals("the_end")){
                            levelKey = Level.END;
                        }

                        TeleportationData telData = new TeleportationData(new Vec3(x, y, z), xRot, yRot, dim, levelKey);
                        map.putIfAbsent(name, telData);
                    }
                }
            }
        }
        map = MapUtil.sortByValue(map);
        return map;
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
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, scrollbarHeight);
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
     * @param lrClick lrClick
     * @return super */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int lrClick) {
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;
        ItemStack stack = this.menu.playerInventory.player.getInventory().getSelectedItem();
        if (lrClick == 0){
            if (!stack.isEmpty()) {
                Map<String, TeleportationData> posMap = getTeleportationList(stack);
                List<String> keyList = posMap.keySet().stream().toList();
                int list_size = Math.min(posMap.size(), 10);
                int list_offset = 0;
                if (this.scrollOffs > 0 && keyList.size() > 6) {
                    int slice = keyList.size() - 10;
                    float steps = (float) 99 / slice;
                    float offset = steps - 1;
                    while (this.scrollOffs > offset) {
                        offset += steps;
                        list_offset ++;
                    }
                }
                for (int i = 0; i < list_size; ++i) {
                    boolean b1 = x + 6.5 < mouseX;
                    boolean b2 = x + 134 > mouseX;
                    boolean b3 = y + 14 + 19 * i < mouseY;
                    boolean b4 = y + 33 + 19 * i >= mouseY;
                    if (b1 && b2 && b3 && b4) {
                        String name = keyList.get(i + list_offset);
                        if (Services.C2S_PACKET_SENDER != null){
                            if (isRemove){
                                Services.C2S_PACKET_SENDER.TeleportNBTPacket(posMap.get(name).pos, posMap.get(name).xRot, posMap.get(name).yRot, name, posMap.get(name).dim, isRemove);
                            } else {
                                Services.C2S_PACKET_SENDER.TeleportPacket(posMap.get(name).levelKey, posMap.get(name).pos, posMap.get(name).xRot, posMap.get(name).yRot);
                            }
                        }
                        return true;
                    }
                }
                if (posMap.size() > 10 && insideScrollbar(x, y, mouseX, mouseY)) {
                    this.scrolling = true;
                }
            }
        } else {
            if (insideEditBoxX(x, y, mouseX, mouseY)){
                editBoxX.setValue("");
            } else if (insideEditBoxY(x, y, mouseX, mouseY)){
                editBoxY.setValue("");
            } else if (insideEditBoxZ(x, y, mouseX, mouseY)){
                editBoxZ.setValue("");
            } else if (insideEditBoxName(x, y, mouseX, mouseY)){
                editBoxName.setValue("");
            }
        }
        return super.mouseClicked(mouseX, mouseY, lrClick);
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
            Player player = this.menu.playerInventory.player;
            ItemStack telTool = player.getInventory().getSelectedItem();
            Map<String, TeleportationData> posMap = getTeleportationList(telTool);
            if (posMap.size() > 10) {
                int slice = posMap.size() - 10;
                float steps = (float) scrollbarHeight / slice;

                this.scrollOffs -= (float) scroll_delta * steps;
                if (this.scrollOffs < 0) this.scrollOffs = 0;
                else if (this.scrollOffs > scrollbarHeight) this.scrollOffs = scrollbarHeight;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, $$2, scroll_delta);
    }

    /** Check if inside Scrollbar
     * @param x x
     * @param y y
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return true if inside */
    public boolean insideScrollbar(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 136 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 205 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    /** Check if inside EditBoxX
     * @param x x
     * @param y y
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return true if inside */
    public boolean insideEditBoxX(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 208 < mouseY;
        boolean b4 = y + 226 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    /** Check if inside EditBoxY
     * @param x x
     * @param y y
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return true if inside */
    public boolean insideEditBoxY(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 228 < mouseY;
        boolean b4 = y + 246 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    /** Check if inside EditBoxZ
     * @param x x
     * @param y y
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return true if inside */
    public boolean insideEditBoxZ(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 248 < mouseY;
        boolean b4 = y + 266 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    /** Check if inside EditBoxName
     * @param x x
     * @param y y
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return true if inside */
    public boolean insideEditBoxName(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 116.5 >= mouseX;
        boolean b3 = y + 268 < mouseY;
        boolean b4 = y + 286 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    /** Record Label
     * @param component component
     * @param x x
     * @param y y
     * @param color color
     * @param shadow shadow */
    public record Label(Component component, int x, int y, int color, boolean shadow) {
    }

    /** Record TeleportationData
     * @param pos pos
     * @param xRot xRot
     * @param yRot yRot
     * @param dim dim
     * @param levelKey levelKey */
    public record TeleportationData(Vec3 pos, float xRot, float yRot, String dim, ResourceKey<Level> levelKey) {
    }

    /** keyPressed
     * @param GLFW_code GLFW_code
     * @param $$1 $$1
     * @param $$2 $$2
     * @return bool */
    @Override
    public boolean keyPressed(int GLFW_code, int $$1, int $$2) {
        if (GLFW_code == GLFW.GLFW_KEY_ESCAPE &&
                (editBoxX.isFocused() || editBoxY.isFocused()
                        || editBoxZ.isFocused() || editBoxName.isFocused())){
            if (editBoxX.isFocused()){
                editBoxX.setFocused(false);
            } else if (editBoxX.isFocused()){
                editBoxX.setFocused(false);
            } else if (editBoxY.isFocused()){
                editBoxY.setFocused(false);
            } else if (editBoxZ.isFocused()){
                editBoxZ.setFocused(false);
            } else if (editBoxName.isFocused()){
                editBoxName.setFocused(false);
            }
            return true;
        }
        if (editBoxX.isFocused() || editBoxY.isFocused() || editBoxZ.isFocused() || editBoxName.isFocused()){
            return true;
        }
        return super.keyPressed(GLFW_code, $$1, $$2);
    }
}

