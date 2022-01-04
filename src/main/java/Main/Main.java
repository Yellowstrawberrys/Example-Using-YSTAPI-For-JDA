package Main;

import CommandHandlers.Ping;
import cf.ystapi.Logging.Logger;
import cf.ystapi.Logging.LoggingBuilder;
import cf.ystapi.jda.Objects.DiscordBot;
import cf.ystapi.jda.YSTBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {

    public static JDA jda = null;
    public static Logger logger = null;
    public static DiscordBot discordBot = null;

    public static void main(String[] args) throws LoginException, IOException {

        //Building Discord Bot
        JDABuilder builder = JDABuilder.createDefault(args[0]);

        builder.setActivity(Activity.playing("YST API | BASIC"));

        //Building JDA
        jda = builder.build();

        //Building YST Logger
        LoggingBuilder loggingBuilder = new LoggingBuilder();

        //Setting Format (Year/Month/Day Hour:Minutes:Seconds [Level] Message)
        loggingBuilder.setFormat("%YY/%MM/%DD %HH:%mm:%SS [%LL] %MSG");

        //Set Web Logger to Enable
        loggingBuilder.useWebLogger(true);

        //Setting Web Logger Port (localhost:8080)
        loggingBuilder.setWebLoggerPort(8080);

        //Setting Web Logger Location (localhost:8080/logging)
        loggingBuilder.setWebLoggerPath("/logging");

        //Setting Name (localhost:8080/logging?name=BOT)
        logger = loggingBuilder.build("BOT");

        YSTBuilder ystBuilder = new YSTBuilder(jda);

        //Setting Help Command(s)
        ystBuilder.setHelpCommands("help");

        //Setting Prefix
        ystBuilder.setPrefix("!");

        //Adding Command(s)
        ystBuilder.addCommand(new Ping());

        //IgnoreCase
        ystBuilder.IgnoreCase(true);

        //Building Discord Bot
        discordBot = ystBuilder.build();
    }
}
