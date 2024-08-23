package com.veins;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.Tuple;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "veins", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {

	public static ForgeConfigSpec SPEC;
	public static OreVeinConfig ORE_VEIN_CONFIG;
	public static ForgeConfigSpec.Builder builder;

	public static HashMap<VeinType,Tuple<ForgeConfigSpec.IntValue,ForgeConfigSpec.IntValue>> vals = new HashMap<>();
	
	public static void buildConfig(IEventBus bus) {
		builder = new Builder();
		final Pair<OreVeinConfig, ForgeConfigSpec> specPair = builder.configure(OreVeinConfig::new);
		SPEC = specPair.getRight();
		ORE_VEIN_CONFIG = specPair.getLeft();
	
	}

	public static class OreVeinConfig {
	    public final ForgeConfigSpec.IntValue maxVeinLength;
	    public final ForgeConfigSpec.IntValue maxBranches;
	    public ForgeConfigSpec.IntValue probOre;
	    public ForgeConfigSpec.IntValue probFiller;
	    public ForgeConfigSpec.IntValue probSpecial;

	    public final ForgeConfigSpec.DoubleValue branchProbability;
	    public final ForgeConfigSpec.DoubleValue decayRate;
	    public ForgeConfigSpec.BooleanValue generateCopper;
		public ForgeConfigSpec.BooleanValue generateIron;
		public ForgeConfigSpec.BooleanValue generateGold;
		public ForgeConfigSpec.BooleanValue generateDiamond;
		public ForgeConfigSpec.BooleanValue generateCoal;
		public ForgeConfigSpec.BooleanValue generateLapis;
		public ForgeConfigSpec.BooleanValue generateRedstone;
		public ForgeConfigSpec.BooleanValue generateEmerald;
		public ForgeConfigSpec.DoubleValue generationFrequency;

	    public ForgeConfigSpec.BooleanValue log;

	    public HashMap<VeinType, ForgeConfigSpec.DoubleValue> veinGenerationFrequencies = new HashMap<>();

	    public OreVeinConfig(ForgeConfigSpec.Builder builder) {
	        builder.push("Ore Vein Configuration");

	        maxVeinLength = builder.comment("Maximum length of the ore veins, WILL HAVE AN AFFECT ON PERFORMANCE")
	                .defineInRange("maxVeinLength", 50, 10, 100);

	        branchProbability = builder.comment("Probability of branching, WILL HAVE AN AFFECT ON PERFORMANCE")
	                .defineInRange("branchProbability", 0.3, 0.0, 1.0);

	        decayRate = builder.comment("Rate at which branches decay").defineInRange("decayRate", 0.5, 0.0, 1.0);
	        maxBranches = builder.comment("Maximun Branches, WILL HAVE AN AFFECT ON PERFORMANCE")
	                .defineInRange("max_branches", 8, 1, 512);

	        probOre = builder.comment("Probability of creating an ore block").defineInRange("ore_prob", 24, 0, 100);
	        probSpecial = builder.comment("Probability of creating an special ore block")
	                .defineInRange("special_ore_prob", 2, 0, 100);
	        probFiller = builder.comment("Probability of creating an filler block").defineInRange("filler_prob", 50, 0,
	                100);
	        builder.pop();

	        builder.push("Misc");

	        log = builder.comment("Whether Ore Veins should be logged when created").define("log", false);

	        builder.pop();
	        builder.push("Vein Generation");

			generateCopper = builder.comment("Whether Copper Vein should generate").define("generateCopper", true);

			generateIron = builder.comment("Whether Iron Vein should generate").define("generateIron", true);

			generateGold = builder.comment("Whether Gold Vein should generate").define("generateGold", true);

			generateDiamond = builder.comment("Whether Diamond Vein should generate").define("generateDiamond", true);

			generateCoal = builder.comment("Whether Coal Vein should generate").define("generateCoal", true);

			generateLapis = builder.comment("Whether Lapis Vein should generate").define("generateLapis", true);

			generateRedstone = builder.comment("Whether Redstone Vein should generate").define("generateRedstone",
					true);

			generateEmerald = builder.comment("Whether Emerald Vein should generate").define("generateEmerald", true);

			builder.pop();
	        // Configuration for whether each ore vein type should generate
	        builder.push("Vein Generation Frequency");
	        for (VeinType v : VeinType.values()) {
	            ForgeConfigSpec.DoubleValue frequency = builder.comment("Generation frequency for " + v.name())
	                    .defineInRange(v.name() + "_generationFrequency", 0.01, 0.0, 1.0);
	            veinGenerationFrequencies.put(v, frequency);
	        }
	        builder.pop();

	        builder.push("veins spawn heights");
	        for (VeinType v : VeinType.values()) {
	            builder.comment("Config Height For: " + v.name());
	            vals.put(v, new Tuple<ForgeConfigSpec.IntValue, ForgeConfigSpec.IntValue>(
	                    builder.defineInRange(v.name() + "_min_spawn_y", v.minY, -64, 256),
	                    builder.defineInRange(v.name() + "_max_spawn_y", v.maxY, -64, 256)));
	        }
	        builder.pop();
	    }
	}


}
