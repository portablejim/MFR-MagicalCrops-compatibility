package portablejim.compatibility;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;

public class Plantable implements IFactoryPlantable {
    private ItemSeeds seed;

    public Plantable(ItemSeeds seed) {
        this.seed = seed;
    }

    @Override
    public Item getSeed() {
        return seed;
    }

    @Override
    public boolean canBePlanted(ItemStack stack, boolean forFermenting) {
        return true;
    }

    @Override
    public ReplacementBlock getPlantedBlock(World world, int x, int y, int z, ItemStack stack) {
        return new ReplacementBlock(seed.getPlant(world, x, y, z));
    }

    @Override
    public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack) {
        if(!world.isAirBlock(x, y, z))
            return false;
        Block ground = world.getBlock(x, y - 1, z);
        return ground.equals(Blocks.farmland);
    }

    @Override
    public void prePlant(World world, int x, int y, int z, ItemStack stack) {
        Block ground = world.getBlock(x, y - 1, z);
        if (ground.equals(Blocks.grass) || ground.equals(Blocks.dirt))
        {
            world.setBlock(x, y - 1, z, Blocks.farmland);
        }
    }

    @Override
    public void postPlant(World world, int x, int y, int z, ItemStack stack) {

    }
}
