package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraArctinurus;
import net.lepidodendron.entity.model.entity.ModelArctinurus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderArctinurus extends RenderLivingBaseWithBook<EntityPrehistoricFloraArctinurus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/arctinurus.png");
    public static float getScaler() {
        return 0.3F * 0.78F;
    }

    public RenderArctinurus(RenderManager mgr) {
        super(mgr, new ModelArctinurus(), 0.2f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraArctinurus entity) {
        return RenderArctinurus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraArctinurus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraArctinurus entity, float f) {
        float scale = this.getScaler();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.15F;
    }

}