// Copyright 2023 Bug1312
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.bug1312.dm_all_dims;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.data.TardisLocationRegistry;
import com.swdteam.common.tardis.data.TardisLocationRegistry.TardisLocation;

import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.PlayerData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod(AllDims.MOD_ID)
public class AllDims {
	public static final String MOD_ID = "dm_all_dims";
	public static Map<RegistryKey<World>, TardisLocation> REGISTRY_BEFORE_MOD;

	public AllDims() {
		MinecraftForge.EVENT_BUS.addListener(this::onServerStart);
	}
	
	// Must do this because data-packs are checked on FMLServerAboutToStartEvent where dimensions aren't accessible
	@SubscribeEvent
	public void onServerStart(FMLServerStartedEvent event) {
		addDimensions(); 
	}
	
	public static void addDimensions() {
		if (ServerLifecycleHooks.getCurrentServer() == null) return;
		REGISTRY_BEFORE_MOD = TardisLocationRegistry.getLocationRegistry();
		Set<RegistryKey<World>> dimensions = ServerLifecycleHooks.getCurrentServer().levelKeys();

		dimensions.forEach(dim -> {
			if (TardisLocationRegistry.getLocationForKey(dim) != null) return;
			TardisLocation location = new TardisLocation();
			
			try {
				FieldUtils.writeField(location, "dimension", dim.location().toString(), true);
				FieldUtils.writeField(location, "dimensionImage", new ResourceLocation("dalekmod:textures/planets/cave_game.png"), true);
		
				TardisLocationRegistry.getLocationRegistry().put(dim, location);
				TardisLocationRegistry.getLocationRegistryAsList().add(location);
			} catch (IllegalAccessException err) { err.printStackTrace(); }
		});
	}
	
	public static Map<RegistryKey<World>, TardisLocation> getLocationsForPlayer(BlockPos pos) {
		Map<RegistryKey<World>, TardisLocation> newMap = TardisLocationRegistry.getLocationRegistry().entrySet().stream()
				.filter(entry -> {			
					return !isBlackListed(entry);
					}).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
		return newMap;
	}
	
	private static boolean isBlackListed(Map.Entry<RegistryKey<World>, TardisLocation> entry) {
		return Arrays.asList(Config.SERVER.blacklist.get()).contains(entry.getKey().location().toString());
	}

	
}


