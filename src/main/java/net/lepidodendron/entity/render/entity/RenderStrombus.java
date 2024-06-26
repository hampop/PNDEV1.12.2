package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraStrombus;
import net.lepidodendron.entity.model.entity.ModelStrombus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderStrombus extends RenderLivingBaseWithBook<EntityPrehistoricFloraStrombus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/strombus.png");

    public static float getScaler() {
        return 0.7F * 0.395F;
    }
    public RenderStrombus(RenderManager mgr) {
        super(mgr, new ModelStrombus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraStrombus entity) {
        return RenderStrombus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraStrombus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    @Override
    protected void preRenderCallback(EntityPrehistoricFloraStrombus entity, float f) {
        float scale = this.getScaler();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}