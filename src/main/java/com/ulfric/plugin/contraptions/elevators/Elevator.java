package com.ulfric.plugin.contraptions.elevators;

import org.bukkit.Location;

public class Elevator {

	private final Location location;
	private final ElevatorDirection direction;

	public Elevator(Location location, ElevatorDirection direction) {
		this.location = location;
		this.direction = direction;
	}

	public Location getLocation() {
		return location;
	}

	public ElevatorDirection getDirection() {
		return direction;
	}

}
