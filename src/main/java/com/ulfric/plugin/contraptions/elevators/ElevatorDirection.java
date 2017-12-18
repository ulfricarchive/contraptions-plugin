package com.ulfric.plugin.contraptions.elevators;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;

import com.google.common.base.Strings;

public enum ElevatorDirection {

	UP(1),
	DOWN(-1);
	
	private static final Map<String, ElevatorDirection> LOOKUP = new HashMap<>();
	
	static {
		LOOKUP.put("up", UP);
		LOOKUP.put("down", DOWN);
	}
	
	public static ElevatorDirection caseInsensitiveDirection(String direction) {
		return LOOKUP.get(Strings.nullToEmpty(direction).toLowerCase());
	}
	
	private final int directionSign;

	private ElevatorDirection(int directionSign) {
		this.directionSign = directionSign;
	}
	
	public Location traverse(Location current) {
		return current.clone().add(0, directionSign, 0);
	}
	
	public Location closestTeleport(Location current) {
		int space = 0;
		int currentY = current.getBlockY();
		
		while (space < 2) {
			current = traverse(current);
			
			if (current.getBlock() == null && currentY > 2) {
				break;
			}
			
			currentY = current.getBlockY();
			
			if (currentY < 1) {
				break;
			}
			
			if (current.getBlock().getType() == Material.AIR) {
				space++;
			} else {
				space = 0;
			}
		}
		
		return current;
	}

}
