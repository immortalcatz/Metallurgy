package shadow.mods.metallurgy.base;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraftforge.common.EnumHelper;
import shadow.mods.metallurgy.IMetalSetEnum;
import shadow.mods.metallurgy.MetallurgyEnumToolMaterial;

public class OreBaseEnum extends IMetalSetEnum{

	public static final int numMetals = 3;
	public static final String[] names = {"Copper", "Tin", "Manganese"};
	private final int[] expValues = {1, 1, 3};
	private final int[] harvestLevels = {1, 1, 2};
	private final int[] pickLevels = {1, 0, 0};
	private int[] metalLevels = {1, 2, 4};
	private final int[] dungeonLootChances = {120, 100, 45};
	private final int[] dungeonLootAmounts = {6, 4, 2};

	public static int[] defaultVeinCount = {12, 10, 4};
	public static int[] defaultOreCount = {6, 7, 4};
	public static int[] defaultOreHeight = {128, 128, 128};
	
	public static int[] numRails = {4, 0, 0};
	
	public static EnumArmorMaterial copperArmor = EnumHelper.addArmorMaterial("Copper", 10, new int[] {2, 3, 2, 1}, 5);
	
	@Override
	public int numMetals() {
		return numMetals;
	}

	@Override
	public int startID(int i) {
		return ConfigBase.ItemStartID + (i * 50);
	}

	@Override
	public String name(int i) {
		return names[i];
	}

	@Override
	public int expValue(int i) {
		return expValues[i];
	}

	@Override
	public int oreHarvestLevel(int i) {
		return harvestLevels[i];
	}

	@Override
	public int brickHarvestLevel(int i) {
		return harvestLevels[i];
	}

	@Override
	public int pickLevel(int i) {
		return pickLevels[i];
	}

	@Override
	public String image() {
		return "/shadow/MetallurgyBaseMetals.png";
	}

	@Override
	public boolean isAlloy() {
		return false;
	}

	@Override
	public int veinCount(int i) {
		return ConfigBase.VeinCount[i];
	}

	@Override
	public int oreCount(int i) {
		return ConfigBase.OreCount[i];
	}

	@Override
	public int oreHeight(int i) {
		return ConfigBase.OreHeight[i];
	}

	@Override
	public int oreID() {
		return ConfigBase.baseMetalsVeinID;
	}

	@Override
	public int brickID() {
		return ConfigBase.baseMetalsBrickID;
	}

	@Override
	public String getSetName() {
		return "BaseMetal";
	}

	@Override
	public MetallurgyEnumToolMaterial toolEnum(int i) {
		switch(i)
		{
			case(0):
				return MetallurgyEnumToolMaterial.Copper;
		}
		return MetallurgyEnumToolMaterial.Copper;
	}

	@Override
	public boolean isCatalyst(int i) {
		if(i == 1 || i == 2)
			return true;
		return false;
	}

	@Override
	public int dungeonLootChance(int i) {
		return dungeonLootChances[i];
	}

	@Override
	public int dungeonLootAmount(int i) {
		return dungeonLootAmounts[i];
	}

	@Override
	public int numRails(int i) {
		return numRails[i];
	}

	@Override
	public boolean spawnsInDimension(int i)
	{
		for(String str : ConfigBase.dimensions.split(" ")) {
			if(str.matches("[0-9]+-[0-9]+"))
			{
				int start = Integer.parseInt(str.split("-")[0]);
				int end = Integer.parseInt(str.split("-")[1]);
				if(i >= start && i <= end)
					return true;
			} else {
				if(i == Integer.parseInt(str))
					return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean metalEnabled(int i) {
		return ConfigBase.metalEnabled[i];
	}

	@Override
	public EnumArmorMaterial armorEnum(int i) 
	{
		return copperArmor;
	}

	@Override
	public int oreMinHeight(int i) {
		return ConfigBase.OreMinHeight[i];
	}

	@Override
	public int level(int i) {
		return metalLevels[i];
	}
	
	public boolean hasMetalBlock() 
	{
		return true;
	}
	public int blockID() 
	{
		return ConfigBase.baseMetalsBlockID;
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return MetallurgyBaseMetals.baseTab;
	}

}
