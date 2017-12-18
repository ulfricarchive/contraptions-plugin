package com.ulfric.plugin.contraptions.elevators;

import java.util.concurrent.CompletableFuture;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.ulfric.plugin.services.Service;

public interface ElevatorService extends Service<ElevatorService> {
	
	static ElevatorService get() {
		return Service.get(ElevatorService.class);
	}
	
	CompletableFuture<Elevator> getElevator(Location location);
	
	CompletableFuture<Boolean> isElevator(Location location);
	
	CompletableFuture<Void> createElevator(Sign sign, ElevatorDirection direction);
	
	void deleteElevator(Location location);
	
	void elevate(Elevator elevator, Player player);

}
