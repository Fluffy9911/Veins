package com.fluffy.mixin;

import com.fluffy.VeinType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(OreVeinifier.class)
public class VeinMixin {

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static NoiseChunk.BlockStateFiller create(DensityFunction veinNoise, DensityFunction gapNoise, DensityFunction oreChanceNoise, PositionalRandomFactory randomFactory) {
        BlockState air = null;
        return (context) -> {
            double veininess = veinNoise.compute(context);
            int y = context.blockY();
            double absVeininess = Math.abs(veininess);

            VeinType veinType;
            if (veininess > 0.6) {
                veinType = VeinType.DIAMOND;
            } else if (veininess > 0.3) {
                veinType = VeinType.GOLD;
            } else if (veininess > 0.1) {
                veinType = VeinType.COPPER;
            } else if (veininess > -0.1) {
                veinType = VeinType.IRON;
            } else if (veininess > -0.4) {
                veinType = VeinType.REDSTONE;
            } else if (veininess > -0.6) {
                veinType = VeinType.LAPIS;
            } else {
                veinType = VeinType.EMERALD;
            }

            int aboveMax = veinType.maxY - y;
            int belowMin = y - veinType.minY;
            if (belowMin >= 0 && aboveMax >= 0) {
                int distanceToEdge = Math.min(aboveMax, belowMin);
                double edgeSoftening = Mth.clampedMap(distanceToEdge, 0.0, 20.0, -0.2, 0.0);
                if (absVeininess + edgeSoftening < 0.4) {
                    return air;
                } else {
                    RandomSource random = randomFactory.at(context.blockX(), y, context.blockZ());
                    if (random.nextFloat() > 0.7F) {
                        return air;
                    } else if (gapNoise.compute(context) >= 0.0) {
                        return air;
                    } else {
                        double richness = Mth.clampedMap(absVeininess, 0.4, 0.6, 0.1, 0.3);
                        if (random.nextFloat() < richness && oreChanceNoise.compute(context) > -0.3) {
                            // Gold and Iron have raw blocks sometimes
                            if (random.nextFloat() < 0.02F && veinType.rawOreBlock != null) {
                                return veinType.rawOreBlock;
                            }
                            return veinType.ore;
                        } else {
                            return veinType.filler;
                        }
                    }
                }
            } else {
                return air;
            }
        };
    }
}
