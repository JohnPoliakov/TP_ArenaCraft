package net.johnpoliakov.tutomc.managers;

import net.johnpoliakov.tutomc.TutoMC;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    // on stock un scoreboard par joueur
    private final HashMap<UUID, Scoreboard> scoreboards = new HashMap<>();

    public void createScoreboard(Player p){

        // Si le joueur n'a pas encore de scoreboard alors on le crée
        if(!scoreboards.containsKey(p.getUniqueId())){

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            // On enregistre le titre du scoreboard
            Objective objective = scoreboard.registerNewObjective("global", "dummy", Component.text("§7ArenaCraft"));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            initialize(scoreboard);

            p.setScoreboard(scoreboard);
            scoreboards.put(p.getUniqueId(), scoreboard);

            // Sinon on lui récupère son précédent
        }else{
            p.setScoreboard(scoreboards.get(p.getUniqueId()));
        }

    }

    // On défini chaque ligne du scoreboard, peu importe l'ordre d'appel, c'est le paramètre line qui va définir
    private void initialize(Scoreboard scoreboard){

        addLine(scoreboard, "", "a", 7);
        addLine(scoreboard, "§4Statut: §cLobby", "statut", 6);
        addLine(scoreboard, "", "b", 5);
        addLine(scoreboard, "§6Timer: §e"+TimeManager.timertext, "timer", 4);
        addLine(scoreboard, "", "c", 3);
        addLine(scoreboard, "§2Score: §a0", "score", 2);
        addLine(scoreboard, "", "d", 1);

    }

    // On ajoute une ligne au scoreboard
    private void addLine(Scoreboard scoreboard, String content, String tag, int line){
        Team team = scoreboard.registerNewTeam(tag);
        team.addEntry(getUID(scoreboard));
        team.prefix(Component.text(content));
        scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(getUID(scoreboard)).setScore(line);
    }

    // On cherche un ID pour chaque ligne afin de les différencier
    private String getUID(Scoreboard scoreboard){

        StringBuilder uid = new StringBuilder("§r");
        for(Team ignored : scoreboard.getTeams()){
            uid.append("§r");
        }

        return uid.toString();
    }


    // Mise à jour du scoreboard
    public void update() {

        for(Map.Entry<UUID, Scoreboard> map : scoreboards.entrySet()){

            map.getValue().getTeam("statut").prefix(Component.text("§4Statut: §c"+TutoMC.getInstance().getGameManager().getState().getName()));

            map.getValue().getTeam("timer").prefix(Component.text("§6Timer: §e"+TimeManager.timertext));

            map.getValue().getTeam("score").prefix(Component.text("§2Score: §a"+ TutoMC.getInstance().getGameManager().getPlayerScore(map.getKey())));


        }

    }

    // On réinitialise l'ensemble des scoreboard
    public void reset(){

        scoreboards.clear();

        for(Player p : Bukkit.getOnlinePlayers()){

            createScoreboard(p);

        }

    }
}
