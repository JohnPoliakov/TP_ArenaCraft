package net.johnpoliakov.tutomc;

import net.johnpoliakov.tutomc.commands.FightCommand;
import net.johnpoliakov.tutomc.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class TutoMC extends JavaPlugin {

    private GameManager gameManager;
    private static TutoMC instance;

    // On enregistre l'instance de notre plugin à son chargement
    @Override
    public void onLoad(){
        instance = this;
    }

    // Activation du plugin
    @Override
    public void onEnable() {

        // On enregistre l'ensemble de nos events
        EventsManager.registerEvents(this);

        // ainsi que les commandes et divers paramètres
        registerCommands();
        registerRules();

        // On défini une nouvelle instance de notre GameManager qui va gérer l'ensemble des parties jusqu'à l'arrêt du server.
        gameManager = new GameManager();

        getServer().getConsoleSender().sendMessage("§6TutoMC §7> §aENABLED");

    }

    // Désactivation du plugin
    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage("§6TutoMC §7> §cDISABLED");

    }

    public static TutoMC getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    private void registerCommands(){

        getCommand("fight").setExecutor(new FightCommand());

    }

    // On renregistre différents gamerule sur l'ensemble des mondes chargés sur le serveur
    private void registerRules(){

        for(World w : Bukkit.getWorlds()){

            w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            w.setGameRule(GameRule.FALL_DAMAGE, false);
            w.setGameRule(GameRule.DROWNING_DAMAGE, false);
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            w.setGameRule(GameRule.NATURAL_REGENERATION, false);

        }

    }

}
