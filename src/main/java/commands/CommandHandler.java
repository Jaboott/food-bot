package commands;

import database.DatabaseManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class CommandHandler extends ListenerAdapter {

    DatabaseManager databaseManager;

    public CommandHandler(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "echo":
                event.reply(event.getOption("message").getAsString()).queue();
                break;
            case "add":
                try {
                    String restaurant = event.getOption("restaurant").getAsString();
                    databaseManager.addRestaurant(restaurant);
                    event.reply(restaurant + " added").queue();
                } catch (SQLException e) {
                    event.reply( "Failed to add restaurant").queue();
                }
                break;
            case "random":
                event.reply("To be added").queue();
        }
    }

}
