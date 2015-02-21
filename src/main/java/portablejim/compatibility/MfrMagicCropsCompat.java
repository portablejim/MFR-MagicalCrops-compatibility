package portablejim.compatibility;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.commons.io.IOUtils;
import powercrystals.minefactoryreloaded.MFRRegistry;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Set;

@Mod(modid = MfrMagicCropsCompat.MODID, version = MfrMagicCropsCompat.VERSION, dependencies = "after:magicalcrops")
public class MfrMagicCropsCompat
{
    public static final String MODID = "mfrmagiccropscompat";
    public static final String VERSION = "1.0";

    public static final String filesPath = "portablejim/compat/minefactory-magicalcrops/";
    
    @EventHandler
    public void preInit(FMLPostInitializationEvent event)
    {
        LogWrapper.makeLog("Minefactory-MagicalCrops-compatibility");

        //noinspection unchecked
        for(Object itemObject : Item.itemRegistry.getKeys().toArray()) {
            if(itemObject instanceof String) {
                String itemString = (String) itemObject;
                if(itemString.startsWith("magicalcrops:magicalcrops_")) {
                    Item item = (Item) Item.itemRegistry.getObject(itemString);
                    if(item instanceof ItemSeeds) {
                        Plantable plantable = new Plantable((ItemSeeds) item);
                        MFRRegistry.registerPlantable(plantable);
                        LogWrapper.fine("Adding %s to the %s list", itemString, "registerPlantable");
                    }
                }
            }
        }
        for(Object blockObject : Block.blockRegistry.getKeys().toArray()) {
            if(blockObject instanceof String) {
                String blockString = (String) blockObject;
                if (blockString.startsWith("magicalcrops:magicalcrops_")) {
                    Block block = (Block) Block.blockRegistry.getObject(blockString);
                    if (block instanceof BlockCrops || block instanceof BlockBush) {
                        Harvestable harvestable = new Harvestable(block);
                        MFRRegistry.registerHarvestable(harvestable);
                        LogWrapper.fine("Adding %s to the %s list", blockString, "registerHarvestable");
                    }
                }
            }
        }

        /*for(String blockString : Data.crops) {
            Block block = Block.getBlockFromName(blockString);
            if(block != null) {
                Harvestable harvestable = new Harvestable(block);
                MFRRegistry.registerHarvestable(harvestable);
            }
        }
        for(String itemString : Data.seeds) {
            Item item = (Item) Item.itemRegistry.getObject(itemString);
            if(item != null) {
                Plantable plantable = new Plantable((ItemSeeds) item);
                MFRRegistry.registerPlantable(plantable);
            }
        }*/
    }

    private void mfrAdd(String message, String[] items) {
        for(String item : items) {
            FMLInterModComms.sendMessage("MineFactoryReloaded", message, item);
            LogWrapper.fine("Adding %s to the %s list", item, message);
        }
    }
}
