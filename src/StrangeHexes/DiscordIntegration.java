package StrangeHexes;

import arc.util.Log;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.GuildMessageChannel;

public class DiscordIntegration {
    public static GatewayDiscordClient gateway;
    public static Snowflake webhookId = Snowflake.of(L);

    public static void connect() {
        try {
            gateway = DiscordClientBuilder.create("").build()
                    .login()
                    .block();
        } catch (Exception e) {
            Log.err("Failed to connect bot", e);
        }
    }

    public static void sendMessage(long channelId, String message) {
        gateway.getChannelById(Snowflake.of(channelId))
                .ofType(GuildMessageChannel.class)
                .flatMap(channel -> channel.createMessage(message))
                .subscribe();
    }

    public static void sendMessageWebhook(Snowflake webhookId, String message, String user) {
        gateway.getWebhookById(webhookId).flatMap(webhook -> {
           return webhook.execute().withContent(message).withUsername(user);
        }).subscribe();
    }
}
