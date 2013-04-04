package com.dmptr.playrecords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dmptr.playrecords.items.ObsidianDisc;
import com.dmptr.playrecords.items.ObsidianRecord;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid="PlayRecords", name="PlayRecords", version="0.0.2")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class PlayRecords {

        // the instance of the mod that Forge uses.
        @Instance("PlayRecords")
        public static PlayRecords instance;

        // says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="com.dmptr.playrecords.client.ClientProxy", serverSide="com.dmptr.playrecords.CommonProxy")
        public static CommonProxy proxy;
        
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
            // if the file doesn't already exist, it will be created.
            Configuration config = new Configuration(event.getSuggestedConfigurationFile());
            config.load();
            
            // get options
            Property recordsInDungeonsConfig = config.get(Configuration.CATEGORY_GENERAL, "recordsInDungeons", true);
            recordsInDungeonsConfig.comment = "generate records in dungeons";
            recordsInDungeons = recordsInDungeonsConfig.getBoolean(false);
            
            Property recordsCraftableConfig = config.get(Configuration.CATEGORY_GENERAL, "recordsCraftable", true);
            recordsCraftableConfig.comment = "enable crafting recipes for records";
            recordsCraftable = recordsCraftableConfig.getBoolean(false);
           
            // get item IDs
            obsidianDiscID = config.getItem("obsidianDiscID", 22639).getInt();
            fireRecordID = config.getItem("fireRecordID", 22640).getInt();
            discordRecordID = config.getItem("discordRecordID", 22641).getInt();
            callmeRecordID = config.getItem("callmeRecordID", 22642).getInt();
            pirateRecordID = config.getItem("pirateRecordID", 22643).getInt();

            // save config
            config.save();
        }
        
        @Init
        public void load(FMLInitializationEvent event) {
        	// add items
        	obsidianDisc = new ObsidianDisc(obsidianDiscID);
        	
        	fireRecord = new ObsidianRecord(fireRecordID, "fire", "FelixMoog - We Didn't Start The Fire").setIconCoord(0, 1);
            discordRecord = new ObsidianRecord(discordRecordID, "discord", "FelixMoog - Discord (Remix)").setIconCoord(2, 1);
            callmeRecord = new ObsidianRecord(callmeRecordID, "callme", "FelixMoog - Call Me Maybe").setIconCoord(5, 1);
            pirateRecord = new ObsidianRecord(pirateRecordID, "pirate", "FelixMoog - He's A Pirate").setIconCoord(3, 1);
            
            // add record crafting if enabled
            if (recordsCraftable) {
            	// add basic crafting
            	ItemStack obsidianStack = new ItemStack(Block.obsidian);
            	ItemStack blockGoldStack = new ItemStack(Block.blockGold);
            
            	GameRegistry.addRecipe(new ItemStack(obsidianDisc), "xxx", "xyx", "xxx",
            			'x', obsidianStack, 'y', blockGoldStack);
            }

        	// add records to dungeon chests if enabled
        	if (recordsInDungeons) {
        		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(callmeRecord), 1, 1, 5));
        		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(pirateRecord), 1, 1, 5));
        		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(fireRecord), 1, 1, 5));
        		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(discordRecord), 1, 1, 5));
        	}

        	// add item names to Language Registry
        	LanguageRegistry.addName(fireRecord, "Obsidian Disc");
        	LanguageRegistry.addName(obsidianDisc, "Unencoded Obsidian Disc");
        	
        	proxy.registerRenderers();
        }
        
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }     
        
        // configuration options
        public static boolean recordsInDungeons, recordsCraftable;
        public static int obsidianDiscID, fireRecordID, discordRecordID, pirateRecordID, callmeRecordID;
        
		// items
		public static Item obsidianDisc, fireRecord, discordRecord, callmeRecord, pirateRecord;
}