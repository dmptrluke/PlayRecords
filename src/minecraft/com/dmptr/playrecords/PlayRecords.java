/**
 * PlayRecords
 *
 * @author Luke Rogers
 * @author Neer Sighted
 */

package com.dmptr.playrecords;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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

    private static HashMap<String, Integer> recordIDs = new HashMap();
    private static HashMap[] recordInfo = { new HashMap() {
        {
            put("name", "callme");
            put("title", "Carly Rae Jephsen - Call Me Maybe (8bit)");
            put("iconIndex", 1);
        }
    }, new HashMap() {
        {
            put("name", "discord");
            put("title", "Bomb Squad - Discord (8bit/remix)");
            put("iconIndex", 6);
        }
    }, new HashMap() {
        {
            put("name", "fire");
            put("title", "Billy Joel - We Didn't Start The Fire");
            put("iconIndex", 16);
        }
    }, new HashMap() {
        {
            put("name", "pirate");
            put("title", "Hans Zimmer - He's a Pirate");
            put("iconIndex", 19);
        }
    }, };

    public static Item blankObsidianRecord;
    public static HashMap<String, Item> records = new HashMap();
    private static Item[] vanillaRecords = { Item.record11, Item.record13,
            Item.recordBlocks, Item.recordCat, Item.recordChirp,
            Item.recordFar, Item.recordMall, Item.recordMellohi,
            Item.recordStal, Item.recordStrad, Item.recordWait, Item.recordWard };

    // Create a creative tab.
    public static final CreativeTabs tabDiscs = new CreativeTabs("tabDiscs") {
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
        recordIDs.put("blank", config.getItem("record.blank", 22639).getInt());
        recordIDs
                .put("callme", config.getItem("record.callme", 22640).getInt());
        recordIDs.put("discord", config.getItem("record.discord", 22641)
                .getInt());
        recordIDs.put("fire", config.getItem("record.fire", 22642).getInt());
        recordIDs
                .put("pirate", config.getItem("record.pirate", 22643).getInt());

        // Save the config.
        config.save();
    }

    @Init
    public void load(FMLInitializationEvent event) { // Add items.
        blankObsidianRecord = new ItemBlankObsidianRecord(
                recordIDs.get("blank"));

        // Loop over record info and create all the records.
        for (HashMap info : recordInfo) {
            String name = info.get("name").toString();
            String title = info.get("title").toString();
            Integer id = recordIDs.get(name);
            Integer iconIndex = Integer.valueOf(info.get("iconIndex")
                    .toString());

            records.put(name, new ItemObsidianRecord(id, name, title)
                    .setIconIndex(iconIndex));
        }
        ;

        // Add vanilla records to creative tab.
        this.setVanillaRecordsTab();

        // Check if dungeon generation is enabled.
        if (recordsInDungeons) {
            // Set up chest generation.
            this.setupLoot();
        }

        // Check if record crafting is enabled.
        if (recordsCraftable) {
            // Set up record crafting.
            this.setupCrafting();
        }

        // Set up renderers.
        proxy.registerRenderers();

        // Set up localizations.
        this.setupLocalizations();

    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        // stub
    }

    /**
     * Helper to change vanilla records to use our creative tab.
     * 
     * @return nothing
     * 
     * @author Neer Sighted
     */
    public static void setVanillaRecordsTab() {
        for (Item record : vanillaRecords) {
            record.setCreativeTab(tabDiscs);
        }
    }

    /**
     * Helper to set up dungeon chest generation.
     * 
     * @return nothing
     * 
     * @author Luke Rogers
     */
    public static void setupLoot() {
        for (Item record : records.values()) {
            ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
                    new WeightedRandomChestContent(new ItemStack(record), 1, 1,
                            5));
        }
    }

    /**
     * Helper to set up record crafting.
     * 
     * @return nothing
     * 
     * @author Luke Rogers
     */
    public static void setupCrafting() {
        // Make blank discs craftable.
        GameRegistry.addRecipe(new ItemStack(blankObsidianRecord), "ooo",
                "ogo", "ooo", 'o', new ItemStack(Block.obsidian), 'g',
                new ItemStack(Block.blockGold));

        // Add record crafting.
        GameRegistry.addRecipe(new ItemStack(records.get("fire")), " f ",
                "frf", " f ", 'r', new ItemStack(blankObsidianRecord), 'f',
                new ItemStack(Item.fireballCharge));

        GameRegistry.addRecipe(new ItemStack(records.get("pirate")), " s ",
                "fof", " b ", 'r', new ItemStack(blankObsidianRecord), 's',
                new ItemStack(Item.swordSteel), 'f',
                new ItemStack(Item.fishRaw), 'b', new ItemStack(Item.boat));
    }

    /**
     * Helper to set up string localizations.
     * 
     * @return nothing
     * 
     * @author Neer Sighted
     */
    public static void setupLocalizations() {
        // Name the items.
        // They all share the same class, so just name one record.
        LanguageRegistry.addName(records.get("fire"), "Obsidian Disc");
        LanguageRegistry
                .addName(blankObsidianRecord, "Unencoded Obsidian Disc");

        // Name the creative tab.
        LanguageRegistry.instance().addStringLocalization("itemGroup.tabDiscs",
                "Music Discs");
    }
}
