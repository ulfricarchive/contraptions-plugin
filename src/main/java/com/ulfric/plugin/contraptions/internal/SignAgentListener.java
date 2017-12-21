package com.ulfric.plugin.contraptions.internal;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.contraptions.SignAgent;
import com.ulfric.plugin.contraptions.SignAgentService;

public class SignAgentListener implements Listener {

	@Inject
	private SignAgentService service;

	@EventHandler(ignoreCancelled = true)
	public void on(SignChangeEvent event) {
		SignAgent agent = service.write(event);
		if (agent != null) {
			if (!event.getPlayer().hasPermission("contraptions-create-agent-" + agent.getPrimaryName())) {
				event.setCancelled(true);
				// TODO play an affect
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void on(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Block block = event.getClickedBlock();
		if (block == null) {
			return;
		}

		BlockState state = block.getState();
		if (!(state instanceof Sign)) {
			return;
		}

		Sign sign = (Sign) state;
		service.click(event.getPlayer(), sign);
	}

}
