package net.lethargiclion.knowitall;

/*
    This file is part of KnowItAll

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KnowItAll extends JavaPlugin {

	// Command executor
	private final KnowItAllCommandExecutor commandExecutor = new KnowItAllCommandExecutor(this);

	// Event Listeners
	private ChatListener chatListener;
	private CommandListener commandListener;
	private PlayerListener playerListener;

	// Loggers and log handlers
	private List<Handler> handlers;
	private Logger playerLog, chatLog, commandLog;
	
	int schedId;

	public void onEnable() { 

		PluginManager pm = this.getServer().getPluginManager();

		// No commands yet!
		//getCommand("command").setExecutor(commandExecutor);
		
		if(handlers == null) handlers = new ArrayList<Handler>();
		else handlers.clear();
		
		playerLog = setupLog("player.log");
		commandLog = setupLog("command.log");
		chatLog = setupLog("chat.log");

		// Register event listeners
		playerListener = new PlayerListener(playerLog);
		pm.registerEvents(playerListener, this);
		chatListener = new ChatListener(chatLog);
		pm.registerEvents(chatListener, this);
		commandListener = new CommandListener(commandLog);
		pm.registerEvents(commandListener, this);

		// Flush logs to disk every 5 seconds
		schedId = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		    public void run() {
		        Iterator<Handler> i = handlers.iterator();
		        while(i.hasNext()) {
		            i.next().flush();
		        }
		    }
		}, 5L, 5L);

	}
	
	public void onDisable() {
	    this.getServer().getScheduler().cancelTask(schedId);
	    
	    destroyLog(playerLog);
	    playerLog = null;
	    
	    destroyLog(commandLog);
	    commandLog = null;
	    
	    destroyLog(chatLog);
	    chatLog = null;
	}
	
	public Logger setupLog(String filename) {
	    // Set up logfile ready to go
        Logger log = Logger.getAnonymousLogger();
        log.setUseParentHandlers(false);
        
        try {
            FileHandler fh = new FileHandler(filename, true);
            log.addHandler(fh);
            fh.setFormatter(new LogFormatter());
        } catch (SecurityException e) {
            this.getLogger().log(Level.SEVERE, String.format("SecurityException while opening %s!", filename), e);
        } catch (IOException e) {
            this.getLogger().log(Level.WARNING, String.format("Could not open %s! Continuing anyway...", filename), e);
        }
        // If we couldn't open the handler, then we have a Logger that doesn't log anywhere.
        // Harmless to log to, but ultimately useless...
        log.info("Start of log");
        return log;
	}
	
	public void destroyLog(Logger log) {
	    // Print end marker into log
	    log.info("End of log");
	    
	    // Retrieve and close each handler on the log (there should only be one)
	    Handler[] handlers = log.getHandlers();
	    for(Handler h: handlers) {
	        this.handlers.remove(h);
	        log.removeHandler(h);
	        h.close();
	    }   
	}


	/**
	 * Utility method for translating org.bukkit.Location into a string for logging.
	 * @param where The Location to convert to a string.
	 * @return String representation of the Location.
	 */
	public static String locToString(Location where) {
	    return String.format("(%s/%d,%d,%d)", where.getWorld().getName(), where.getBlockX(), where.getBlockY(), where.getBlockZ());
	}
}
