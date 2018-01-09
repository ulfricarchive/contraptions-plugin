package com.ulfric.plugin.contraptions.crowbar;

import java.util.List;

import org.bukkit.Material;

import com.ulfric.conf4j.ConfigurationBean;

public interface CrowbarSettings extends ConfigurationBean {
	
	String displayName();
	List<String> lore();
	Material material();
	int spawnerMaxUse();
	int endMaxUse();

}
