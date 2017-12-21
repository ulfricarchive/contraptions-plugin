package com.ulfric.plugin.contraptions.internal;

import java.util.Objects;

import org.bukkit.plugin.PluginManager;

import com.ulfric.dragoon.application.Application;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.contraptions.SignAgent;
import com.ulfric.plugin.contraptions.SignAgentService;

public class SignAgentApplication extends Application {

	private final SignAgent agent;

	@Inject
	private PluginManager manager;

	@Inject
	private SignAgentService service;

	public SignAgentApplication(SignAgent agent) {
		Objects.requireNonNull(agent, "agent");

		this.agent = agent;

		addBootHook(this::register);
		addShutdownHook(this::unregister);
	}

	private void register() {
		service.register(agent);
	}

	private void unregister() {
		service.unregister(agent);
	}

}
