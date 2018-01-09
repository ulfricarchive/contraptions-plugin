package com.ulfric.plugin.contraptions.crowbar;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ulfric.dragoon.conf4j.Settings;

public class Crowbar {
	
	private ItemStack item;
	private static final String name = settings().displayName();
	private static List<String> lore = settings().lore();
	private static final Material material = settings().material();
	private static final int maxSpawnerUsage = settings().spawnerMaxUse();
	private static final int maxEndPortalUsage = settings().endMaxUse();
	private int spawnerUsage;
	private int endPortalUsage;
	
	@Settings
	private static CrowbarSettings settings;
	
	public Crowbar() {
		spawnerUsage = maxSpawnerUsage;
		endPortalUsage = maxEndPortalUsage;
		CrowbarAgent.updateLore(this);
	}
	
	public Crowbar(ItemStack item) {
		this.spawnerUsage = CrowbarAgent.getSpawnerUsage(item);
		this.endPortalUsage = CrowbarAgent.getEndPortalUsage(item);
	}
	
	public void create() {
		item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public boolean outOfUses() {
		if (spawnerUsage <= 0 && endPortalUsage <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean canBreakSpawner() {
		if (spawnerUsage > 0) {
			return true;
		}
		return false;
	}
	
	public boolean canBreakEndPortal() {
		if (endPortalUsage > 0) {
			return true;
		}
		return false;
	}
	
	public void remove(Player player, ItemStack crowbar) {
		player.getInventory().remove(crowbar);
	}

	public int getSpawnerUsage() {
		return spawnerUsage;
	}

	public void setSpawnerUsage(int spawnerUsage) {
		this.spawnerUsage = spawnerUsage;
	}
	
	public void removeSpawnerUsage() {
		this.spawnerUsage--;
	}

	public int getEndPortalUsage() {
		return endPortalUsage;
	}

	public void setEndPortalUsage(int endPortalUsage) {
		this.endPortalUsage = endPortalUsage;
	}
	
	public void removeEndPortalUsage() {
		this.endPortalUsage--;
	}

	public int getMaxSpawnerUsage() {
		return maxSpawnerUsage;
	}

	public int getMaxEndPortalUsage() {
		return maxEndPortalUsage;
	}

	public String getName() {
		return name;
	}
	
	public List<String> getLore() {
		return lore;
	}

	public ItemStack getItem() {
		return item;
	}
	
	public Material getMaterial() {
		return material;
	}

	public static CrowbarSettings settings() {
		return settings;
	}

}
