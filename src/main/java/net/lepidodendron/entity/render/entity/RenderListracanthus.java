package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraListracanthus;
import net.lepidodendron.entity.model.entity.ModelListracanthus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderListracanthus extends RenderLivingBaseWithBook<EntityPrehistoricFloraListracanthus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/listracanthus.png");

    public RenderListracanthus(RenderManager mgr) {
        super(mgr, new ModelListracanthus(), 1.0f);
    }

    public static float getScaler() {return 0.6F; }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraListracanthus entity) {
        return RenderListracanthus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraListracanthus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraListracanthus entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        if (scale < 0.1f) {scale = 0.1f;}
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.45F;
    }

}