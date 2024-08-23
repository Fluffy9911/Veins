package com.veins;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public enum VeinType {
	COPPER(new BlockState[] { Blocks.COPPER_ORE.defaultBlockState(), Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState() },
			new BlockState[] { Blocks.RAW_COPPER_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.GRANITE.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, 0, 256),

	IRON(new BlockState[] { Blocks.IRON_ORE.defaultBlockState(), Blocks.DEEPSLATE_IRON_ORE.defaultBlockState() },
			new BlockState[] { Blocks.RAW_IRON_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.TUFF.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, -64, 50),

	GOLD(new BlockState[] { Blocks.GOLD_ORE.defaultBlockState(), Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState() },
			new BlockState[] { Blocks.RAW_GOLD_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.ANDESITE.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, -64, 0),

	DIAMOND(new BlockState[] { Blocks.DIAMOND_ORE.defaultBlockState(),
			Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState() },
			new BlockState[] { Blocks.DIAMOND_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.SMOOTH_BASALT.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, -64, 0),

	COAL(new BlockState[] { Blocks.COAL_ORE.defaultBlockState(), Blocks.DEEPSLATE_COAL_ORE.defaultBlockState() },
			new BlockState[] { Blocks.COAL_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.DIRT.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, -64, 0),

	LAPIS(new BlockState[] { Blocks.LAPIS_ORE.defaultBlockState(), Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState() },
			new BlockState[] { Blocks.LAPIS_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.CLAY.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, -64, 0),

	REDSTONE(
			new BlockState[] { Blocks.REDSTONE_ORE.defaultBlockState(),
					Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState() },
			new BlockState[] { Blocks.REDSTONE_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.GRAVEL.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, -64, 0),

	EMERALD(new BlockState[] { Blocks.EMERALD_ORE.defaultBlockState(),
			Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState() },
			new BlockState[] { Blocks.EMERALD_BLOCK.defaultBlockState() },
			new BlockState[] { Blocks.COBBLESTONE.defaultBlockState() },
			new BlockState[] { Blocks.STONE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState() }, -64, 256);

	public static final Codec<VeinType> CODEC = Codec.STRING.xmap(VeinType::valueOf, VeinType::name);
	public BlockState[] ore;
	public BlockState[] rawOreBlock;
	public BlockState[] filler;
	public BlockState[] replace;
	public int minY;
	public int maxY;

	VeinType(BlockState[] ore, BlockState[] raw_ore, BlockState[] filler, BlockState[] replace, int minY, int maxY) {
		this.ore = ore;
		this.rawOreBlock = raw_ore;
		this.filler = filler;
		this.minY = minY;
		this.maxY = maxY;
		this.replace = replace;

	}
}