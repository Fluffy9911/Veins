package com.veins;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.veins.ModConfig.OreVeinConfig;
import com.veins.features.OreFeature;
import com.veins.features.OreFeature.Configuration;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;




@Mod.EventBusSubscriber(modid = "veins", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VeinsRegistry {

	public static final ResourceKey<PlacedFeature> COPPER_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "coppervein"));
	public static final ResourceKey<PlacedFeature> IRON_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "ironvein"));
	public static final ResourceKey<PlacedFeature> GOLD_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "goldvein"));
	public static final ResourceKey<PlacedFeature> DIAMOND_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "diamondvein"));
	public static final ResourceKey<PlacedFeature> COAL_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "coalvein"));
	public static final ResourceKey<PlacedFeature> LAPIS_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "lapisvein"));
	public static final ResourceKey<PlacedFeature> REDSTONE_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "redstonevein"));
	public static final ResourceKey<PlacedFeature> EMERALD_VEIN = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
			new ResourceLocation(Veins.id, "emeraldvein"));

	public static Holder<ConfiguredFeature<Configuration, ?>> copper;
	public static Holder<ConfiguredFeature<Configuration, ?>> iron;
	public static Holder<ConfiguredFeature<Configuration, ?>> gold;
	public static Holder<ConfiguredFeature<Configuration, ?>> diamond;
	public static Holder<ConfiguredFeature<Configuration, ?>> coal;
	public static Holder<ConfiguredFeature<Configuration, ?>> lapis;
	public static Holder<ConfiguredFeature<Configuration, ?>> redstone;
	public static Holder<ConfiguredFeature<Configuration, ?>> emerald;

	public static Holder<PlacedFeature> cpf;
	public static Holder<PlacedFeature> ipf;
	public static Holder<PlacedFeature> gpf;
	public static Holder<PlacedFeature> dpf;
	public static Holder<PlacedFeature> cpfCoal;
	public static Holder<PlacedFeature> lpf;
	public static Holder<PlacedFeature> rpf;
	public static Holder<PlacedFeature> epf;

	

	public static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
		FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
			if (registry.equals(event.getRegistryKey())) {
				source.accept((t, rl) -> event.register(registry, rl, () -> t));
			}
		});
	}
	public static void registerFeatures(BiConsumer<Feature<?>, ResourceLocation> r) {
		copper = registerFeature(r, VeinType.COPPER, COPPER_VEIN, 5, 50, 512, 60, 0.01);
		iron = registerFeature(r, VeinType.IRON, IRON_VEIN, 5, 50, 256, 60, 0.01);
		gold = registerFeature(r, VeinType.GOLD, GOLD_VEIN, 5, 50, 64, 60, 0.01);
		diamond = registerFeature(r, VeinType.DIAMOND, DIAMOND_VEIN, 3, 30, 32, 16, 0.01);
		coal = registerFeature(r, VeinType.COAL, COAL_VEIN, 10, 100, 128, 128, 0.01);
		lapis = registerFeature(r, VeinType.LAPIS, LAPIS_VEIN, 2, 20, 32, 30, 0.01);
		redstone = registerFeature(r, VeinType.REDSTONE, REDSTONE_VEIN, 4, 40, 32, 16, 0.01);
		emerald = registerFeature(r, VeinType.EMERALD, EMERALD_VEIN, 1, 10, 16, 30, 0.01);

		// Set Holder<PlacedFeature> variables
		cpf = registerPlacedFeature(COPPER_VEIN, copper, 60, 0.01,VeinType.COPPER);
		ipf = registerPlacedFeature(IRON_VEIN, iron, 60, 0.01,VeinType.IRON);
		gpf = registerPlacedFeature(GOLD_VEIN, gold, 60, 0.01,VeinType.GOLD);
		dpf = registerPlacedFeature(DIAMOND_VEIN, diamond, 16, 0.01,VeinType.DIAMOND);
		cpfCoal = registerPlacedFeature(COAL_VEIN, coal, 128, 0.01,VeinType.COAL);
		lpf = registerPlacedFeature(LAPIS_VEIN, lapis, 30, 0.01,VeinType.LAPIS);
		rpf = registerPlacedFeature(REDSTONE_VEIN, redstone, 16, 0.01,VeinType.REDSTONE);
		epf = registerPlacedFeature(EMERALD_VEIN, emerald, 30, 0.01,VeinType.EMERALD);
	}
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, "veins");

    public static final RegistryObject<PlacementModifierType<RandomBelowYPlacementModifier>> RANDOM_BELOW_Y =
            PLACEMENT_MODIFIERS.register("random_below_y", 
                    () -> () -> RandomBelowYPlacementModifier.CODEC);
	 public static Map<ResourceLocation, PlacedFeature> createFeatureMap() {
	        return Map.of(
	            COPPER_VEIN.location(), cpf.get(),
	            IRON_VEIN.location(), ipf.get(),
	            GOLD_VEIN.location(), gpf.get(),
	            DIAMOND_VEIN.location(), dpf.get(),
	            COAL_VEIN.location(), cpfCoal.get(),
	            LAPIS_VEIN.location(), lpf.get(),
	            REDSTONE_VEIN.location(), rpf.get(),
	            EMERALD_VEIN.location(), epf.get()
	        );
	    }
	// Helper method to register a feature and its placement
	private static Holder<ConfiguredFeature<Configuration, ?>> registerFeature(
			BiConsumer<Feature<?>, ResourceLocation> r, VeinType veinType, ResourceKey<PlacedFeature> resourceKey,
			int minBranch, int maxBranch, int veinSize, int maxY, double chance) {

		var feature = new OreFeature(OreFeature.Configuration.CODEC, minBranch, maxBranch, 0, chance);
		var config = new OreFeature.Configuration(veinType, veinSize);

		r.accept(feature, resourceKey.location());

		return FeatureUtils.register(resourceKey.location().toString(), feature, config);
	}

	// Helper method to register a placed feature
	private static Holder<PlacedFeature> registerPlacedFeature(ResourceKey<PlacedFeature> resourceKey,
			Holder<ConfiguredFeature<Configuration, ?>> configuredFeature, int maxY, double chance,VeinType type) {

		return PlacementUtils.register(resourceKey.location().toString(), configuredFeature,
				new RandomBelowYPlacementModifier(type.maxY, ModConfig.getOrDef(chance, type)), PlacementUtils.FULL_RANGE);
	}

	public static OreVeinConfig CONFIG = ModConfig.ORE_VEIN_CONFIG;

//	@SubscribeEvent
//	public static void onBiomeLoading(BiomeLoadingEvent event) {
//		// Check if the biome category is underground
//		if (event.getCategory() == Biome.BiomeCategory.UNDERGROUND) {
//			// Check each ore vein generation setting from the config before adding to the
//			// biome
//
//			if (CONFIG.generateCopper.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, cpf);
//			}
//
//			if (CONFIG.generateIron.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, ipf);
//			}
//
//			if (CONFIG.generateGold.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, gpf);
//			}
//
//			if (CONFIG.generateDiamond.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, dpf);
//			}
//
//			if (CONFIG.generateCoal.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, cpfCoal);
//			}
//
//			if (CONFIG.generateLapis.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, lpf);
//			}
//
//			if (CONFIG.generateRedstone.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, rpf);
//			}
//
//			if (CONFIG.generateEmerald.get()) {
//				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, epf);
//			}
		
	//}
}
