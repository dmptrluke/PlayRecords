package com.dmptr.playrecords.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.dmptr.playrecords.CommonProxy;

public class ItemObsidianDisc extends Item {
    public ItemObsidianDisc(int id) {
        super(id);

        // Set the item name.
        setItemName("blankObsidianDisc");
        // Set the icon.
        setIconCoord(6, 1);
        // Set the creative tab.
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }

    public String getTextureFile() {
        return CommonProxy.ITEMS_PNG;
    }
}
