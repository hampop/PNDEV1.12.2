package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraOnychodus;
import net.lepidodendron.entity.model.entity.ModelOnychodus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderOnychodus extends RenderLivingBaseWithBook<EntityPrehistoricFloraOnychodus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/onychodus.png");

    public RenderOnychodus(RenderManager mgr) {
        super(mgr, new ModelOnychodus(), 0.6f);
    }

    public static float getScaler() {return 0.750F; }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraOnychodus entity) {
        return RenderOnychodus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraOnychodus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraOnychodus entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        if (scale < 0.1f) {scale = 0.1f;}
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.45F;
    }

}