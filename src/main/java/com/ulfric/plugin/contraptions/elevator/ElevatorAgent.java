package com.ulfric.plugin.contraptions.elevator;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import com.ulfric.plugin.contraptions.SignAgent;
import com.ulfric.plugin.locale.TellService;

public class ElevatorAgent extends SignAgent {

	public ElevatorAgent() {
		super("elevator", "lift");
	}

	@Override
	public void handle(Player player, Sign sign) {
		ElevatorDirection direction = ElevatorDirection.parseDirection(sign.getLine(1));
		if (direction != null) {
			Location playerLocation = player.getLocation();
			playEffect(playerLocation);
			Location teleport = direction.closestTeleport(sign.getLocation());
			teleport = teleport.setDirection(playerLocation.getDirection());
			player.teleport(direction.closestTeleport(teleport)); // TODO use relative teleports
		}
	}

	@SuppressWarnings("deprecation")
	private void playEffect(Location location) {
		World world = location.getWorld();
		world.playSound(location, Sound.ENDERMAN_TELEPORT, 4F, 1F);
		world.playEffect(location, Effect.PORTAL, Material.PORTAL.getId(), 0, 0F, 0.3F, 0F, 1, 12, 2);
	}

	@Override
	protected boolean rewriteAdditionalLines(SignChangeEvent event) {
		String directionLine = event.getLine(1);

		ElevatorDirection direction = ElevatorDirection.parseDirection(directionLine);

		if (direction == null) {
			event.setCancelled(true);
			TellService.sendMessage(event.getPlayer(), "elevator-invalid-direction");
			return false;
		}

		event.setLine(1, StringUtils.capitalize(direction.name()));

		return true;
	}

}
