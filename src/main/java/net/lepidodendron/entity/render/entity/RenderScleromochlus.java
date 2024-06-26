package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraScleromochlus;
import net.lepidodendron.entity.model.entity.ModelScleromochlus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderScleromochlus extends RenderLivingBaseWithBook<EntityPrehistoricFloraScleromochlus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/scleromochlus.png");
    public static float getScaler() {
        return 0.15F;
    }

    public RenderScleromochlus(RenderManager mgr) {
        super(mgr, new ModelScleromochlus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraScleromochlus entity) {
        return RenderScleromochlus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraScleromochlus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraScleromochlus entity, float f) {
        float scale = entity.getAgeScale() * 1.125F;
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.1F;
    }

}



