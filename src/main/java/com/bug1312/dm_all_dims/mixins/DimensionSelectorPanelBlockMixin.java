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

package com.bug1312.dm_all_dims.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.bug1312.dm_all_dims.AllDims;
import com.swdteam.common.block.tardis.DimensionSelectorPanelBlock;
import com.swdteam.common.tardis.data.TardisLocationRegistry.TardisLocation;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

@Mixin(DimensionSelectorPanelBlock.class)
public class DimensionSelectorPanelBlockMixin {
		
	@Redirect(at = @At(value = "INVOKE", target = "Lcom/swdteam/common/tardis/data/TardisLocationRegistry;getLocationRegistry()Ljava/util/Map;"), method = "use")
	private Map<RegistryKey<World>, TardisLocation> getLocationRegistry(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return AllDims.getLocationsForPlayer(pos);
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "Lcom/swdteam/common/tardis/data/TardisLocationRegistry;getLocationRegistryAsList()Ljava/util/List;"), method = "use")
	private List<TardisLocation> getLocationRegistryAsList(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		Map<RegistryKey<World>, TardisLocation> map = AllDims.getLocationsForPlayer(pos);
		List<TardisLocation> list = new ArrayList<TardisLocation>(map.values());
		return list;
	}
	
}
