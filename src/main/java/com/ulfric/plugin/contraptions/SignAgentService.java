package com.ulfric.plugin.contraptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import com.ulfric.plugin.services.Service;

public class SignAgentService implements Service<SignAgentService> {

	private final List<SignAgent> agents = new ArrayList<>();

	@Override
	public Class<SignAgentService> getService() {
		return SignAgentService.class;
	}

	public void register(SignAgent agent) {
		Objects.requireNonNull(agent, "agent");

		agents.add(agent);
	}

	public void unregister(SignAgent agent) {
		Objects.requireNonNull(agent, "agent");

		agents.remove(agent);
	}

	public SignAgent write(SignChangeEvent event) {
		for (SignAgent agent : agents) {
			if (agent.rewrite(event)) {
				return agent;
			}
		}

		return null;
	}

	public void click(Player player, Sign sign) {
		String line = sign.getLine(0);
		for (SignAgent agent : agents) {
			if (agent.matches(line)) {
				agent.handle(player, sign);
			}
		}
	}

}
