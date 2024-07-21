package commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class CommandManager {

    private JDA jda;
    public CommandManager(JDA jda) {
        this.jda = jda;
    }

    public void initCommands() {
        jda.updateCommands().addCommands(
                Commands.slash("echo", "Repeats messages back to you.")
                        .addOption(OptionType.STRING, "message", "The message to repeat."),
                Commands.slash("add", "Add a restaurant to the bot.")
                        .addOption(OptionType.STRING, "restaurant", "The name of the restaurant."),
                Commands.slash("random", "Chooses a random restaurant for you.")
        ).queue();

        System.out.println("Finish loading commands");
    }
}
