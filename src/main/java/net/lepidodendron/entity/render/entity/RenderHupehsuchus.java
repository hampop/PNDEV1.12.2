package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraHupehsuchus;
import net.lepidodendron.entity.model.entity.ModelHupehsuchus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHupehsuchus extends RenderLivingBaseWithBook<EntityPrehistoricFloraHupehsuchus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/hupehsuchus.png");

    public static float getScaler() {
        return 0.7F * 0.3F;
    }
    public RenderHupehsuchus(RenderManager mgr) {
        super(mgr, new ModelHupehsuchus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraHupehsuchus entity) {
        return RenderHupehsuchus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraHupehsuchus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraHupehsuchus entity, float f) {
        float scale = this.getScaler()*entity.getAgeScale();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}