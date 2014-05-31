package me.supermaxman.pluginpractice;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChargeExecutor extends BaseExecutor { //I extend my BaseExecutor.class so that this command class is smaller and can be specific for this command
	
    @Override //This @Override is used to override the function in the BaseExecutor.class, dont mind it
    protected void run(Player player, String[] args) {
    	if(PluginPractice.permission.has(player, "PluginPractice.lightningball.create")) {//Here we check the permissions back in PluginPractice.class to see if the player has the right permission node
    		if(player.getItemInHand().getType()==Material.CLAY_BALL) {//Here we check the item in the player's hand to see if it is a clay ball
    			ItemStack items = player.getItemInHand();//I create the object to reduce clutter, this ItemStack represents the item in the player's hand
    			ItemMeta m = items.getItemMeta();//I create a ItemMeta object so I can modify it and then put it back into the ItemStack
    		    m.setDisplayName(ChatColor.YELLOW + "LightningBall");//I change the ItemMeta object to use the item display name LightningBall with the yellow chat color
    		    items.setItemMeta(m);//I set the metadata for the item in the player's hand to the new metadata I have created with the display name
        		player.sendMessage(ChatColor.YELLOW + "You are now holding LightningBalls.");//I send the player a message confirming the command has run correctly.
    		}else {
        		player.sendMessage(ChatColor.RED + "You are not holding any clay balls in your hand.");//This will send if the player is not holding a clay ball ItemStack when he runs the command
    		}
    	}else {
    		player.sendMessage(ChatColor.RED + "You do not have permission to use that command.");//This will send if the player does not have the permission node in the permissions file
    	}
    }

    public ChargeExecutor(PluginPractice pl) {//This is just something that is needed with using BaseExecutor.class
        super(pl);
    }
}
