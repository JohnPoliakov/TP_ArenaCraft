package net.johnpoliakov.tutomc.player;

import net.johnpoliakov.tutomc.managers.ItemManager;
import net.johnpoliakov.tutomc.utils.ClassType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    // Sélection de la classe dans la safeZone
    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        final Player p = e.getPlayer();
        final Action action = e.getAction();

        if(action == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ENCHANTING_TABLE){

            e.setCancelled(true);

            Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, Component.text("             §aÉquipement"));


            inventory.setItem(1, new ItemManager(Material.IRON_SWORD, "§cClasse guerrier", 1,Component.text("§7- Armure en fer"), Component.text("§7- Épée tranchant II")).build());
            inventory.setItem(3, new ItemManager(Material.BOW, "§cClasse archer", 1,Component.text("§7- Armure en cuir"), Component.text("§7- Arc power II")).build());


            p.openInventory(inventory);
        }

    }

    // On équipe le joueur avec la classe sélectionnée
    public static void equipPlayer(Player p, ClassType type){

        p.getInventory().clear();

        switch (type){

            case WARRIOR -> {
                p.getEquipment().setHelmet(new ItemManager(Material.IRON_HELMET, "§6Casque", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setChestplate(new ItemManager(Material.IRON_CHESTPLATE, "§6Plastron", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setLeggings(new ItemManager(Material.IRON_LEGGINGS, "§6Pantalon", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setBoots(new ItemManager(Material.IRON_BOOTS, "§6Bottes", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setItemInMainHand(new ItemManager(Material.IRON_SWORD, "§cÉpée", 1)
                        .addEnchant(Enchantment.DAMAGE_ALL, 2)
                        .setUnbreakable()
                        .build());
            }

            case BOWMAN -> {


                p.getEquipment().setHelmet(new ItemManager(Material.LEATHER_HELMET, "§6Casque", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setChestplate(new ItemManager(Material.LEATHER_CHESTPLATE, "§6Plastron", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setLeggings(new ItemManager(Material.LEATHER_LEGGINGS, "§6Pantalon", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setBoots(new ItemManager(Material.LEATHER_BOOTS, "§6Bottes", 1)
                        .setUnbreakable()
                        .build());

                p.getEquipment().setItemInMainHand(new ItemManager(Material.BOW, "§cArc", 1)
                        .addEnchant(Enchantment.ARROW_DAMAGE, 2)
                        .addEnchant(Enchantment.ARROW_INFINITE, 1)
                        .setUnbreakable()
                        .build());

                p.getInventory().addItem(new ItemStack(Material.ARROW));

            }

        }
    }

    // Ouverture inventaire de sélection de kits
    @EventHandler
    public void onClick(InventoryClickEvent e){

        final Inventory inventory = e.getClickedInventory();
        final Player p = (Player) e.getWhoClicked();
        final ItemStack item = e.getCurrentItem();

        if(inventory != null){

            if(e.getView().title().equals(Component.text("             §aÉquipement")) && item != null){

                e.setCancelled(true);

                p.closeInventory();

                switch (item.getType()) {


                    case IRON_SWORD -> {

                        equipPlayer(p, ClassType.WARRIOR);

                    }

                    case BOW -> {

                        equipPlayer(p, ClassType.BOWMAN);

                    }

                }

            }

        }

    }
}
