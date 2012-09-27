package net.lethargiclion.knowitall;

import java.util.logging.Logger;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    Logger playerLogger;
    
    public PlayerListener(Logger l) {
        playerLogger = l;
    }
    
    /*
    @EventHandler(priority=EventPriority.MONITOR)
    public void onServerListPing(ServerListPingEvent event){
        log.info(String.format("[LIST] %s queried the server", event.getAddress().getHostAddress()));
    }
    */
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        playerLogger.info(String.format(
                "[CONNECT] %s/%s %s",
                event.getName(),
                event.getAddress().getHostAddress(),
                event.getResult() == PlayerPreLoginEvent.Result.ALLOWED ? "connected" : String.format("denied connection: %s", event.getKickMessage())
            ));
    }
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if(event.getResult() != PlayerLoginEvent.Result.ALLOWED)
        playerLogger.info(String.format(
                "[LOGIN] %s@%s was denied login: %s",
                event.getPlayer().getName(),
                KnowItAll.locToString(event.getPlayer().getLocation()),
                event.getKickMessage()
            ));
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerLogger.info(String.format(
                "[JOIN] %s@%s joined the game",
                event.getPlayer().getName(),
                KnowItAll.locToString(event.getPlayer().getLocation())
            ));
    }
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerLogger.info(String.format(
                "[QUIT] %s@%s left the game",
                event.getPlayer().getName(),
                KnowItAll.locToString(event.getPlayer().getLocation())
            ));
    }
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerKick(PlayerKickEvent event) {
        playerLogger.info(String.format(
                "[KICK] %s@%s was kicked: %s",
                event.getPlayer().getName(),
                KnowItAll.locToString(event.getPlayer().getLocation()),
                event.getReason()
            ));
    }
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        playerLogger.info(String.format(
                "[GAMEMODE] %s@%s entered %s mode",
                event.getPlayer().getName(),
                KnowItAll.locToString(event.getPlayer().getLocation()),
                event.getNewGameMode().toString()
            ));
    }
    
    public void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntityType() == EntityType.PLAYER && event instanceof PlayerDeathEvent) {
            PlayerDeathEvent pevent = (PlayerDeathEvent)event;
            Player p = (Player)pevent.getEntity();
            playerLogger.info(String.format(
                    "[DEATH] %s@%s died: %s",
                    p.getName(),
                    KnowItAll.locToString(p.getLocation()),
                    pevent.getDeathMessage()
                ));
        }
    }
    
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        playerLogger.info(String.format(
                "[SLEEP] %s@%s entered a bed",
                event.getPlayer().getName(),
                KnowItAll.locToString(event.getPlayer().getLocation())
            ));
    }
    
}
