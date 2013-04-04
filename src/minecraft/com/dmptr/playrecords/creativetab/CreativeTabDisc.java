package com.dmptr.playrecords.creativetab;

import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.dmptr.playrecords.PlayRecords;

public class CreativeTabDisc extends CreativeTabs {
    public CreativeTabDisc(String label) {
	  super(label);
	}
    
    public int getTabIconItemIndex() {
    	return PlayRecords.obsidianDisc.itemID;
    }
}
