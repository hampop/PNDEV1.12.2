package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraMcnamaraspis;
import net.lepidodendron.entity.model.entity.ModelMcnamaraspis;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMcnamaraspis extends RenderLivingBaseWithBook<EntityPrehistoricFloraMcnamaraspis> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/mcnamaraspis.png");

    public RenderMcnamaraspis(RenderManager mgr) {
        super(mgr, new ModelMcnamaraspis(), 0.0f);
    }

    public static float getScaler() {return 0.135F; }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraMcnamaraspis entity) {
        return RenderMcnamaraspis.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraMcnamaraspis entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraMcnamaraspis entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        if (scale < 0.1f) {scale = 0.1f;}
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.0F;
    }

}