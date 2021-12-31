package net.johnpoliakov.tutomc.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFoodListener implements Listener {

    // On annule la faim
    @EventHandler
    public void onFoodDecease(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
}
