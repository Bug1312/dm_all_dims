package com.bug1312.dm_all_dims.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bug1312.dm_all_dims.AllDims;
import com.google.gson.JsonElement;
import com.swdteam.common.tardis.datapack.TardisLocationListener;

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

@Mixin(TardisLocationListener.class)
public class TardisLocationListenerMixin {

	@Inject(at = @At("TAIL"), method = "apply")
	public void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn, CallbackInfo ci) {
		AllDims.addDimensions();
	}
	
}
