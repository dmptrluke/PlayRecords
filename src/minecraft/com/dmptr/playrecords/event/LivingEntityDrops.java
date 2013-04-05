package com.dmptr.playrecords.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import com.dmptr.playrecords.PlayRecords;

public class LivingEntityDrops {
    public static Random rand = new Random();

    @ForgeSubscribe
    public void onLivingEntityDrop(LivingDropsEvent event) {
        List<Item> possibleDrops = new ArrayList<Item>(PlayRecords.records.values());
        Item drop = possibleDrops.get(rand.nextInt(possibleDrops.size()));

        if (event.entityLiving instanceof EntitySkeleton
                && event.source.getEntity() instanceof EntityEnderman) {
            event.entityLiving.dropItem(drop.itemID, 1);
        }
    };
}