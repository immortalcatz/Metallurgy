package shadow.mods.metallurgy.precious;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import shadow.mods.metallurgy.base.ConfigBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.src.*;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ISidedInventory;

public class FM_TileEntityMint extends TileEntity
{
    public int direction = 0;
    private int ingotId = 0;
    private int amount = 0;
    
    public int resetTime = 0;
    
    public boolean powered = false;

	private int ticksSinceSync;
    
    public void setDirection(int par1)
    {
    	direction = par1;
    }
    
    public int getDirection()
    {
    	return direction;
    }
    
    public void power()
    {
    	if(resetTime == 0)
    	{
    		resetTime = 10;
    	}
    	powered = true;
    }
    
    public void unpower()
    {
    	powered = false;
    }
    
    public void setIngot(ItemStack ingot)
    {
    	if(ingotId == 0 && resetTime == 0)
    	{
	    	amount = FM_MintRecipes.minting().getMintingResult(ingot);
	    	if(amount != 0)
	    	{
	    		ingotId = ingot.itemID;
				int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, ingotId);
	    		ingot.stackSize--;
	    	}
    	}
    }
    
    public ItemStack currentIngot()
    {
    	if(ingotId != 0)
    		return new ItemStack(ingotId, 1, 0);
    	else
    		return null;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);


        this.direction = par1NBTTagCompound.getShort("Direction");
        ticksSinceSync = 40;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Direction", (short)this.direction);
    }
    
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
    public void updateEntity()
    {
    	if(resetTime > 0)
    	{
    		resetTime--;

			int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, resetTime);
			
    		if(resetTime == 6)
    		{
	    		mint();
    		}	
    	}
    }

    @Override
	public void receiveClientEvent(int i, int j) 
    {
		if (i == 1) {
			direction = j;
		} else if(i == 2)
		{
			resetTime = j;
		} else if(i == 3)
		{
			ingotId = j;
		}
        worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
	}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }
    
	public void sendPacket()
	{
		System.out.println("sending packet");
		if(worldObj.isRemote)
			return;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(2);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(direction);
		} catch (IOException e) {
			// UNPOSSIBLE?
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "MetallurgyFantas";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;
		
		if (packet != null) {
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.worldType, packet);
		}
	}

	public void mint() {
        ItemStack var7 = new ItemStack(mod_MetallurgyPrecious.Coin, 1, 0);
        Random rand = new Random();

        if (!worldObj.isRemote && currentIngot() != null)
        {
            float var8 = rand.nextFloat() * 0.8F + 0.1F;
            float var9 = rand.nextFloat() * 0.8F + 0.1F;
            float var10 = rand.nextFloat() * 0.8F + 0.1F;

            EntityItem var12 = new EntityItem(worldObj, (double)((float)xCoord + var8), (double)((float)yCoord + var9), (double)((float)zCoord + var10), new ItemStack(var7.itemID, 1, var7.getItemDamage()));

            if (var7.hasTagCompound())
            {
                var12.item.setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
            }

            float var13 = 0.05F;
            var12.motionX = (double)((float)rand.nextGaussian() * var13);
            var12.motionY = (double)((float)rand.nextGaussian() * var13 + 0.2F);
            var12.motionZ = (double)((float)rand.nextGaussian() * var13);
            var12.delayBeforeCanPickup = 20;
            worldObj.spawnEntityInWorld(var12);
        }
		
        if(--amount == 0)
        {
        	ingotId = 0;
			int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, ingotId);
        }
	}

	public String getIngotImage() {
		if(ingotId == mod_MetallurgyPrecious.ores.Bar[1].shiftedIndex)
			return "MintSilver.png";
		else if(ingotId == mod_MetallurgyPrecious.alloys.Bar[0].shiftedIndex)
			return "MintBrass.png";
		else if(ingotId == mod_MetallurgyPrecious.alloys.Bar[1].shiftedIndex)
			return "MintElectrum.png";
		else if(ingotId == mod_MetallurgyPrecious.ores.Bar[2].shiftedIndex)
			return "MintPlatinum.png";
		else if(ingotId == Item.ingotGold.shiftedIndex)
			return "MintGold.png";
		return "";
	}
}