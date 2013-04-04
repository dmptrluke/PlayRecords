package com.dmptr.playrecords.items;

import java.util.List;

import com.dmptr.playrecords.CommonProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ObsidianDisc extends Item {
        public ObsidianDisc(int id) {
                super(id);

                // Constructor configuration
                setCreativeTab(CreativeTabs.tabMisc);
                setItemName("blankObsidianDisc");
                setIconCoord(6, 1);
        }

        @Override
        public EnumRarity getRarity(ItemStack par1ItemStack){
                return EnumRarity.rare;
        }

        public String getTextureFile() {
                return CommonProxy.ITEMS_PNG;
        }
}
