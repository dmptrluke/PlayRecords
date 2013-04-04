package com.dmptr.playrecords.items;

import com.dmptr.playrecords.CommonProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

public class ObsidianRecord extends ItemRecord {
	
		public final String title;

        public ObsidianRecord(int id, String music, String title) {
                super(id, music);
        
                this.title = title;
                
                // Constructor Configuration
                setCreativeTab(CreativeTabs.tabMisc);
                setItemName("obsidianDisc");
        }
        
        @Override
        public EnumRarity getRarity(ItemStack par1ItemStack){
        	return EnumRarity.epic;
        }
        
        @Override
        @SideOnly(Side.CLIENT)
        public String getRecordTitle()
        {
            return this.title;
        }

        public String getTextureFile() {
            return CommonProxy.ITEMS_PNG;
        }

}