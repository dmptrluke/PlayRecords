package com.dmptr.playrecords.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

import com.dmptr.playrecords.CommonProxy;
import com.dmptr.playrecords.PlayRecords;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemObsidianRecord extends ItemRecord {
    public String title;

    public ItemObsidianRecord(int id, String music, String title) {
        super(id, music);

        // Set the item name.
        setItemName("obsidianDisc");
        // Set the (default) item sprite.
        setIconIndex(255);
        // Configure creative tab.
        setCreativeTab(PlayRecords.tabDiscs);
        
        // Set up the title.
        this.title = title;
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getRecordTitle() {
        return this.title;
    }

    @Override
    public String getTextureFile() {
        return CommonProxy.ITEMS_PNG;
    }
}
