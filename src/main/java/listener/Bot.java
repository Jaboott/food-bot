package listener;

import commands.CommandHandler;
import commands.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot extends ListenerAdapter {
    public static void main(String[] args) {
        initBot("DISCORDAPITOKEN");
    }

    private static void initBot(String discordAPI) {
        JDA jda = JDABuilder.createDefault(discordAPI)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        CommandManager commandManager = new CommandManager(jda);
        commandManager.initCommands();
        jda.addEventListener(new Bot());
        jda.addEventListener(new CommandHandler());
    }

}