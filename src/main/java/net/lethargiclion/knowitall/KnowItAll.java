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

import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KnowItAll extends JavaPlugin {

	//ClassListeners
	private final KnowItAllCommandExecutor commandExecutor = new KnowItAllCommandExecutor(this);
	//ClassListeners

	public void onDisable() {
		// add any code you want to be executed when your plugin is disabled
	}

	public void onEnable() { 

		PluginManager pm = this.getServer().getPluginManager();

		getCommand("command").setExecutor(commandExecutor);

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
