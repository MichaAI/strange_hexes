package StrangeHexes;

import arc.*;
import arc.util.*;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.net.Administration.*;
import mindustry.world.blocks.storage.*;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static StrangeHexes.DiscordIntegration.*;

public class StrangeHexes extends Plugin {
    public static long channel = 1246806416271081502L;
    public static final String[] username = {"a"};

    //called when game initializes
    @Override
    public void init() {
        ConfigLoader.load(); //Должен загрузится первым
        DiscordIntegration.connect();

        //listen for a block selection event
        Events.on(BuildSelectEvent.class, event -> {
            if (!event.breaking && event.builder != null && event.builder.buildPlan() != null && event.builder.buildPlan().block == Blocks.thoriumReactor && event.builder.isPlayer()) {
                //player is the unit controller
                Player player = event.builder.getPlayer();

                //send a message to everyone saying that this player has begun building a reactor
                Call.sendMessage("[scarlet]ALERT![] " + player.name + " has begun building a reactor at " + event.tile.x + ", " + event.tile.y);
            }
        });

        Events.on(PlayerChatEvent.class, event -> {
            if (event.message.startsWith("/")) {
                return;
            }
            sendMessageWebhook(webhookId, "`" + event.message
                            .replace("`", "")
                            .replaceAll("\\[.*?\\]", "") + " `",
                    event.player.name
                            .replaceAll("\\[.*?\\]", ""));
        });

        Events.on(PlayerConnect.class, event -> {
            sendMessageWebhookWithEmbed(webhookId, "Server",
                    EmbedCreateSpec.builder()
                            .color(Color.GREEN)
                            .description("`" + event.player.name
                                    .replace("`", "")
                                    .replaceAll("\\[.*?\\]", "") + "` join to server.")
                            .build());
        });

        Events.on(PlayerLeave.class, event -> {
            sendMessageWebhookWithEmbed(webhookId, "Server",
                    EmbedCreateSpec.builder()
                            .color(Color.RUBY)
                            .description("`" + event.player.name
                                    .replace("`", "").replaceAll("\\[.*?\\]", "") + "` left from server.")
                            .build());
        });

        Events.on(ServerLoadEvent.class, event -> {
            sendMessageWebhookWithEmbed(webhookId, "Server",
                    EmbedCreateSpec.builder()
                            .color(Color.CYAN)
                            .description("Server Started!")
                            .build());
        });


        //add a chat filter that changes the contents of all messages
        //in this case, all instances of "heck" are censored
        Vars.netServer.admins.addChatFilter((player, text) -> text.replace("heck", "h*ck"));

        //add an action filter for preventing players from doing certain things
        Vars.netServer.admins.addActionFilter(action -> {
            //random example: prevent blast compound depositing
            if (action.type == ActionType.depositItem && action.item == Items.blastCompound && action.tile.block() instanceof CoreBlock) {
                action.player.sendMessage("Example action filter: Prevents players from depositing blast compound into the core.");
                return false;
            }
            return true;
        });

        gateway.on(MessageCreateEvent.class, event -> {
            Message message = event.getMessage();
            if (message.getChannelId().asLong() == channel) {
                message.getAuthorAsMember().subscribe(member -> {
                    String content = message.getContent();
                    Pattern pattern = Pattern.compile("<@(!?\\d+)>");
                    Matcher matcher = pattern.matcher(content);

                    while (matcher.find()) {
                        String userId = matcher.group(1);
                        gateway.getUserById(Snowflake.of(userId)).subscribe(other -> {
                            username[0] = other.getUsername();
                        });

                        Log.info(username[0]);
                        content = content.replace("<@" + matcher.group(1) + ">", "[olive][ [white]@" + username[0] + " [] ][]");
                    }

                    Call.sendMessage("[olive][ [#7289da]" + Iconc.discord + " DISCORD []][] " + member.getDisplayName() + ": " + content);
                }); //TODO понять что за хуйню я только что написал
            }
            return Mono.empty();
        }).subscribe();
    }

    //register commands that run on the server
    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("reactors", "List all thorium reactors in the map.", args -> {
            for (int x = 0; x < Vars.world.width(); x++) {
                for (int y = 0; y < Vars.world.height(); y++) {
                    //loop through and log all found reactors
                    //make sure to only log reactor centers
                    if (Vars.world.tile(x, y).block() == Blocks.thoriumReactor && Vars.world.tile(x, y).isCenter()) {
                        Log.info("Reactor at @, @", x, y);
                    }
                }
            }
        });
    }

    //register commands that player can invoke in-game
    @Override
    public void registerClientCommands(CommandHandler handler) {

        //register a simple reply command
        handler.<Player>register("reply", "<text...>", "A simple ping command that echoes a player's text.", (args, player) -> {
            player.sendMessage("You said: [accent] " + args[0]);
        });

        //register a whisper command which can be used to send other players messages
        handler.<Player>register("w", "<player> <text...>", "Whisper text to another player.", (args, player) -> {
            //find player by name
            Player other = Groups.player.find(p -> p.name.equalsIgnoreCase(args[0]));

            //give error message with scarlet-colored text if player isn't found
            if (other == null) {
                player.sendMessage("[scarlet]No player by that name found!");
                return;
            }

            //send the other player a message, using [lightgray] for gray text color and [] to reset color
            other.sendMessage("[lightgray](whisper) " + player.name + ":[] " + args[1]);
        });

        handler.<Player>register("discord", "", "Send link to discord server", (args, player) -> {
            Call.openURI("https://discord.gg/vY8E35XgTg");
        });
    }
}
