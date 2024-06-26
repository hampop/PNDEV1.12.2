package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraRebellatrix;
import net.lepidodendron.entity.model.entity.ModelRebellatrix;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRebellatrix extends RenderLivingBaseWithBook<EntityPrehistoricFloraRebellatrix> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/rebellatrix.png");

    public RenderRebellatrix(RenderManager mgr) {
        super(mgr, new ModelRebellatrix(), 0.0f);
    }
    public static float getScaler() {return 0.343F; }
    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraRebellatrix entity) {
        return RenderRebellatrix.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraRebellatrix entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraRebellatrix entity, float f) {
        float scale = entity.getAgeScale();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}