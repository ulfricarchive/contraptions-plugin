package com.ulfric.plugin.contraptions;

import com.ulfric.plugin.Plugin;
import com.ulfric.plugin.contraptions.crowbar.CrowbarCommand;
import com.ulfric.plugin.contraptions.crowbar.CrowbarListener;
import com.ulfric.plugin.contraptions.elevator.ElevatorAgent;
import com.ulfric.plugin.contraptions.internal.SignAgentFeature;
import com.ulfric.plugin.contraptions.internal.SignAgentListener;

public class ContraptionsPlugin extends Plugin {

	public ContraptionsPlugin() {
		install(SignAgentFeature.class);
		install(SignAgentService.class);
		install(SignAgentListener.class);
		install(ElevatorAgent.class);
		
		install(CrowbarListener.class);
		install(CrowbarCommand.class);
	}

}
