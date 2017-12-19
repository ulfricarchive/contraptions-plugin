package com.ulfric.plugin.contraptions.elevators;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

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
		return new Location(
				current.getWorld(), 
				current.getX(), 
				current.getY() + directionSign, 
				current.getZ()
		);
	}
	
	public Location closestTeleport(Location current) {
		int space = 0;
		int currentY = current.getBlockY();
		
		while (space < 2) {
			current = traverse(current);
			Block block = current.getBlock();
			
			if (block == null && currentY > 2) {
				break;
			}
			
			currentY = block.getY();
			
			if (currentY < 1) {
				break;
			}
			
			if (block.getType() == Material.AIR) {
				space++;
			} else {
				space = 0;
			}
		}
		
		return current;
	}

}
