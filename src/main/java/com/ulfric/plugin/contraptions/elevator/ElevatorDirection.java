package com.ulfric.plugin.contraptions.elevator;

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

	public static ElevatorDirection parseDirection(String direction) {
		return LOOKUP.get(Strings.nullToEmpty(direction).trim().toLowerCase());
	}

	private final double directionSign;

	private ElevatorDirection(double directionSign) {
		this.directionSign = directionSign;
	}

	public Location traverse(Location current) {
		return current.add(0, directionSign, 0);
	}

	public Location closestTeleport(Location current) {
		current = current.clone();
		int space = 0;
		int currentY = 0;
		int validY = -1;
		int max = current.getWorld().getMaxHeight();

		do {
			current = traverse(current);
			Block block = current.getBlock();
			currentY = block == null ? current.getBlockY() : block.getY();

			if (isEmpty(block)) {
				space++;
				if (validY == -1) {
					validY = currentY;
				}
			} else {
				validY = -1;
				space = 0;
			}

		} while (space < 2 && currentY > 0 && currentY < max);

		if (space < 2) {
			return null;
		}
		current.setY(validY);
		return current;
	}

	private boolean isEmpty(Block block) {
		if (block == null) {
			return true;
		}

		Material material = block.getType();
		return material == null || material == Material.AIR || (!material.isSolid() && !block.isLiquid());
	}

}
