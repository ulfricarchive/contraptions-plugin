package com.ulfric.plugin.contraptions;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public abstract class SignAgent {

	private final String primaryName;
	private final Set<String> names;

	public SignAgent(String primaryName, String... aliases) {
		Objects.requireNonNull(primaryName, "primharyName");
		Objects.requireNonNull(aliases, "aliases");

		this.primaryName = primaryName.trim().toLowerCase();
		this.names = Stream.concat(Stream.of(primaryName), Arrays.stream(aliases))
				.filter(Objects::nonNull)
				.map(String::trim)
				.map(String::toLowerCase)
				.collect(Collectors.toSet());
		this.names.add(primaryName);
	}

	public final String getPrimaryName() {
		return primaryName;
	}

	public abstract void handle(Player player, Sign sign);

	public final boolean rewrite(SignChangeEvent event) {
		String line = event.getLine(0);
		if (matches(line)) {
			if (rewriteAdditionalLines(event)) {
				event.setLine(0, ChatColor.DARK_BLUE + "[" + primaryName + "]");
				return true;
			}
		}

		return false;
	}

	protected boolean rewriteAdditionalLines(SignChangeEvent event) {
		return true;
	}

	public final boolean matches(String line) {
		if (StringUtils.isEmpty(line)) {
			return false;
		}

		line = line.toLowerCase();
		line = ChatColor.translateAlternateColorCodes('&', line);
		line = ChatColor.stripColor(line);
		line = line.trim();

		if (line.startsWith("[") && line.endsWith("]")) {
			line = line.substring(1, line.length() - 1);
		}

		return names.contains(line);
	}

}
