package shadow.mods.metallurgy.utility;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;


public class ClientProxy extends shadow.mods.metallurgy.utility.CommonProxy{
	
	@Override
	public void addNames()
	{
		ModLoader.addLocalization("tile.UtilityVein.PhosphoriteOre.name", "Phosporite Ore");
		ModLoader.addLocalization("tile.UtilityVein.SulfurOre.name", "Sulfur Ore");
		ModLoader.addLocalization("tile.UtilityVein.SaltpeterOre.name", "Saltpeter Ore");
		ModLoader.addLocalization("tile.UtilityVein.MagnesiumOre.name", "Magnesium Ore");
		ModLoader.addLocalization("tile.UtilityVein.BitumenOre.name", "Bitumen Ore");
		ModLoader.addLocalization("tile.UtilityVein.PotashOre.name", "Potash Ore");

		ModLoader.addName(MetallurgyUtility.bitumen, "Bitumen");
		ModLoader.addName(MetallurgyUtility.tar, "Tar");
		ModLoader.addName(MetallurgyUtility.saltpeter, "Saltpeter");
		ModLoader.addName(MetallurgyUtility.potash, "Potash");
		ModLoader.addName(MetallurgyUtility.fertilizer, "Fertilizer");
		ModLoader.addName(MetallurgyUtility.magnesium, "Magnesium");
		ModLoader.addName(MetallurgyUtility.phosphorite, "Phosphorite");
		ModLoader.addName(MetallurgyUtility.phosphorus, "Phosphorous");
		ModLoader.addName(MetallurgyUtility.sulfur, "Sulfur");
	}
	
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture("/shadow/MetallurgyUtilityOres.png");
		//MinecraftForgeClient.preloadTexture("");
	}
	
	@Override
	public void registerTileEntitySpecialRenderer() {

	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public File getMinecraftDir()
	{
		return Minecraft.getMinecraftDir();
	}
}
