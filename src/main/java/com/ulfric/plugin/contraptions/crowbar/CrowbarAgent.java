package com.ulfric.plugin.contraptions.crowbar;

 import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CrowbarAgent {
	
	private final String crowbarName = ChatColor.GOLD + "Crowbar";
	
	public void handle(Player player, ItemStack item, Block blockBroken) {
		if (!itemIsCrowbar(crowbarName, item)) {
			System.out.println("Not crowbar.");
			return;
		}
		
		Crowbar crowbar = new Crowbar(item);
		Material brokenType = blockBroken.getType();
		
		if (brokenType == Material.MOB_SPAWNER) {
			if (crowbar.canBreakSpawner()) {
				crowbar.removeSpawnerUsage();
				updateLore(crowbar);
				return;
			}
		}
		
		if (brokenType == Material.ENDER_PORTAL) {
			if (crowbar.canBreakEndPortal()) {
				crowbar.removeEndPortalUsage();
				updateLore(crowbar);
				return;
			}
		}
		
		if (brokenType == Material.MOB_SPAWNER || brokenType == Material.ENDER_PORTAL) {
			if (crowbar.outOfUses()) {
				crowbar.remove(player, item);
				playBreakEffect(player.getLocation());
				return;
			}
		}
	}
	
	private boolean itemHasName(String name, ItemStack item) {
		if (item == null) {
			return false;
		}
		
		if (!item.hasItemMeta()) {
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		if (meta.getDisplayName() == null) {
			return false;
		}

		return item.getItemMeta().getDisplayName().equals(name);
	}
	
	private boolean itemIsCrowbar(String crowbarName, ItemStack item) {
		if (!itemHasName(crowbarName, item)) {
			return false;
		}
		return true;
	}
	
	public static int getSpawnerUsage(ItemStack item) {
		return getSpawnerUsageFromLore(item.getItemMeta().getLore());
	}
	
	public static int getEndPortalUsage(ItemStack item) {
		return getEndPortalUsageFromLore(item.getItemMeta().getLore());
	}
	
	private static int getSpawnerUsageFromLore(List<String> lore) {
		String usageLine = lore.get(1);
		String[] usageParts = usageLine.split(":");
		int usageRemaining = Integer.parseInt(usageParts[1]);
		return usageRemaining;
	}
	
	private static int getEndPortalUsageFromLore(List<String> lore) {
		String usageLine = lore.get(1);
		String[] usageParts = usageLine.split(":");
		int usageRemaining = Integer.parseInt(usageParts[1]);
		return usageRemaining;
	}
	
	private void playBreakEffect(Location location) {
		location.getWorld().playSound(location, Sound.ITEM_BREAK, 4F, 1F);
	}
	
	
	public static void updateLore(Crowbar crowbar) {
		for (String lore : crowbar.getLore()) {
			String[] parts = lore.split(":");
			String usage = parts[1].trim();
			usage = String.valueOf(crowbar.getSpawnerUsage());
			lore = parts[0] + ": " + usage;
		}
	}
}
