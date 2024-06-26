package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraLabidosaurus;
import net.lepidodendron.entity.model.entity.ModelLabidosaurus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLabidosaurus extends RenderLivingBaseWithBook<EntityPrehistoricFloraLabidosaurus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/labidosaurus.png");
    public static float getScaler() {
        return 0.385f;
    }

    public RenderLabidosaurus(RenderManager mgr) {
        super(mgr, new ModelLabidosaurus(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraLabidosaurus entity) {
        return RenderLabidosaurus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraLabidosaurus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraLabidosaurus entity, float f) {
        float scale = entity.getAgeScale() * getScaler();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.2F;
    }

}