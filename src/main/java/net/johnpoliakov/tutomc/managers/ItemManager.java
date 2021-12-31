package net.johnpoliakov.tutomc.managers;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

    private final ItemStack item;

    // Constructeur pour notre item
    public ItemManager(Material material, String name, int amount, Component... lore){
        item = new ItemStack(material, amount);

        ItemMeta itemM = item.getItemMeta();

        itemM.displayName(Component.text(name));
        itemM.lore(Arrays.asList(lore));

        item.setItemMeta(itemM);
    }

    // On cache les attributs
    public ItemManager hideAttributes(){

        ItemMeta itemM = item.getItemMeta();

        itemM.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(itemM);

        return this;
    }

    // On rend l'item incassable
    public ItemManager setUnbreakable(){

        ItemMeta itemM = item.getItemMeta();

        itemM.setUnbreakable(true);

        item.setItemMeta(itemM);

        return this;
    }

    // On cache les enchantements
    public ItemManager hideEnchants(){

        ItemMeta itemM = item.getItemMeta();

        itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(itemM);

        return this;
    }

    // On ajoute l'enchantements
    public ItemManager addEnchant(Enchantment enchant, int level){


        ItemMeta itemM = item.getItemMeta();

        itemM.addEnchant(enchant, level, true);

        item.setItemMeta(itemM);

        return this;
    }

    // On retourne l'item construit
    public ItemStack build() {
        return item;
    }
}
