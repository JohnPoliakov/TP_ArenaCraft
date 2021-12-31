package net.johnpoliakov.tutomc.managers;

import net.johnpoliakov.tutomc.TutoMC;
import net.johnpoliakov.tutomc.player.PlayerInteractListener;
import net.johnpoliakov.tutomc.utils.ClassType;
import net.johnpoliakov.tutomc.utils.GameSettings;
import net.johnpoliakov.tutomc.utils.GameState;
import net.johnpoliakov.tutomc.utils.VFX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;

public class TimeManager {

    private static double timer = 605;
    public static String timertext = (int)timer/60+":00";

    public static void start(){

        // On lance un tâche qui se répète toutes les secondes
        new BukkitRunnable() {
            @Override
            public void run() {

                // Si la partie n'est pas lancée on coupe
                if (TutoMC.getInstance().getGameManager().isLobby()) {
                    cancel();
                }

                // On met à jour le scoreboard
                TutoMC.getInstance().getGameManager().getScoreboardManager().update();


                // On switch sur le timer afin d'afficher un compte à rebours
                switch ((int) timer) {

                    case 605 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            VFX.sendTitle(p, "§45", "", 200, 600, 200);

                        }
                    }
                    case 604 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            VFX.sendTitle(p, "§c4", "", 200, 600, 200);

                        }
                    }
                    case 603 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            VFX.sendTitle(p, "§63", "", 200, 600, 200);

                        }
                    }
                    case 602 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            VFX.sendTitle(p, "§e2", "", 200, 600, 200);

                        }
                    }
                    case 601 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            VFX.sendTitle(p, "§a1", "", 200, 600, 200);

                        }
                    }
                    case 600 -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            VFX.sendTitle(p, "§bGO", "", 200, 600, 200);
                            VFX.notifyPlayer(p, "§aDébut de la partie", Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                            p.teleport(GameSettings.ARENA_SPAWN);
                            p.setInvulnerable(false);

                            PlayerInteractListener.equipPlayer(p, ClassType.WARRIOR);

                            TutoMC.getInstance().getGameManager().getScore().putIfAbsent(p.getUniqueId(), 0);

                        }
                        TutoMC.getInstance().getGameManager().setState(GameState.RUNNING);
                    }

                    // On stop la partie une fois le timer arrivé à 0
                    case 0 -> {

                        TutoMC.getInstance().getGameManager().stop();
                        reset();

                    }

                }

                // On décrémente le timer et on update le texte
                timer--;
                timertext = new SimpleDateFormat("mm:ss").format(timer * 1000);
            }

        }.runTaskTimer(TutoMC.getInstance(), 0L, 20L);

    }

    // Reset du timer
    public static void reset(){
        timer = 605;
        timertext = (int)timer/60+":00";
    }

}
