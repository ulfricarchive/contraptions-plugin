package com.ulfric.plugin.contraptions.elevators;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.ulfric.plugin.locale.TellService;

public class ElevatorSignListener implements Listener {

	@EventHandler
	public void on(BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		Player player = event.getPlayer();
		
		if (!isElevatorSign(block)) {
			return;
		}
		
		Sign sign = (Sign) block.getState();
		
		ElevatorDirection direction = ElevatorDirection.caseInsensitiveDirection(sign.getLine(1));
		
		if (direction == null) {
			
			if (player != null) {
				TellService.sendMessage(player, "elevator-invalid-direction");
			}
			
			return;
		}
	}
	
	private boolean isElevatorSign(Block block) {
		if (!(block.getState() instanceof Sign)) {
			return false;
		}
		
		return ((Sign) block.getState()).getLine(0).equalsIgnoreCase("[Elevator]");
	}

}
