package shadow.mods.metallurgy.mystcraft;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenLakes;
import java.util.Random;

import xcompwiz.mystcraft.api.symbol.AgeSymbol;
import xcompwiz.mystcraft.api.symbol.AgeSymbol.Category;
import xcompwiz.mystcraft.api.symbol.IAgeController;
import xcompwiz.mystcraft.api.symbol.IPopulate;

import shadow.mods.metallurgy.MetalSet;
import shadow.mods.metallurgy.base.MetallurgyBaseMetals;
import shadow.mods.metallurgy.utility.UtilityWorldGen;

public class OreSymbolUtility  extends AgeSymbol 
{	
	@Override
	public void registerLogic(IAgeController controller) {
		controller.registerInterface(this, new Populator());
	}

	@Override
	public String identifier() {
		return "Utility Ores";
	}
	
	@Override
	public Category getCategory() {
		return AgeSymbol.Category.WorldModifier;
	}

	public int instabilityModifier(int count) {
		if(count > 2)
			return (count - 2)*100;
		return 0;
	}

	private class Populator implements IPopulate {
		public Populator(){}
		
		public boolean populate(World worldObj, Random rand, int i, int j, boolean flag) {
			if(flag)
				return false;
			UtilityWorldGen.generateAllOres(rand, i/16, j/16, worldObj);
			
			return false;
		}
	}
}
