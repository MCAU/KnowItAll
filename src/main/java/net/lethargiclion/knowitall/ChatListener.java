package net.lethargiclion.knowitall;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    
    Logger chatLogger;
    
    public ChatListener(Logger l) {
        chatLogger = l;
    }
    
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        //FIXME: Thread safety?
        
        // Even log cancelled chat
        String logMessage = String.format("%s: %s", event.getPlayer().getName(), event.getMessage());
        chatLogger.info(logMessage);
    }
    


}
