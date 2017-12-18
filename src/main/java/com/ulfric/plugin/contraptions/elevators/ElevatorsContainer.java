package com.ulfric.plugin.contraptions.elevators;

import com.ulfric.dragoon.application.Container;
import com.ulfric.plugin.contraptions.elevators.store.PersistentElevators;

public class ElevatorsContainer extends Container {

	public ElevatorsContainer() {
		install(PersistentElevators.class);
		install(ElevatorSignListener.class);
	}

}
