package com.ulfric.plugin.contraptions.elevators;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.base.Strings;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.locale.TellService;
import com.ulfric.plugin.tasks.Scheduler;

public class ElevatorSignListener implements Listener {
	
	@Inject
	private ElevatorService elevatorService;
	
	@Inject
	private Scheduler scheduler;

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
		
		rewriteSign(sign, direction);
		elevatorService.createElevator(sign, direction);
		TellService.sendMessage(player, "elevator-created");
	}
	
	@EventHandler
	public void on(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if (block == null || !(block.getState() instanceof Sign)) {
			return;
		}
		
		elevatorService.deleteElevator(block.getLocation());
	}
	
	@EventHandler
	public void on(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		
		if (block == null || !(block.getState() instanceof Sign)) {
			return;
		}
		
		elevatorService.getElevator(block.getLocation()).whenComplete((elevator, throwable) -> {
			if (elevator != null && throwable == null) {
				scheduler.runOnMainThread(() -> {
					elevatorService.elevate(elevator, event.getPlayer());
				});
			}
		});
		
	}
	
	private void rewriteSign(Sign sign, ElevatorDirection direction) {
		sign.setLine(0,  "[Elevator]");
		sign.setLine(1, StringUtils.capitalize(direction.name().toLowerCase()));
	}
	
	private boolean isElevatorSign(Block block) {
		if (!(block.getState() instanceof Sign)) {
			return false;
		}
		
		return ((Sign) block.getState()).getLine(0).equalsIgnoreCase("[Elevator]");
	}

}
