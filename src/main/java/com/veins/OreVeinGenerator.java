package com.veins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;

public class OreVeinGenerator {

	public static List<BlockPos> generateOreVein(BlockPos start, int maxLength, double branchProbability,
			double decayRate, Random random) {
		List<BlockPos> positions = new ArrayList<>();

		generateBranch(start, maxLength, branchProbability, decayRate, random, positions, new ArrayList<>(), 0);
		if (positions.size() < 24) {
			return new ArrayList<>();
		}
		return positions;
	}

	private static void generateBranch(BlockPos start, int length, double branchProbability, double decayRate,
			Random random, List<BlockPos> positions, List<BlockPos> visited, int depth) {
		if (length <= 0 || depth > ModConfig.ORE_VEIN_CONFIG.maxVeinLength.get())
			return; // Increased depth for larger veins

		// Mark current position
		if (!visited.contains(start)) {
			positions.add(start);
			visited.add(start);
		}

		// Determine number of branches with more variation
		int numBranches = (int) Math.max(1, random.nextGaussian() * (1 / decayRate) + 2); // Added +2 for more branches
		numBranches = Math.min(numBranches, ModConfig.ORE_VEIN_CONFIG.maxBranches.get()); // Allow up to 8 branches for
																							// larger veins

		// Generate branches
		for (int i = 0; i < numBranches; i++) {
			if (random.nextDouble() < branchProbability) { // Changed to < for more control
				// Random direction and distance with larger range
				int dx = random.nextInt(5) - 2; // Larger range for more variation
				int dy = random.nextInt(3) - 1;
				int dz = random.nextInt(5) - 2; // Larger range for more variation

				// Ensure new position is within bounds and not revisited
				BlockPos newPos = start.offset(dx, dy, dz);
				if (!visited.contains(newPos) && withinBounds(newPos)) {
					// Generate branch recursively
					generateBranch(newPos, length - 1, branchProbability, decayRate, random, positions, visited,
							depth + 1);
				}
			}
		}
	}

	private static boolean withinBounds(BlockPos pos) {

		return pos.getY() >= -64 && pos.getY() <= 256;
	}
}
