/**
 * PlayRecords
 *
 * @author Luke Rogers
 * @author Neer Sighted
 */

package com.dmptr.playrecords;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import com.dmptr.playrecords.items.ItemBlankObsidianRecord;
import com.dmptr.playrecords.items.ItemObsidianRecord;

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

    private static HashMap<String, Integer> itemIDs = new HashMap();
    private static HashMap[] recordInfo = { new HashMap() {
        {
            put("name", "callme");
            put("title", "Carly Rae Jepsen - Call Me Maybe");
            put("iconIndex", 1);
        }
    }, new HashMap() {
        {
            put("name", "discord");
            put("title", "Eurobeat - Discord (remix)");
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
    }, new HashMap() {
        {
            put("name", "moveforward");
            put("title", "Kevin MacLeod - Move Forward");
            put("iconIndex", 20);
        }
    } };

    public static Item blankObsidianRecord;
    public static HashMap<String, Item> records = new HashMap();
    private static Item[] vanillaRecords = { Item.record11, Item.record13,
            Item.recordBlocks, Item.recordCat, Item.recordChirp,
            Item.recordFar, Item.recordMall, Item.recordMellohi,
            Item.recordStal, Item.recordStrad, Item.recordWait, Item.recordWard };

    // Create the discs creative tab.
    public static CreativeTabs tabDiscs = new CreativeTabs("tabDiscs") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(Item.record13);
        }
    };

    /**
     * Helper to create the various items.
     * 
     * @return nothing
     * 
     * @author Neer Sighted
     */
    private static void createItems() {
        blankObsidianRecord = new ItemBlankObsidianRecord(itemIDs.get("blank"));

        // Loop over record info and create all the records.
        for (HashMap info : recordInfo) {
            String name = info.get("name").toString();
            String title = info.get("title").toString();
            int id = itemIDs.get(name);
            int iconIndex = Integer.parseInt(info.get("iconIndex").toString());

            records.put(name, new ItemObsidianRecord(id, name, title)
                    .setIconIndex(iconIndex));
        }
    }

    /**
     * Helper to set up record crafting.
     * 
     * @return nothing
     * 
     * @author Luke Rogers
     */
    private static void setupCrafting() {
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
    private static void setupLocalizations() {
        /* Name the items.
         * They all share the same class, so just name one record.
         */
        LanguageRegistry.addName(records.get("callme"), "Obsidian Disc");
        LanguageRegistry.addName(blankObsidianRecord, "Blank Obsidian Disc");

        // Name the creative tab.
        LanguageRegistry.instance().addStringLocalization("itemGroup.tabDiscs",
                "Music Discs");
    }

    /**
     * Helper to set up dungeon chest generation.
     * 
     * @return nothing
     * 
     * @author Luke Rogers
     */
    private static void setupLoot() {
        for (Item record : records.values()) {
            ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
                    new WeightedRandomChestContent(new ItemStack(record), 1, 1,
                            5));
        }
    }

    /**
     * Helper to change vanilla records to use our creative tab.
     * 
     * @return nothing
     * 
     * @author Neer Sighted
     */
    private static void setVanillaRecordsTab() {
        for (Item record : vanillaRecords) {
            record.setCreativeTab(tabDiscs);
        }
    }

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
        itemIDs.put("blank", config.getItem("record.blank", 22639).getInt());
        itemIDs.put("callme", config.getItem("record.callme", 22640).getInt());
        itemIDs.put("discord", config.getItem("record.discord", 22641).getInt());
        itemIDs.put("fire", config.getItem("record.fire", 22642).getInt());
        itemIDs.put("pirate", config.getItem("record.pirate", 22643).getInt());
        itemIDs.put("moveforward", config.getItem("record.moveforward", 22644)
                .getInt());

        // Save the config.
        config.save();
    }

    @Init
    public void load(FMLInitializationEvent event) {
        // Create the items.
        createItems();

        // Add vanilla records to creative tab.
        setVanillaRecordsTab();

        // Set up dungeon loot (if enabled).
        if (recordsInDungeons)
            setupLoot();

        // Set up record crafting (if enabled).
        if (recordsCraftable)
            setupCrafting();

        // Set up renderers.
        proxy.registerRenderers();

        // Set up localizations.
        setupLocalizations();

    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        // stub
    }
}