package de.rexituz.bingo.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemBuilder {
    public ItemStack getItem(String name, Material material, int i, Boolean hasLore, ArrayList<String> lore, Boolean enchanted, boolean hasData, int data){
        ItemStack item;
        if(hasData){
            item = new ItemStack(material, i,(short) data);
        } else {
            item = new ItemStack(material, i);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if(hasLore)
            meta.setLore(lore);
        if(enchanted)
            meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        item.setItemMeta(meta);
        return item;
    }
}
