package CommandHandlers;

import Main.Main;
import cf.ystapi.jda.Handlers.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class Ping implements CommandHandler {
    @Override
    public String name() {
        return "ping";
    }

    @Override
    public String usage() {
        return "!ping";
    }

    @Override
    public String helpMessages() {
        return "Showing Gateway/Message ping";
    }

    @Override
    public void onCalled(MessageReceivedEvent event, String[] args, MessageChannel channel) {
        Main.logger.info("Ping Command Called");
        //Embed
        EmbedBuilder em = new EmbedBuilder().setTitle("Ping")
                .setColor(Color.YELLOW)
                .addField("Gateway Ping", "Loading...", true)
                .addField("Message Ping", "Loading...", true)
                .addField("Websocket Ping", "Loading...", true);

        //Started as ms
        long started = System.currentTimeMillis();

        channel.sendMessageEmbeds(em.build()).queue(s -> {
            long time = System.currentTimeMillis()-started;
            EmbedBuilder em1 = new EmbedBuilder().setTitle("Ping")
                    .setColor(Color.YELLOW)
                    .addField("Gateway Ping", s.getJDA().getGatewayPing()+"ms", true)
                    .addField("Message Ping", time+"ms", true)
                    .addField("Websocket Ping", ping()+"ms", true);
            s.editMessageEmbeds(em1.build()).queue();
        });
    }

    public int ping(){
        try {
            long timeToRespond = 0;
            //Discord Server
            InetSocketAddress socketAddress = new InetSocketAddress("discord.com", 80);

            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(true);

            Date start = new Date();
            if (sc.connect(socketAddress)) {
                Date stop = new Date();
                timeToRespond = (stop.getTime() - start.getTime());
            }
            return (int) timeToRespond;
        } catch (IOException ex) {
            Main.logger.error(ex.getMessage());
            return 0;
        }
    }
}
