package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraEdestus;
import net.lepidodendron.entity.model.entity.ModelEdestus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEdestus extends RenderLivingBaseWithBook<EntityPrehistoricFloraEdestus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/edestus.png");

    public RenderEdestus(RenderManager mgr) {
        super(mgr, new ModelEdestus(), 0.5f);
    }

    public static float getScaler() {
        return 1.65F;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraEdestus entity) {
        return RenderEdestus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraEdestus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraEdestus entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        if (scale < 0.1f) {scale = 0.1f;}
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.45F;
    }

}