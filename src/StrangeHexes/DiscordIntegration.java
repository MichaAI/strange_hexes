package StrangeHexes;

import arc.util.Log;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.spec.EmbedCreateSpec;

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

    //public static void sendMessage(long channelId, String message) {
    //    gateway.getChannelById(Snowflake.of(channelId))
    //            .ofType(GuildMessageChannel.class)
    //            .flatMap(channel -> channel.createMessage(message))
    //            .subscribe();
    //}

    public static void sendMessageWebhook(Snowflake webhookId, String message, String webhookUsername) {
        gateway.getWebhookById(webhookId).flatMap(webhook -> {
           return webhook.execute()
                   .withContent(message)
                   .withUsername(webhookUsername)
                   .withAvatarUrl("https://github.com/Anuken/Mindustry/blob/master/core/assets/sprites/error.png?raw=true"); //TODO change this please
        }).subscribe();
    }

    public static void sendMessageWebhookWithEmbed(Snowflake webhookId, String webhookUsername, EmbedCreateSpec embed) {
        gateway.getWebhookById(webhookId).flatMap(webhook -> {
            return webhook.execute()
                    .withEmbeds(embed)
                    .withUsername(webhookUsername)
                    .withAvatarUrl("https://w7.pngwing.com/pngs/164/999/png-transparent-drawing-samsung-galaxy-s-computer-servers-productivity-database-big-data-purple-text-service.png"); // TODO поменяй эту хуйню долбоеб
        }).subscribe();
    }
}
