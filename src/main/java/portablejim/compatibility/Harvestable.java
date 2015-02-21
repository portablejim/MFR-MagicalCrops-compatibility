package portablejim.compatibility;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by james on 22/02/15.
 */
public class Harvestable implements IFactoryHarvestable {
    Block block;

    public Harvestable(Block block) {
        this.block = block;
    }

    @Override
    public Block getPlant() {
        return block;
    }

    @Override
    public HarvestType getHarvestType() {
        return HarvestType.Normal;
    }

    @Override
    public boolean breakBlock() {
        return true;
    }

    @Override
    public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) >= 7;
    }

    @Override
    public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        return block == null ? null : block.getDrops(world, x, y, z, meta, 0);
    }

    @Override
    public void preHarvest(World world, int x, int y, int z) {

    }

    @Override
    public void postHarvest(World world, int x, int y, int z) {

    }
}
