package net.johnpoliakov.tutomc.player;

import net.johnpoliakov.tutomc.TutoMC;
import net.johnpoliakov.tutomc.utils.GameSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        final Player p = e.getPlayer();

        e.joinMessage(Component.text("§7[§a+§7] §f"+p.getName()));

        // Si la game est en LOBBY alors on reset tout
        if(TutoMC.getInstance().getGameManager().isLobby()) {

            /*
            *
            * Voici un ensemble de lignes assez basiques qui montre brièvement quelques opérations possibles sur l'objet Player.
            *
            * */

            p.setInvulnerable(true); // On rend le joueur invulnerable, attention il faut changer le boolean à nouveau pour annuler
            p.getInventory().clear(); // On vide l'inventaire
            p.setFoodLevel(20); // On rempli sa barre de nourriture
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20); // On change sa vie maximum possible
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()); // On rempli sa vie avec sa valeur maximum
            p.setGameMode(GameMode.ADVENTURE); // On change le GameMode
            p.teleport(GameSettings.LOBBY_SPAWN); // On TP

            // Sinon on change le gamemode selon si le joueur était là au lancement de la partie.
        }else{

            p.setInvulnerable(false);
            p.setFoodLevel(20);

            p.setGameMode(TutoMC.getInstance().getGameManager().getScore().containsKey(p.getUniqueId()) ? GameMode.ADVENTURE : GameMode.SPECTATOR);

        }

        TutoMC.getInstance().getGameManager().getScoreboardManager().createScoreboard(p);

    }

}
