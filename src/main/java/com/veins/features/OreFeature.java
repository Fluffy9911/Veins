package com.veins.features;

import java.util.Random;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.veins.VeinType;
import com.veins.Veins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class OreFeature extends Feature<OreFeature.Configuration> {
	double bmin, bmax, ddmin, ddmax;

	public OreFeature(Codec<Configuration> p, double bmin, double bmax, double ddmin, double ddmax) {
		super(p);
		this.bmin = bmin;
		this.bmax = bmax;
		this.ddmin = ddmin;
		this.ddmax = ddmax;

	}

	public OreFeature(Codec<OreFeature.Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<OreFeature.Configuration> context) {
		WorldGenLevel level = context.level();
		Random random = context.random();
		BlockPos pos = context.origin();
		Configuration config = context.config();

		VeinType veinType = config.veinType;
		int veinSize = config.veinSize;

		Veins.createVein(veinType, veinSize, level, pos, random, random.nextDouble(bmin, bmax),
				random.nextDouble(ddmin, ddmax));
		LogUtils.getLogger().debug("Placed");
		return true;
	}

	// Custom configuration class for this feature
	public static class Configuration implements FeatureConfiguration {
		public final VeinType veinType;
		public final int veinSize;

		public Configuration(VeinType veinType, int veinSize) {
			this.veinType = veinType;
			this.veinSize = veinSize;
		}

		// Implement codec or serializer here for the configuration
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance
				.group(VeinType.CODEC.fieldOf("vein_type").forGetter(config -> config.veinType),
						Codec.INT.fieldOf("vein_size").orElse(10).forGetter(config -> config.veinSize))
				.apply(instance, Configuration::new));
	}
}
