/**
 * PlayRecords
 * 
 * @author Luke Rogers
 * @author Neer Sighted
 */

package com.dmptr.playrecords;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemRecord;
import net.minecraft.block.Block;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.ChestGenHooks;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import com.dmptr.playrecords.items.*;

@Mod(modid = "PlayRecords", name = "PlayRecords", version = "0.0.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class PlayRecords {
    // Instance the mod.
    @Instance("PlayRecords")
    public static PlayRecords instance;

    // Specify proxy location (rendering code).
    @SidedProxy(clientSide = "com.dmptr.playrecords.client.ClientProxy", serverSide = "com.dmptr.playrecords.CommonProxy")
    public static CommonProxy proxy;

    // Declare types.
    public static boolean recordsInDungeons, recordsCraftable;
    public static int obsidianDiscID, fireRecordID, discordRecordID,
            pirateRecordID, callmeRecordID;

    public static Item blankObsidianRecord;
    public static HashMap<String, Item> records = new HashMap();

    // Create a creative tab.
    public static CreativeTabs tabDiscs = new CreativeTabs("tabDiscs") {
        public ItemStack getIconItemStack() {
            return new ItemStack(blankObsidianRecord);
        };
    };
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        // Load the configuration file.
        Configuration config = new Configuration(
                event.getSuggestedConfigurationFile());
        config.load();

        // Load options.
        Property recordsInDungeonsConfig = config.get(
                Configuration.CATEGORY_GENERAL, "recordsInDungeons", true);
        recordsInDungeonsConfig.comment = "generate records in dungeons";
        recordsInDungeons = recordsInDungeonsConfig.getBoolean(false);

        Property recordsCraftableConfig = config.get(
                Configuration.CATEGORY_GENERAL, "recordsCraftable", true);
        recordsCraftableConfig.comment = "enable crafting recipes for records";
        recordsCraftable = recordsCraftableConfig.getBoolean(false);

        // Load item IDs.
        obsidianDiscID = config.getItem("obsidianDiscID", 22639).getInt();
        callmeRecordID = config.getItem("callmeRecordID", 22640).getInt();
        discordRecordID = config.getItem("discordRecordID", 22641).getInt();
        fireRecordID = config.getItem("fireRecordID", 22642).getInt();
        pirateRecordID = config.getItem("pirateRecordID", 22643).getInt();

        // Save the config.
        config.save();
    }

    @Init
    public void load(FMLInitializationEvent event) { // Add items.
        blankObsidianRecord = new ItemBlankObsidianRecord(obsidianDiscID);

        // Add items.
        records.put("callme", new ItemObsidianRecord(callmeRecordID, "callme",
                "FelixMoog - Call Me Maybe").setIconIndex(19));
        records.put("discord", new ItemObsidianRecord(discordRecordID,
                "discord", "FelixMoog - Discord (Remix)").setIconIndex(6));
        records.put("fire", new ItemObsidianRecord(fireRecordID, "fire",
                "FelixMoog - We Didn't Start The Fire").setIconIndex(1));
        records.put("pirate", new ItemObsidianRecord(pirateRecordID, "pirate",
                "FelixMoog - He's A Pirate").setIconIndex(16));

        // Add vanilla records to creative tab.
        Item.record11.setCreativeTab(tabDiscs);
        Item.record13.setCreativeTab(tabDiscs);
        Item.recordBlocks.setCreativeTab(tabDiscs);
        Item.recordCat.setCreativeTab(tabDiscs);
        Item.recordChirp.setCreativeTab(tabDiscs);
        Item.recordFar.setCreativeTab(tabDiscs);
        Item.recordMall.setCreativeTab(tabDiscs);
        Item.recordMellohi.setCreativeTab(tabDiscs);
        Item.recordStal.setCreativeTab(tabDiscs);
        Item.recordStrad.setCreativeTab(tabDiscs);
        Item.recordWait.setCreativeTab(tabDiscs);
        Item.recordWard.setCreativeTab(tabDiscs);

        // Check if record crafting is enabled.
        if (recordsCraftable) {
            // Make blank discs craftable.
            ItemStack obsidianStack = new ItemStack(Block.obsidian);
            ItemStack blockGoldStack = new ItemStack(Block.blockGold);

            GameRegistry.addRecipe(new ItemStack(blankObsidianRecord), "xxx",
                    "xyx", "xxx", 'x', obsidianStack, 'y', blockGoldStack);

            // Add record crafting.
            ItemStack blankObsidianRecordStack = new ItemStack(
                    blankObsidianRecord);
            ItemStack fireballChargeStack = new ItemStack(Item.fireballCharge);
            ItemStack swordSteelStack = new ItemStack(Item.swordSteel);
            ItemStack rawFishStack = new ItemStack(Item.fishRaw);
            ItemStack boatStack = new ItemStack(Item.boat);

            GameRegistry.addRecipe(new ItemStack(records.get("fire")), " x ",
                    "xox", " x ", 'o', blankObsidianRecordStack, 'x',
                    fireballChargeStack);
            GameRegistry.addRecipe(new ItemStack(records.get("pirate")), " x ",
                    "fof", " b ", 'o', blankObsidianRecordStack, 'x',
                    swordSteelStack, 'f', rawFishStack, 'b', boatStack);
        }

        // Check if dungeon generation is enabled.
        if (recordsInDungeons) {
            for (Item value : records.values()) {
                ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
                        new WeightedRandomChestContent(new ItemStack(value), 1,
                                1, 5));
            }
        }

        // Add item names to Language Registry.
        LanguageRegistry.addName(records.get("fire"), "Obsidian Disc");
        LanguageRegistry
                .addName(blankObsidianRecord, "Unencoded Obsidian Disc");

        // Name the creative tab.
        LanguageRegistry.instance().addStringLocalization("itemGroup.tabDiscs",
                "Music Discs");

        // Set up renderers.
        proxy.registerRenderers();
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        // stub
    }
}
