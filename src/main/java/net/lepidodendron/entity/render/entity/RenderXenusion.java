package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraXenusion;
import net.lepidodendron.entity.model.entity.ModelXenusion;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderXenusion extends RenderLivingBaseWithBook<EntityPrehistoricFloraXenusion> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/xenusion.png");

    public RenderXenusion(RenderManager mgr) {
        super(mgr, new ModelXenusion(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraXenusion entity) {
        return RenderXenusion.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraXenusion entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraXenusion entity, float f) {
        GlStateManager.scale(1.35, 1.35, 1.35);
    }

}