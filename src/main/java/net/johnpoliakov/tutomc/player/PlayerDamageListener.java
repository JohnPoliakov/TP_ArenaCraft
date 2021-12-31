package net.johnpoliakov.tutomc.player;

import net.johnpoliakov.tutomc.TutoMC;
import net.johnpoliakov.tutomc.utils.GameSettings;
import net.johnpoliakov.tutomc.utils.VFX;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    // On annule les dégâts sur le joueur si il se trouve dans la safeZone.
    @EventHandler
    public void onDamage(EntityDamageEvent e){

        if(e.getEntity() instanceof Player p){

            final Location loc = p.getLocation();
            e.setCancelled(isInArea(loc, GameSettings.ARENA_SAFE_ZONE_CORNER_1, GameSettings.ARENA_SAFE_ZONE_CORNER_2));


        }

    }


    // Check si la position d'un joueur donné se trouve dans un cuboid précisé par ses angles opposés
    private boolean isInArea(Location l, Location l1, Location l2){

        double x1 = Math.min(l1.getX(), l2.getX());
        double y1 = Math.min(l1.getY(), l2.getY());
        double z1 = Math.min(l1.getZ(), l2.getZ());

        double x2 = Math.max(l1.getX(), l2.getX());
        double y2 = Math.max(l1.getY(), l2.getY());
        double z2 = Math.max(l1.getZ(), l2.getZ());

        return l.getX() >= x1 && l.getX() <= x2 &&
                l.getY() >= y1 && l.getY() <= y2 &&
                l.getZ() >= z1 && l.getZ() <= z2;
    }


    // Écoute des dégâts pour enregistrer les kills
    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e){


        if(e.getEntity() instanceof Player p){

            // Si la cible meurt
            if(e.getFinalDamage() > p.getHealth()){
                e.setCancelled(true);

                resetPlayer(p);

                // Si le tueur est un joueur
                if(e.getDamager() instanceof Player killer){

                    // On comptabilise le kill
                    TutoMC.getInstance().getGameManager().incrementScore(killer.getUniqueId());

                    // On redonne de la vie pour récompenser le tueur
                    killer.setHealth(Math.min(killer.getHealth() + 6, killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));

                    VFX.playSound(killer, Sound.BLOCK_NOTE_BLOCK_PLING);

                    // Si l'entité tueuse est une flèche on récompense le tireur
                }else if(e.getDamager() instanceof Arrow a && a.getShooter() instanceof Player killer){

                    TutoMC.getInstance().getGameManager().incrementScore(killer.getUniqueId());

                    killer.setHealth(Math.min(killer.getHealth() + 6, killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));

                    VFX.playSound(killer, Sound.BLOCK_NOTE_BLOCK_PLING);

                }

            }

        }

    }

    // "Réinitialise" un joueur pour son respawn
    private void resetPlayer(Player p){
        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        p.teleport(GameSettings.ARENA_SPAWN);
    }
}
