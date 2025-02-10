package net.arcspartan.templar_addons.block.custom;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockStateProperties extends BlockStateProperties {

    public static final IntegerProperty INFUSION_TABLE_CHARGES = IntegerProperty.create("infusion_table_charges", 0, 64);
    public static final int MIN_INFUSION_TABLE_CHARGES = 0;
    public static final int MAX_INFUSION_TABLE_CHARGES = 64;

}
