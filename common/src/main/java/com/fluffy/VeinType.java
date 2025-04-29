package com.fluffy;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public  enum VeinType {
    COPPER(Blocks.COPPER_ORE.defaultBlockState(), Blocks.RAW_COPPER_BLOCK.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), 0, 50),
    IRON(Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(), Blocks.RAW_IRON_BLOCK.defaultBlockState(), Blocks.TUFF.defaultBlockState(), -60, -8),
    GOLD(Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState(), Blocks.RAW_GOLD_BLOCK.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), -60, -8),
    DIAMOND(Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), -64, -16),
    REDSTONE(Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), -64, -32),
    EMERALD(Blocks.EMERALD_ORE.defaultBlockState(), Blocks.STONE.defaultBlockState(), Blocks.STONE.defaultBlockState(), -32, 32),
    LAPIS(Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), -64, 0);
        public BlockState ore;
        public BlockState rawOreBlock;
        public BlockState filler;
        public final int minY;
        public final int maxY;

        private VeinType(BlockState blockState, BlockState blockState2, BlockState blockState3, int j, int k) {
            this.ore = blockState;
            this.rawOreBlock = blockState2;
            this.filler = blockState3;
            this.minY = j;
            this.maxY = k;
        }
    }