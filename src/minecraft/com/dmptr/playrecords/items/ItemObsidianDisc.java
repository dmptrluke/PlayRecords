package com.dmptr.playrecords.items;

import java.util.List;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.dmptr.playrecords.PlayRecords;
import com.dmptr.playrecords.CommonProxy;

public class ItemObsidianDisc extends Item {
    public ItemObsidianDisc(int id) {
        super(id);

        // Set the item name.
        setItemName("blankObsidianDisc");
        // Set the icon.
        setIconCoord(6, 1);
        // Configure creative tab.
        setCreativeTab(PlayRecords.tabDisc);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }

    public String getTextureFile() {
        return CommonProxy.ITEMS_PNG;
    }
}
