package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraBelonostomusJurassic;
import net.lepidodendron.entity.model.entity.ModelBelonostomus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBelonostomusJurassic extends RenderLivingBaseWithBook<EntityPrehistoricFloraBelonostomusJurassic> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/belonostomus_jurassic.png");

    public static float getScaler() {
        return 0.7F * 0.6F;
    }
    public RenderBelonostomusJurassic(RenderManager mgr) {
        super(mgr, new ModelBelonostomus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraBelonostomusJurassic entity) {
        return RenderBelonostomusJurassic.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraBelonostomusJurassic entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraBelonostomusJurassic entity, float f) {
        float scale = this.getScaler()*entity.getAgeScale();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}