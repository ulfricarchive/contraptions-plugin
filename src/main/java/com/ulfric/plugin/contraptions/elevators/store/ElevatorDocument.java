package com.ulfric.plugin.contraptions.elevators.store;

import org.bukkit.Location;

import com.ulfric.dragoon.acrodb.Document;
import com.ulfric.plugin.contraptions.elevators.ElevatorDirection;

public class ElevatorDocument extends Document {

	private Location location;
	private ElevatorDirection direction;
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ElevatorDirection getDirection() {
		return direction;
	}
	
	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}
	
}
