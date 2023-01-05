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

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;


public class Config {

	public final ConfigValue<Boolean> discoveryMode;
	public final ConfigValue<String[]> revealed;
	public final ConfigValue<Boolean> revealDatapacks;
	public final ConfigValue<String[]> blacklist;

	Config(ForgeConfigSpec.Builder builder) {
		builder.comment("DM: All Dimensions Configuration Settings").push("server");
		
		String[] _blacklist = { "dalekmod:tardis" };

		blacklist = builder.comment("This is a list of dimensions that will NEVER show in the dimensional selector.")
				.define("blacklist", _blacklist);
		
		builder.pop();
	}
	
    public static final Config SERVER;
    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        SERVER = specPair.getLeft();
    }
	
}
