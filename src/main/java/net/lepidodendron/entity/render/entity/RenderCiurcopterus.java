package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraCiurcopterus;
import net.lepidodendron.entity.model.entity.ModelCiurcopterus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCiurcopterus extends RenderLivingBaseWithBook<EntityPrehistoricFloraCiurcopterus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/ciurcopterus.png");

    public static float getScaler() {
        return 0.43F;
    }
    public RenderCiurcopterus(RenderManager mgr) {
        super(mgr, new ModelCiurcopterus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraCiurcopterus entity) {
        return RenderCiurcopterus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraCiurcopterus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    @Override
    protected void preRenderCallback(EntityPrehistoricFloraCiurcopterus entity, float f) {
        float scale = this.getScaler() * entity.getAgeScale();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}