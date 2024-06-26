package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraPhantaspis;
import net.lepidodendron.entity.model.entity.ModelPhantaspis;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPhantaspis extends RenderLivingBaseWithBook<EntityPrehistoricFloraPhantaspis> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/phantaspis.png");
    public static float getScaler() {
        return 0.21F;
    }

    public RenderPhantaspis(RenderManager mgr) {
        super(mgr, new ModelPhantaspis(), 0.00f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraPhantaspis entity) {
        return RenderPhantaspis.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraPhantaspis entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    @Override
    protected void preRenderCallback(EntityPrehistoricFloraPhantaspis entity, float f) {
        float scale = this.getScaler();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}