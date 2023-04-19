package io.hamibot.dcgateway;

import io.github.cdimascio.dotenv.Dotenv;
import io.hamibot.dcgateway.core.ShardManager;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import io.hamibot.dcgateway.utils.hamibot.HamibotSocketData;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.regex.Pattern;

public class DiscordGateway {

	public 	DiscordGateway(Dotenv dotenv, @NotNull String[] args) {
		if (dotenv.get("TOKEN") == null) throw new IllegalArgumentException("Token Missing");
		boolean debug = Arrays.asList(args).contains("--debug");
		for(String shard : args[0].split(Pattern.quote(","))) {
			DiscordSocketData discordSocketData = new DiscordSocketData();
			discordSocketData.token = dotenv.get("TOKEN");
			discordSocketData.shardingId = Integer.parseInt(shard);
			discordSocketData.shardingCount = Integer.parseInt(args[1]);
			discordSocketData.debug = debug;

			HamibotSocketData hamibotSocketData = new HamibotSocketData();
			hamibotSocketData.debug = debug;
			hamibotSocketData.shardingId = Integer.parseInt(shard);
			hamibotSocketData.shardingCount = Integer.parseInt(args[1]);

			ShardManager.createConnection(discordSocketData, hamibotSocketData);
		}
	}
}
