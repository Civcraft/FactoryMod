package com.github.igotyou.FactoryMod.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vg.civcraft.mc.namelayer.command.PlayerCommand;

import com.github.igotyou.FactoryMod.FactoryModPlugin;
import com.github.igotyou.FactoryMod.managers.FactoryModManager;
import com.github.igotyou.FactoryMod.managers.NetherFactoryManager;

public class FactoryLookupCommand extends PlayerCommand {

	public FactoryLookupCommand(String name) {
		super(name);
		setIdentifier("nflookup");
		setDescription("Tells you where the closes nether factory is");
		setUsage("/nflookup");
		setArguments(0,0);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Sorry, only players can use this command");
			return true;
		}
		Player player = (Player) sender;
		if(!player.getLocation().getWorld().getName().equalsIgnoreCase(FactoryModPlugin.WORLD_NAME)) {
			player.sendMessage(ChatColor.RED + "There are no Nether Factories in this dimension!");
			return true;
		}
		NetherFactoryManager nfMan = (NetherFactoryManager) FactoryModManager.factoryMan.getManager(NetherFactoryManager.class);
		if(nfMan == null) {
			player.sendMessage(ChatColor.RED + "Something went wrong, try again later");
			return true;
		}
		Location nearestNF = nfMan.getClosestFactoryToPlayer(player);
		if(nearestNF == null) {
			player.sendMessage(ChatColor.RED + "There are currently no Nether Factories in existence!");
			return true;
		}
		int factoryX = nearestNF.getBlockX();
		int factoryY = nearestNF.getBlockY();
		int factoryZ = nearestNF.getBlockZ();
		double distance = nearestNF.distance(player.getLocation());
		player.sendMessage(ChatColor.GREEN + "The nearest Nether Factory is located at [" + factoryX + ", " + factoryY + ", " + factoryZ + "] which is " + (int) distance + "meters away!");
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}

}
