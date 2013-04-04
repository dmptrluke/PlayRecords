package com.dmptr.playrecords.items;

import java.util.List;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.dmptr.playrecords.PlayRecords;
import com.dmptr.playrecords.CommonProxy;

public class ItemBlankObsidianRecord extends Item {
    public ItemBlankObsidianRecord(int id) {
        super(id);

        // Set the item name.
        this.setItemName("blankObsidianDisc");
        // Set the icon.
        this.setIconCoord(6, 1);
        // Configure creative tab.
        this.setCreativeTab(PlayRecords.tabDiscs);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }

    public String getTextureFile() {
        return CommonProxy.ITEMS_PNG;
    }
}
