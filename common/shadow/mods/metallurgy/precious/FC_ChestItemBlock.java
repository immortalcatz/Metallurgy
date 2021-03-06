package shadow.mods.metallurgy.precious;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class FC_ChestItemBlock extends ItemBlock 
{
        public FC_ChestItemBlock(int i) {
                super(i);
                setHasSubtypes(true);
        }
        
        @Override
        public int getMetadata(int i) {
                return i;
        }

        @Override
        public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
        {
        	switch(par1ItemStack.getItemDamage()) {
		        case 0: {
		            par3List.add("6x9");
		            break;
		        }
		        case 1: {
		            par3List.add("8x9");
		            break;
		        }
		        case 2: {
		            par3List.add("9x9");
		            break;
		        }
		        case 3: {
		            par3List.add("9x10");
		            break;
		        }
		        case 4: {
		            par3List.add("9x12");
		            break;
		        }
		    }
        }
        
        @Override
        public String getItemNameIS(ItemStack itemstack)
        {
            String name = "";
            switch(itemstack.getItemDamage()) {
	            case 0: {
	                name = "BrassChest";
	                break;
	            }
	            case 1: {
	                name = "SilverChest"; 
	                break;
	            }
	            case 2: {
	                name = "GoldChest";
	                break;
	            }
	            case 3: {
	                name = "ElectrumChest";
	                break;
	            }
	            case 4: {
	                name = "PlatinumChest";
	                break;
	            }
	            default: name = "chest";
            }
            return getItemName() + "." + name;
        }
}
