package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "echo":
                event.reply(event.getOption("message").getAsString()).queue();
                break;
            case "add":
                event.reply(event.getOption("restaurant").getAsString() + " added").queue();
                break;
            case "random":
                event.reply("To be added").queue();
        }
    }

}
