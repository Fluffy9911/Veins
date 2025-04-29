package com.fluffy.neoforge;

import net.neoforged.fml.common.Mod;

import com.fluffy.VeinsCommon;

@Mod(VeinsCommon.MOD_ID)
public final class VeinsNeoForge {
    public VeinsNeoForge() {
        // Run our common setup.
        VeinsCommon.init();
    }
}
