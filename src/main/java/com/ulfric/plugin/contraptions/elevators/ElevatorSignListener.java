package com.ulfric.plugin.contraptions.elevators;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.ulfric.plugin.locale.TellService;

public class ElevatorSignListener implements Listener {

	@EventHandler
	public void on(SignChangeEvent event) {
		
		Block block = event.getBlock();
		Player player = event.getPlayer();
		
		if (!isElevatorSign(block)) {
			return;
		}
		
		Sign sign = (Sign) block.getState();
		
		ElevatorDirection direction = ElevatorDirection.caseInsensitiveDirection(event.getLine(1));
		
		if (direction == null) {

			event.setCancelled(true);
			cancelSignPlace(block);
			
			if (player != null) {
				TellService.sendMessage(player, "elevator-invalid-direction");
			}
			
			return;
		}
		
		rewriteSign(sign, direction);
		TellService.sendMessage(player, "elevator-created");
	}
	
	@EventHandler(ignoreCancelled = true)
	public void on(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		
		if (block == null || !(block.getState() instanceof Sign)) {
			return;
		}
		
		if (isElevatorSign(block) && !event.getPlayer().isSneaking()) {
			Sign sign = (Sign) block.getState();
			ElevatorDirection direction = ElevatorDirection.caseInsensitiveDirection(sign.getLine(1));
			
			event.getPlayer().teleport(direction.closestTeleport(sign.getLocation()));
		}
	}
	
	private void rewriteSign(Sign sign, ElevatorDirection direction) {
		sign.setLine(0,  "[Elevator]");
		sign.setLine(1, StringUtils.capitalize(direction.name().toLowerCase()));
	}
	
	private void cancelSignPlace(Block block) {
		block.setType(Material.AIR);
		
		ItemStack item = new ItemStack(Material.SIGN); 
		block.getLocation().getWorld().dropItemNaturally(block.getLocation(), item);
	}
	
	private boolean isElevatorSign(Block block) {
		if (!(block.getState() instanceof Sign)) {
			return false;
		}
		
		return ((Sign) block.getState()).getLine(0).equalsIgnoreCase("[Elevator]");
	}

}
