package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraArchosaurus;
import net.lepidodendron.entity.model.entity.ModelArchosaurus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderArchosaurus extends RenderLivingBaseWithBook<EntityPrehistoricFloraArchosaurus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/archosaurus.png");

    public RenderArchosaurus(RenderManager mgr) {
        super(mgr, new ModelArchosaurus(), 0.3f);
    }

    public static float getScaler() {return 0.33f; }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraArchosaurus entity) {
        return RenderArchosaurus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraArchosaurus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraArchosaurus entity, float f) {
        float scale = entity.getAgeScale() * this.getScaler();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.230F;
    }

}