package com.veins.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class FeatureBuilders {
	public  static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
			    DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "veins");

			  static RegistryObject<Codec<Modifier>> BIOME_MOD_CODEC = BIOME_MODIFIER_SERIALIZERS.register("biome_mod_codec", () ->
			    Codec.unit(new Modifier()));
}
