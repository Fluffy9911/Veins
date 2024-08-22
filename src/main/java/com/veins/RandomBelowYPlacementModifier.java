package com.veins;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomBelowYPlacementModifier extends PlacementModifier {
	public static final Codec<RandomBelowYPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Codec.INT.fieldOf("y").forGetter(RandomBelowYPlacementModifier::getYLevel),
					Codec.DOUBLE.fieldOf("chance").forGetter(RandomBelowYPlacementModifier::getChance))
			.apply(instance, RandomBelowYPlacementModifier::new));

	private final int yLevel;
	private final double chance;

	public RandomBelowYPlacementModifier(int yLevel, double chance) {
		this.yLevel = yLevel;
		this.chance = chance;
	}

	public int getYLevel() {
		return yLevel;
	}

	public double getChance() {
		return chance;
	}

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, Random random, BlockPos pos) {
		// Check if the position is below the specified Y-level

		if (pos.getY() < yLevel) {
			// Random chance to determine if the position should be used
			if (random.nextDouble() < chance) {
				return Stream.of(pos);
			}
		}
		return Stream.empty();
	}

	@Override
	public PlacementModifierType<?> type() {
		// TODO Auto-generated method stub
		return new PlacementModifierType<RandomBelowYPlacementModifier>() {

			@Override
			public Codec<RandomBelowYPlacementModifier> codec() {
				// TODO Auto-generated method stub
				return CODEC;
			}
		};
	}
}
