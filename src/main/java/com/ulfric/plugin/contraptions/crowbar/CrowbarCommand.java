package com.ulfric.plugin.contraptions.crowbar;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Command;
import com.ulfric.plugin.commands.permissions.Permission;

@Name("crowbar")
public class CrowbarCommand extends Command {

	@Override
	public void run() {
		Player player = (Player) sender();
		Crowbar crowbar = new Crowbar();
		System.out.println("Creating crowbar.");
		crowbar.create();
		player.getInventory().addItem(crowbar.getItem());
	}
}
