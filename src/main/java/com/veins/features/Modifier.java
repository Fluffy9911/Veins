package com.veins.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.veins.ModConfig;
import com.veins.VeinsRegistry;
import com.veins.ModConfig.OreVeinConfig;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

import static com.veins.VeinsRegistry.*;

public record Modifier() implements BiomeModifier {
	
	
	
	public static OreVeinConfig CONFIG = ModConfig.ORE_VEIN_CONFIG;

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if( phase == Phase.MODIFY) {
		
		
		if (CONFIG.generateCopper.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, cpf);
	}

	if (CONFIG.generateIron.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, ipf);
	}

	if (CONFIG.generateGold.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, gpf);
	}

	if (CONFIG.generateDiamond.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, dpf);
	}

	if (CONFIG.generateCoal.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, cpfCoal);
	}

	if (CONFIG.generateLapis.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, lpf);
	}

	if (CONFIG.generateRedstone.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, rpf);
	}

	if (CONFIG.generateEmerald.get()) {
		builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, epf);
	}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		
		return FeatureBuilders.BIOME_MOD_CODEC.get();
	}

}
