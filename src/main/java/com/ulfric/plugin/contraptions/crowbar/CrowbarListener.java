package com.ulfric.plugin.contraptions.crowbar;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class CrowbarListener implements Listener {
		
	@EventHandler
	public void on(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		Block blockBroken = event.getBlock();
		
		CrowbarAgent crowbarAgent = new CrowbarAgent();
		crowbarAgent.handle(player, item, blockBroken);
	}
	
	@EventHandler
	public void on(PlayerJoinEvent event) {
		Crowbar crowbar = new Crowbar();
		crowbar.create();
		event.getPlayer().getInventory().addItem(crowbar.getItem());
	}
}
