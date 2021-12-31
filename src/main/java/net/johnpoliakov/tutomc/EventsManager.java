package net.johnpoliakov.tutomc;

import net.johnpoliakov.tutomc.player.PlayerDamageListener;
import net.johnpoliakov.tutomc.player.PlayerFoodListener;
import net.johnpoliakov.tutomc.player.PlayerInteractListener;
import net.johnpoliakov.tutomc.player.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {

    // On enregistre l'ensemble de nos events sinon les écoutes ne seront pas éxécutées

    // Les events s'enregistre sur l'instance du plugin d'où le paramètre passé
    public static void registerEvents(TutoMC pl){

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoinListener(), pl);
        pm.registerEvents(new PlayerInteractListener(), pl);
        pm.registerEvents(new PlayerDamageListener(), pl);
        pm.registerEvents(new PlayerFoodListener(), pl);

    }

}
