package com.veins;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import com.veins.ModConfig.OreVeinConfig;
import com.veins.features.FeatureBuilders;
import com.veins.features.Modifier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.NoiseChunk.BlockStateFiller;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import org.slf4j.Logger;

import static com.veins.VeinsRegistry.cpf;
import static com.veins.VeinsRegistry.cpfCoal;
import static com.veins.VeinsRegistry.dpf;
import static com.veins.VeinsRegistry.epf;
import static com.veins.VeinsRegistry.gpf;
import static com.veins.VeinsRegistry.ipf;
import static com.veins.VeinsRegistry.lpf;
import static com.veins.VeinsRegistry.rpf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import static com.veins.VeinsRegistry.*;
// The value here should match an entry in the META-INF/mods.toml file
@Mod("veins")
public class Veins {
	public static String id = "veins";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();
	public static OreVeinConfig CONFIG;

	public Veins() {
		
IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModConfig.buildConfig(bus);
	ModLoadingContext.get().registerConfig(Type.COMMON, ModConfig.SPEC);
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		VeinsRegistry.PLACEMENT_MODIFIERS.register(bus);	
		
	VeinsRegistry.bind(Registries.FEATURE, VeinsRegistry::registerFeatures);
		MinecraftForge.EVENT_BUS.register(this);
		CONFIG = ModConfig.ORE_VEIN_CONFIG;
FeatureBuilders.BIOME_MODIFIER_SERIALIZERS.register(bus);
FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gather);
	}

	private void setup(final FMLCommonSetupEvent event) {
		
	        
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {

	}

	private void processIMC(final InterModProcessEvent event) {

	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {

	}

	// You can use EventBusSubscriber to automatically subscribe events on the
	// contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

	}

	public static void createVein(VeinType type, int veinSize, WorldGenLevel level, BlockPos pos, Random random,
			double branch, double decay) {

		double bp = branch;
		double dd = decay;
		int size = veinSize;
		if (ModConfig.ORE_VEIN_CONFIG.log.get())
			LogUtils.getLogger().debug("Creating vein of type: {} at position: {}, BranchProb: {}, Decay: {}, Size: {}",
					type, pos, bp, dd, size);
		List<BlockPos> vpo = OreVeinGenerator.generateOreVein(pos, size, bp, dd, random);
		for (Iterator iterator = vpo.iterator(); iterator.hasNext();) {
			BlockPos blockPos = (BlockPos) iterator.next();
			if (shouldPlaceOre(type, level, blockPos, random)) {

				if (rchance(random, ModConfig.ORE_VEIN_CONFIG.probOre.get())) {

					level.setBlock(blockPos, getRandomElement(type.ore), Block.UPDATE_ALL_IMMEDIATE);
				} else if (rchance(random, ModConfig.ORE_VEIN_CONFIG.probSpecial.get())) {
					level.setBlock(blockPos, getRandomElement(type.rawOreBlock), Block.UPDATE_ALL_IMMEDIATE);
				} else if (rchance(random, ModConfig.ORE_VEIN_CONFIG.probFiller.get())) {
					level.setBlock(blockPos, getRandomElement(type.filler), Block.UPDATE_ALL_IMMEDIATE);
				}

			}
		}

		return;
	}

	private static boolean shouldPlaceOre(VeinType type, WorldGenLevel level, BlockPos pos, Random random) {
		int blockY = pos.getY();
		if (ModConfig.ORE_VEIN_CONFIG.log.get())
			LogUtils.getLogger().debug("Checking placement conditions at Y: {}", blockY);

		BlockState currentBlock = level.getBlockState(pos);
		for (BlockState state : type.replace) {
			if (currentBlock.is(state.getBlock()) ) {
				if (ModConfig.ORE_VEIN_CONFIG.log.get())
					LogUtils.getLogger().debug("Placement conditions met at: {}", pos);
				return true;
			}
		}
		if (ModConfig.ORE_VEIN_CONFIG.log.get())
			LogUtils.getLogger().debug("Block at {} cannot be replaced", pos);
		return false;
	}

	private static final Random RANDOM = new Random();

	// Generic method to get a random element from an array
	public static <T> T getRandomElement(T[] array) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException("Array must not be null or empty");
		}
		int index = RANDOM.nextInt(array.length);
		return array[index];
	}

	public static boolean rchance(Random r, int i) {
		if (i == 0) {
			return false;
		}
		if (r.nextInt(0, 100) < i) {
			return true;
		}
		return false;
	}
	public  void gather(GatherDataEvent event) {
RegistrySetBuilder rsb = new RegistrySetBuilder();
		
	}
}
