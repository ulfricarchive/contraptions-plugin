package com.ulfric.plugin.contraptions.elevators;

import com.ulfric.dragoon.application.Container;

public class ElevatorsContainer extends Container {

	public ElevatorsContainer() {
		install(ElevatorSignListener.class);
	}

}
