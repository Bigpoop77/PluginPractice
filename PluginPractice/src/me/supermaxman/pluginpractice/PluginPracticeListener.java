package me.supermaxman.pluginpractice;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

	
public class PluginPracticeListener implements Listener {//Implementing listener allows this class to become an event listener
	
	
	@EventHandler//@EventHandler tells the class the bukkit server is using for events that this plugin is trying to listen to an event, put this before every function in your listener class
	public void onPlayerInteract(PlayerInteractEvent e) {//I use the org.bukkit.event.player.PlayerInteractEvent to see when the player right clicks
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) ) {//This checks to see if the interaction is a right click in either air or on a block, and if it is either it continues
			ItemStack items = e.getItem();//Create ItemStack object to refer to the items in the player's hand
			if(items.getItemMeta().hasDisplayName() && items.getType()==Material.CLAY_BALL) {//If the item has a display name, which is custom metadata, and is a clay ball then the code continues. To understand the metadata more, see ChargeExecutor.java
				if(items.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "LightningBall")) {//If the item's metadata has a display name of LightningBall in yellow then the code continues
					e.setCancelled(true);//Cancel the event in case the player right clicked something he didnt want to while throwing the lightningball
					Player p = e.getPlayer();//Create a Player object to refer to the player
					if(!p.getGameMode().equals(GameMode.CREATIVE)) {//This code checks to see if the player is not in creative, and if he is not in creative it reduces the ammount of clay balls in his hand by 1
						if(items.getAmount()==1) {//this code removes the itemstack if the player is out of clay balls
							p.setItemInHand(null);//this sets the item in the player's hand to null, or air
						}
						items.setAmount(items.getAmount()-1);//this reduces the itemstack number of clay balls by 1
					}
					Snowball ball = p.launchProjectile(Snowball.class);//this creates a snowball projectile, and launches the projectile from the player
					PluginPractice.balls.add(ball.getUniqueId().toString());//this adds the projectile's custom unique identification string to the arraylist which stores them. This differentiates between normal snowballs and these custom projectiles
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {//This event uses the org.bukkit.event.entity.ProjectileHitEvent to find the location the custom snowball projectiles land so we can create the lightning
		if(e.getEntity() instanceof Snowball) {//this checks to see if the event entity that has hit something is a snowball
			if(PluginPractice.balls.contains(e.getEntity().getUniqueId().toString())) {//this checks the arraylist for the custom unique identification string of this entity, and if it is there it continues. If it is not there that means this is just a normal snowball
				PluginPractice.balls.remove(e.getEntity().getUniqueId().toString());//removes this unique id from the arraylist just to clear up space
				Location location = e.getEntity().getLocation();//Here I create a location object to refer to the location the snowball landed
				location.getWorld().strikeLightning(location);//Here I strike the lightning at the location the snowball landed
			}
		}
	}
	
	
}
