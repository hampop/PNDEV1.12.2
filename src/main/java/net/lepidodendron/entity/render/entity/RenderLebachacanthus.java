package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraLebachacanthus;
import net.lepidodendron.entity.model.entity.ModelLebachacanthus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLebachacanthus extends RenderLivingBaseWithBook<EntityPrehistoricFloraLebachacanthus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/lebachacanthus.png");

    public RenderLebachacanthus(RenderManager mgr) {
        super(mgr, new ModelLebachacanthus(), 0.0f);
    }

    public static float getScaler() {return 0.59F*1.25f; }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraLebachacanthus entity) {
        return RenderLebachacanthus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraLebachacanthus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraLebachacanthus entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        if (scale < 0.1f) {scale = 0.1f;}
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.35F;
    }

}