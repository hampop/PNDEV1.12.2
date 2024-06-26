package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraClevosaurus;
import net.lepidodendron.entity.model.entity.ModelClevosaurus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderClevosaurus extends RenderLivingBaseWithBook<EntityPrehistoricFloraClevosaurus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/clevosaurus.png");

    public RenderClevosaurus(RenderManager mgr) {
        super(mgr, new ModelClevosaurus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraClevosaurus entity) {
        return RenderClevosaurus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraClevosaurus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraClevosaurus entity, float f) {
        float scale = entity.getAgeScale() * 1.00F;
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.05F;
    }

}