package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraNerinea;
import net.lepidodendron.entity.model.entity.ModelNerinea;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNerinea extends RenderLivingBaseWithBook<EntityPrehistoricFloraNerinea> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/nerinea.png");

    public static float getScaler() {
        return 0.7F * 0.28F;
    }
    public RenderNerinea(RenderManager mgr) {
        super(mgr, new ModelNerinea(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraNerinea entity) {
        return RenderNerinea.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraNerinea entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    @Override
    protected void preRenderCallback(EntityPrehistoricFloraNerinea entity, float f) {
        float scale = this.getScaler();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}