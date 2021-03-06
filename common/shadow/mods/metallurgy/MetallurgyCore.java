package shadow.mods.metallurgy;
import java.io.File;
import java.io.FilenameFilter;

import org.lwjgl.opengl.GL11;

import shadow.mods.metallurgy.*;
import shadow.mods.metallurgy.base.FurnaceUpgradeRecipes;
import shadow.mods.metallurgy.base.MetallurgyBaseMetals;
//import vazkii.um.common.ModConverter;
//import vazkii.um.common.UpdateManagerMod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.FMLRelaunchLog;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "MetallurgyCore", name = "Metallurgy Core", version = "2.3.4")
@NetworkMod(channels = { "MetallurgyCore" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class )
public class MetallurgyCore implements ITickHandler
{
	@SidedProxy(clientSide = "shadow.mods.metallurgy.CoreClientProxy", serverSide = "shadow.mods.metallurgy.CoreCommonProxy")
	public static CoreCommonProxy proxy;

	@Instance( value = "MetallurgyCore" )
	public static MetallurgyCore instance;
	
	public static CoreConfig config;
	
	public static boolean hasBase;
	public static boolean hasNether;
	public static boolean hasPrecious;
	public static boolean hasFantasy;
	public static boolean hasEnder;

	public static Block crusher;
	public static Block vanillaBricks;
	
	public static Block smoke;
	public static Block smokeInactive;
	public static Block smokeEater;
	
	public static List<String> updateNeeded = new ArrayList<String>();
	public static boolean sentUpdateMessage = false;
	
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{        
		config.init();
		crusher = new BC_BlockCrusher(CoreConfig.crusherID, false).setHardness(3.5F).setBlockName("Crusher").setCreativeTab(CreativeTabs.tabDecorations);
		vanillaBricks = new MetallurgyBlock(CoreConfig.vanillaBrickID, "/shadow/Overrides.png", 2, 5).setHardness(2F).setResistance(.1F).setBlockName("VanillaBrick");
		
		proxy.registerRenderInformation();

		try {
			Class a = Class.forName("shadow.mods.metallurgy.base.MetallurgyBaseMetals");
	    	hasBase = true;
    		System.out.println("Metallurgy Core: Base Metals detected, comapatibility added");
    	} catch(ClassNotFoundException e) {
    		System.out.println("Metallurgy Core: Base Metals not detected, reason: " + e);
    	}
		try {
			Class a = Class.forName("shadow.mods.metallurgy.nether.MetallurgyNether");
	    	hasNether = true;
    		System.out.println("Metallurgy Core: Nether detected, comapatibility added");
    	} catch(ClassNotFoundException e) {
    		System.out.println("Metallurgy Core: Nether not detected, reason: " + e);
    	}
		try {
			Class a = Class.forName("shadow.mods.metallurgy.precious.MetallurgyPrecious");
	    	hasPrecious = true;
    		System.out.println("Metallurgy Core: Precious detected, comapatibility added");
    	} catch(ClassNotFoundException e) {
    		System.out.println("Metallurgy Core: Precious not detected, reason: " + e);
    	}
		try {
			Class a = Class.forName("shadow.mods.metallurgy.fantasy.MetallurgyFantasy");
	    	hasFantasy = true;
    		System.out.println("Metallurgy Core: Fantasy detected, comapatibility added");
    	} catch(ClassNotFoundException e) {
    		System.out.println("Metallurgy Core: Fantasy not detected, reason: " + e);
    	}
		try {
			Class a = Class.forName("shadow.mods.metallurgy.ender.MetallurgyEnder");
	    	hasEnder = true;
    		System.out.println("Metallurgy Core: Ender detected, comapatibility added");
    	} catch(ClassNotFoundException e) {
    		System.out.println("Metallurgy Core: Ender not detected, reason: " + e);
    	}
	}
	
	@Init
	public void load(FMLInitializationEvent event) 
	{
		mod_Iron.load();
		mod_Gold.load();
		
		GameRegistry.registerBlock(vanillaBricks, MetallurgyItemBlock.class);
		
		GameRegistry.registerWorldGenerator(new CoreWorldGen());
		
		OreDictionary.registerOre("dustGold", new ItemStack(mod_Gold.GoldDust, 1));
		OreDictionary.registerOre("dustIron", new ItemStack(mod_Iron.IronDust, 1));
		
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);

		proxy.addNames();
		proxy.registerRenderInformation();
		proxy.registerTileEntitySpecialRenderer();
		if(CoreConfig.enableTextureOverrides)
			proxy.addTextureOverrides();
		ModLoader.registerBlock(crusher, shadow.mods.metallurgy.BC_BlockCrusherItem.class);
		ModLoader.registerTileEntity(BC_TileEntityCrusher.class, "crusher");
		
		//mod_MetallurgyzAchievements.load();
		
	    RecipeHelper.addBrickRecipes(vanillaBricks.blockID, 0, Item.ingotIron);
	    RecipeHelper.addBrickRecipes(vanillaBricks.blockID, 1, Item.ingotGold);
	    
		if(CoreConfig.crushersEnabled)
			CrusherUpgradeRecipes.load();
		
	    BC_CrusherRecipes.smelting().addCrushing(Block.cobblestone.blockID, new ItemStack(Block.sand));
	    BC_CrusherRecipes.smelting().addCrushing(Block.gravel.blockID, new ItemStack(Item.flint));

		try {
			Class a = Class.forName("ic2.api.Ic2Recipes");
			ItemStack circuit = ic2.api.Items.getItem("electronicCircuit");
			ItemStack macerator = ic2.api.Items.getItem("macerator");
			GameRegistry.addRecipe(macerator, new Object[] {
	    			" I ", "IFI", "ICI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('C'), circuit, Character.valueOf('F'), new ItemStack(crusher, 1, 1)
	    		});
			GameRegistry.addRecipe(macerator, new Object[] {
	    			" I ", "IFI", " C ", Character.valueOf('I'), Item.ingotIron, Character.valueOf('C'), circuit, Character.valueOf('F'), new ItemStack(crusher, 1, 2)
	    		});
			GameRegistry.addRecipe(macerator, new Object[] {
    			"F", "C", Character.valueOf('C'), circuit, Character.valueOf('F'), new ItemStack(crusher, 1, 3)
    		});
			GameRegistry.addRecipe(macerator, new Object[] {
	    		"F", "C", Character.valueOf('C'), circuit, Character.valueOf('F'), new ItemStack(crusher, 1, 4)
    		});

			ItemStack coalDust = ic2.api.Items.getItem("coalDust");
			if(coalDust != null)
				BC_CrusherRecipes.smelting().addCrushing(Item.coal.shiftedIndex, 0, coalDust);
		} catch(Exception e) {}
		
		try {
			Class a = Class.forName("railcraft.common.api.crafting.RailcraftCraftingManager");
			
			HashMap<ItemStack, Float> rockCrusherRecipe = new HashMap<ItemStack, Float>();
			rockCrusherRecipe.put(new ItemStack(mod_Iron.IronDust, 2), 1.0F);
			railcraft.common.api.crafting.RailcraftCraftingManager.rockCrusher.addRecipe(new ItemStack(Block.oreIron, 1), rockCrusherRecipe);
			rockCrusherRecipe = new HashMap<ItemStack, Float>();
			rockCrusherRecipe.put(new ItemStack(mod_Gold.GoldDust, 2), 1.0F);
			railcraft.common.api.crafting.RailcraftCraftingManager.rockCrusher.addRecipe(new ItemStack(Block.oreGold, 1), rockCrusherRecipe);
		} catch(Exception e) {}
		
		new UpdateManager("2.3.4", "Core", "http://ladadeda.info/CoreVersion.txt");
		TickRegistry.registerTickHandler(this, Side.CLIENT);
	}
	
	public static CreativeTabs getNewCreativeTab(String name, int iconID)
	{
		int tabID = CreativeTabs.getNextID();
		//CreativeTabs[] temp = new CreativeTabs[tabID + 1];
		//System.arraycopy(CreativeTabs.creativeTabArray, 0, temp, 0, tabID);
		CreativeTabs metallurgyTab = new CreativeTabMetallurgy(tabID, name, iconID);
		//temp[tabID] = metallurgyTab;
		//CreativeTabs.creativeTabArray = temp;
		
		proxy.addCreativeTabName(name);
		
		return metallurgyTab;
	}

	public static void needsUpdate(String name, String latest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.PLAYER)) && !sentUpdateMessage && updateNeeded.size() > 0)
		{
			String updateMessage = "";
			for(String name : updateNeeded)
				updateMessage += name + ", ";
			updateMessage = updateMessage.substring(0, updateMessage.length() - 2);
			System.out.println(updateMessage);
			EntityPlayer player = (EntityPlayer)tickData[0];
			player.sendChatToPlayer("Metallurgy: Updates avaliable for " + updateMessage);
			sentUpdateMessage = true;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void blockError(IllegalArgumentException e) 
	{
		final String s = e.getMessage();
		Thread t = new Thread(new Runnable(){
	        public void run(){
	            JOptionPane.showMessageDialog(null, "Metallurgy Error: There was a block ID conflict, you can fix this by changing the IDs in your config files!\n\n" + s);
	        }
	    });
	    t.start();
	}
}