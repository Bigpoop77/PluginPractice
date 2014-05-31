package me.supermaxman.pluginpractice;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/*
 * This is a class I use to help seperate my command structure into class files. You do not really need to know what
 * this file does, all you need to do is extend this class in whatever command class you create.
 * See "ChargeExecutor.class" to see what I mean.
 * 
 */

abstract class BaseExecutor implements CommandExecutor {
	protected static PluginPractice pl;
    BaseExecutor(PluginPractice pl) {
        BaseExecutor.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        final boolean isPlayer = (sender instanceof Player);

        if (isPlayer) {
            player = (Player) sender;
        } else {
            return false;
        }

        this.run(player, args);

        return true;
    }

    protected abstract void run(Player player, String[] args);

}