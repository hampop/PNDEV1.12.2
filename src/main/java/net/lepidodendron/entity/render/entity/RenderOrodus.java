package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraOrodus;
import net.lepidodendron.entity.model.entity.ModelOrodus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderOrodus extends RenderLivingBaseWithBook<EntityPrehistoricFloraOrodus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/orodus.png");

    public RenderOrodus(RenderManager mgr) {
        super(mgr, new ModelOrodus(), 0.0f);
    }

    public static float getScaler() {return 0.475F; }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraOrodus entity) {
        return RenderOrodus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraOrodus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraOrodus entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.35F;
    }

}

