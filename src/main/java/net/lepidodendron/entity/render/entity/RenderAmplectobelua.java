package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraAmplectobelua;
import net.lepidodendron.entity.model.entity.ModelAmplectobelua;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAmplectobelua extends RenderLivingBaseWithBook<EntityPrehistoricFloraAmplectobelua> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/amplectobelua.png");

    public static float getScaler() {return 0.42F;}
    public RenderAmplectobelua(RenderManager mgr) {
        super(mgr, new ModelAmplectobelua(), 0.2f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraAmplectobelua entity) {
        return RenderAmplectobelua.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraAmplectobelua entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    @Override
    protected void preRenderCallback(EntityPrehistoricFloraAmplectobelua entity, float f) {
        float scale = entity.getAgeScale()*getScaler();
        if (scale < 0.1f) {scale = 0.1f;}
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.45F;
    }

}