package portablejim.compatibility;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.launchwrapper.LogWrapper;
import powercrystals.minefactoryreloaded.MFRRegistry;

@Mod(modid = MfrMagicCropsCompat.MODID, version = MfrMagicCropsCompat.VERSION)
public class MfrMagicCropsCompat
{
    public static final String MODID = "mfrmagiccropscompat";
    public static final String VERSION = "1.0";

    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void preInit(FMLPostInitializationEvent event)
    {
        LogWrapper.makeLog("Minefactory-MagicalCrops-compatibility");

        int countPlantableReged = 0;
        //noinspection unchecked
        for(Object itemObject : Item.itemRegistry.getKeys().toArray()) {
            if(itemObject instanceof String) {
                String itemString = (String) itemObject;
                if(itemString.startsWith("magicalcrops:")) {
                    Item item = (Item) Item.itemRegistry.getObject(itemString);
                    if(item instanceof ItemSeeds) {
                        Plantable plantable = new Plantable((ItemSeeds) item);
                        MFRRegistry.registerPlantable(plantable);
                        LogWrapper.fine("Adding %s to the %s list", itemString, "registerPlantable");
                        countPlantableReged++;
                    }
                }
            }
        }
        LogWrapper.fine("Plantable registered count %d", countPlantableReged);
        
        int countHarvestableReged = 0;
        for(Object blockObject : Block.blockRegistry.getKeys().toArray()) {
            if(blockObject instanceof String) {
                String blockString = (String) blockObject;
                if (blockString.startsWith("magicalcrops:")) {
                    Block block = (Block) Block.blockRegistry.getObject(blockString);
                    if (block instanceof BlockCrops || block instanceof BlockBush) {
                        Harvestable harvestable = new Harvestable(block);
                        MFRRegistry.registerHarvestable(harvestable);
                        LogWrapper.fine("Adding %s to the %s list", blockString, "registerHarvestable");
                        countHarvestableReged++;
                    }
                }
            }
        }
        LogWrapper.fine("Harvestable registered count %d", countHarvestableReged);
    }
}
