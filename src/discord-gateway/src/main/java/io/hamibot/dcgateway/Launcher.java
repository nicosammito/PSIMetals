package io.hamibot.dcgateway;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

public class Launcher {
	/**
	 *
	 * @param args all arguments that are given when stating client
	 */
	public static void main(@NotNull String[] args) {
		if(args.length<2) throw new IllegalArgumentException("Args should contain shard_id and shard_count");
		new DiscordGateway(Dotenv.load(),args);
	}
}
