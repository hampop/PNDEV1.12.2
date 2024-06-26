package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraLysorophus;
import net.lepidodendron.entity.model.entity.ModelLysorophus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLysorophus extends RenderLivingBaseWithBook<EntityPrehistoricFloraLysorophus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/lysorophus.png");

    public RenderLysorophus(RenderManager mgr) {
        super(mgr, new ModelLysorophus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraLysorophus entity) {
        return RenderLysorophus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraLysorophus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

}


