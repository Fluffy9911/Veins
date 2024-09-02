package com.veins;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.NoiseChunk.BlockStateFiller;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
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
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import org.slf4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("veins")
public class Veins {
	public static String id = "veins";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();

	public Veins() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModConfig.buildConfig(bus);
		ModLoadingContext.get().registerConfig(Type.COMMON, ModConfig.SPEC);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		VeinsRegistry.bind(ForgeRegistries.FEATURES, VeinsRegistry::registerFeatures);

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

		if (ModConfig.ORE_VEIN_CONFIG.log.get()) {
			LogUtils.getLogger().debug("Creating vein of type: {} at position: {}, BranchProb: {}, Decay: {}, Size: {}",
					type, pos, branch, decay, veinSize);
		}

		List<BlockPos> veinPositions = OreVeinGenerator.generateOreVein(pos, veinSize, branch, decay, random);

		double probOre = ModConfig.ORE_VEIN_CONFIG.probOre.get();
		double probSpecial = ModConfig.ORE_VEIN_CONFIG.probSpecial.get();
		double probFiller = ModConfig.ORE_VEIN_CONFIG.probFiller.get();

		for (BlockPos blockPos : veinPositions) {
			if (shouldPlaceOre(type, level, blockPos, random)) {
				double randValue = random.nextDouble();

				if (randValue < probOre) {
					level.setBlock(blockPos, getRandomElement(type.ore), Block.UPDATE_ALL_IMMEDIATE);
				} else if (randValue < probOre + probSpecial) {
					level.setBlock(blockPos, getRandomElement(type.rawOreBlock), Block.UPDATE_ALL_IMMEDIATE);
				} else if (randValue < probOre + probSpecial + probFiller) {
					level.setBlock(blockPos, getRandomElement(type.filler), Block.UPDATE_ALL_IMMEDIATE);
				}
			}
		}
	}

	private static boolean shouldPlaceOre(VeinType type, WorldGenLevel level, BlockPos pos, Random random) {
		int blockY = pos.getY();
		if (ModConfig.ORE_VEIN_CONFIG.log.get())
			LogUtils.getLogger().debug("Checking placement conditions at Y: {}", blockY);

		BlockState currentBlock = level.getBlockState(pos);
		for (BlockState state : type.replace) {
			if (currentBlock.is(state.getBlock())) {
				if (ModConfig.ORE_VEIN_CONFIG.log.get())
					LogUtils.getLogger().debug("Placement conditions met at: {}", pos);
				return true;
			}
		}
		if (ModConfig.ORE_VEIN_CONFIG.log.get())
			LogUtils.getLogger().debug("Block at {} cannot be replaced, block at: {} is not one of: {}", pos,
					currentBlock, type.replace);
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
}
