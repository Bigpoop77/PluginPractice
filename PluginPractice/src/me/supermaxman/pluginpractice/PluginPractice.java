package me.supermaxman.pluginpractice;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;//This import is the one which uses the Vault.jar for univeral permissions support, I reccomend using Vault as a library so that you have universal support

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;



public class PluginPractice extends JavaPlugin {
	
	/*
	 * Note:
	 * I currently have two custom jars being used as libraries for this project. I am using the craftbukkit-1.7.9.jar and the Vault.jar
	 * Vault is a library used for permissions, and allows me to make universally compatable plugins which work with any permission plugins servers use such as PermissionsEx or other ones
	 * 
	 */
	
	public static PluginPractice plugin;//We create a plugin object to refer to this plugin, just reduces clutter
	public static final Logger log = Logger.getLogger("Minecraft");//The log is used to send messages to the server console, which you can see in onEnable and onDisable
	public static Permission permission = null;//We set the permissions object up as null so that it can be set when the server enables

	public static ArrayList<String> balls = new ArrayList<String>();//This is an ArrayList, and it is holding a list of Strings, which are the unique identification strings of the lightningball projectiles
	
	public void onEnable() {//onEnable automatically happens when your plugin is enabled by the server
		
		plugin = this;//set the plugin object to the plugin class
		if (!setupPermissions()) {//This code checks to see if the server is running Vault, and if it isnt the plugin disables itself. This code is provided by the Vault API website
        	log.severe("Vault fail!");//Send error to console
            this.setEnabled(false);//disable plugin
            return;//stop enabling plugin
        }
		
        getCommand("charge").setExecutor(new ChargeExecutor(this));//this is how you register plugin executors, which is my prefered way to handle commands. It causes less of a mess and allows multiple class files    
		
		getServer().getPluginManager().registerEvents(new PluginPracticeListener(), plugin);//This registers server events to your event listener class file
		
		log.info(getName() + " has been enabled.");//Send enable message to console
		
	}
	
	
	public void onDisable() {//onDisable automatically happens when your plugin is disabled by the server
		
		log.info(getName() + " has been disabled.");//send disable message
		
	}
	
	private boolean setupPermissions() {//This code is provided by the Vault API website to enable Vault, just place this in your plugin and make sure you use the other code onEnable
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
            return true;
        }
        return false;
    }
}