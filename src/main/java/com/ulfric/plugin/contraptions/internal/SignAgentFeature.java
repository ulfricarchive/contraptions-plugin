package com.ulfric.plugin.contraptions.internal;

import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.application.Application;
import com.ulfric.dragoon.application.Feature;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.contraptions.SignAgent;

public class SignAgentFeature extends Feature {

	@Inject
	private ObjectFactory factory;

	@Override
	public Application apply(Object object) {
		if (object instanceof SignAgent) {
			return factory.request(SignAgentApplication.class, object);
		}
		return null;
	}

}
