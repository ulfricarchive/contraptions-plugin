package com.ulfric.plugin.contraptions;

import com.ulfric.plugin.Plugin;
import com.ulfric.plugin.contraptions.elevators.ElevatorsContainer;

public class ContraptionsPlugin extends Plugin {

	public ContraptionsPlugin() {
		install(ElevatorsContainer.class);
	}

}
