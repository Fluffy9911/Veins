package com.mixins.veins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.veins.Veins;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSource;

@Mixin(OreVeinifier.class)
public class OreVeinifierMixin {

	@Overwrite
	public static NoiseChunk.BlockStateFiller create(DensityFunction p_209668_, DensityFunction p_209669_,
			DensityFunction p_209670_, PositionalRandomFactory p_209671_) {
		return new NoiseChunk.BlockStateFiller() {

			@Override
			public BlockState calculate(FunctionContext p_209283_) {

				return null;
			}

		};
	}

}
