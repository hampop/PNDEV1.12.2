package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraPhlegethontia;
import net.lepidodendron.entity.model.entity.ModelPhlegethontia;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPhlegethontia extends RenderLivingBaseWithBook<EntityPrehistoricFloraPhlegethontia> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/phlegethontia.png");

    public RenderPhlegethontia(RenderManager mgr) {
        super(mgr, new ModelPhlegethontia(), 0.0f);
    }

    public static float getScaler() {
        return 0.15F;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraPhlegethontia entity) {
        return RenderPhlegethontia.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraPhlegethontia entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraPhlegethontia entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.0F;
    }

}