package com.ulfric.plugin.contraptions.elevators.store;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.ulfric.dragoon.acrodb.Database;
import com.ulfric.dragoon.acrodb.Store;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.contraptions.elevators.Elevator;
import com.ulfric.plugin.contraptions.elevators.ElevatorDirection;
import com.ulfric.plugin.contraptions.elevators.ElevatorService;

public class PersistentElevators implements ElevatorService {
	
	private final Map<Location, Elevator> elevatorCache = new HashMap<>();
	
	@Inject
	@Database({"elevators"})
	private Store<ElevatorDocument> elevators;

	@Override
	public Class<ElevatorService> getService() {
		return ElevatorService.class;
	}

	@Override
	public CompletableFuture<Elevator> getElevator(Location location) {
		Elevator cached = elevatorCache.get(location);
		if (cached != null) {
			return CompletableFuture.completedFuture(cached);
		}
		
		return elevators.getAsynchronous(location.toString())
				.thenApply(document -> {
					Elevator elevator = new Elevator(location, document.getDirection());
					elevatorCache.put(location, elevator);
					
					return elevator;
				});
	}

	@Override
	public CompletableFuture<Boolean> isElevator(Location location) {
		return getElevator(location)
				.handleAsync((found, throwable) -> found != null && throwable == null);
	}

	@Override
	public CompletableFuture<Void> createElevator(Sign sign, ElevatorDirection direction) {
		ElevatorDocument document = new ElevatorDocument();
		document.setLocation(sign.getLocation());
		document.setDirection(direction);
		
		return elevators.persistAsynchronous(document);
	}

	@Override
	public void deleteElevator(Location location) {
		elevators.deleteDocument(location.toString());
	}

	@Override
	public void elevate(Elevator elevator, Player player) {
		ElevatorDirection direction = elevator.getDirection();
		
		player.teleport(direction.closestTeleport(elevator.getLocation()));
	}
	
	

}
