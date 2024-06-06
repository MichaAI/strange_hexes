package StrangeHexes;

import StrangeHexes.WTFMapGenerator.LoadHexes;
import arc.Events;
import arc.graphics.Colors;
import arc.util.CommandHandler;
import arc.util.Log;
import arc.util.Strings;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.EventType.*;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Iconc;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Administration.ActionType;
import mindustry.world.blocks.storage.CoreBlock;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static StrangeHexes.DiscordIntegration.*;

public class StrangeHexes extends Plugin {
    public static final String[] username = {"a"};
    public static long channel = 1246806416271081502L;
    public static String colors = Colors.getColors().orderedKeys().toString("|"); // Представляет из мебя перечисление цветов для использование в regex (white|gray|...|)

    //called when game initializes
    @Override
    public void init() {
        ConfigLoader.load(); //Должен загрузится первым
        DiscordIntegration.connect();
        LoadHexes.init();

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
            sendMessageWebhook(webhookId, "`" + Strings.stripColors(event.message.replace("`", "")) + " `",
                    Strings.stripColors(event.player.name.replace("`", "")),
                    "https://api.dicebear.com/8.x/shapes/png?seed="
                            + Strings.stripColors(event.player.name.replace("`", ""))
                            + "&shape1=ellipseFilled,polygonFilled,rectangleFilled,line,polygon,rectangle,ellipse&shape2=ellipseFilled,line,polygonFilled,rectangleFilled,rectangle,polygon,ellipse");
            ;
        });

        Events.on(PlayerConnect.class, event -> {
            sendMessageWebhookWithEmbed(webhookId, "Server",
                    EmbedCreateSpec.builder()
                            .color(Color.of(0xB2EC5D))
                            .author(Strings.stripColors(event.player.name.replace("`", ""))
                                    + " join to server.", "", "https://robohash.org/"
                                    + Strings.stripColors(event.player.name) + "?set=set3")
                            .build());
        });

        Events.on(PlayerLeave.class, event -> {
            sendMessageWebhookWithEmbed(webhookId, "Server",
                    EmbedCreateSpec.builder()
                            .color(Color.of(0xEB4C42))
                            .author(Strings.stripColors(event.player.name.replace("`", ""))
                                    + " left from server.", "", "https://robohash.org/"
                                    + Strings.stripColors(event.player.name) + "?set=set3")
                            .build());
        });

        Events.on(ServerLoadEvent.class, event -> {
            sendMessageWebhookWithEmbed(webhookId, "Server",
                    EmbedCreateSpec.builder()
                            .color(Color.of(0x0047AB))
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
                    if (member.isBot()) return;
                    String content = message.getContent();
                    Pattern pattern = Pattern.compile("<@(!?\\d+)>");
                    Matcher matcher = pattern.matcher(content);

                    while (matcher.find()) {
                        String userId = matcher.group(1);
                        gateway.getUserById(Snowflake.of(userId)).subscribe(other -> {
                            username[0] = other.getUsername();
                        });
                        content = content.replace("<@" + matcher.group(1) + ">", "[olive][ [white]@" + username[0] + " [] ][]");
                    }
                    Log.info("[ DISCORD ]" + member.getDisplayName() + ": " + content, Color.CYAN);
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
