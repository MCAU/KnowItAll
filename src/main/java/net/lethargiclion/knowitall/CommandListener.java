package net.lethargiclion.knowitall;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import net.lethargiclion.knowitall.KnowItAll;

public class CommandListener implements Listener {
    
    Logger commandLogger;
    
    public CommandListener(Logger l) {
        commandLogger = l;
    }
    
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String logMessage = String.format("[COMMAND] %s@%s: %s",
                event.getPlayer().getName(),
                KnowItAll.locToString(event.getPlayer().getLocation()),
                event.getMessage()
            );
        commandLogger.info(logMessage);
    }
    
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onServerCommand(ServerCommandEvent event) {
        String logMessage = String.format("[CONSOLE] %s", event.getCommand());
        commandLogger.info(logMessage);
    }
    
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onRemoteServerCommand(RemoteServerCommandEvent event) {
        String logMessage = String.format("[RCON] %s", event.getCommand());
        commandLogger.info(logMessage);
    }

}
