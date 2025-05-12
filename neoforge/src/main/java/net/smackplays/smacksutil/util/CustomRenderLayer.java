package net.smackplays.smacksutil.util;


import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalDouble;

public class CustomRenderLayer extends RenderType {
    public static final RenderType LINES = RenderType.create(
            "lines", 1536, false, false,
            com.mojang.blaze3d.pipeline.RenderPipeline.builder()
                    .build(),
            CompositeState.builder()
                    .setLineState(new LineStateShard(OptionalDouble.empty()))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setOutputState(OUTLINE_TARGET)
                    .createCompositeState(false));

    public CustomRenderLayer(String string, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, i, bl, bl2, runnable, runnable2);
    }

    @Override
    public void draw(@NotNull MeshData meshData) {}

    @Override
    public @NotNull RenderTarget getRenderTarget() {
        return null;
    }

    @Override
    public @NotNull RenderPipeline getRenderPipeline() {
        return null;
    }

    @Override
    public VertexFormat format() {
        return null;
    }

    @Override
    public VertexFormat.Mode mode() {
        return null;
    }
}
