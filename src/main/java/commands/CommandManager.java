package commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import restaurant.Cuisine;
import restaurant.GeneralLocation;

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
                        .addOption(OptionType.STRING, "restaurant", "The name of the restaurant.", true)
                        .addOptions(buildCuisine())
                        .addOptions(buildGeneralLocation()),
                Commands.slash("random", "Chooses a random restaurant for you."),
                Commands.slash("remove", "Remove specified restaurant for you.")
                        .addOption(OptionType.STRING, "restaurant", "The name of the restaurant.", true, true)
        ).queue();

        System.out.println("Finish loading commands");
    }

    private OptionData buildCuisine() {
        OptionData optionData = new OptionData(OptionType.STRING, "type", "The type of cuisine", true);
        for (Cuisine c : Cuisine.values()) {
            optionData.addChoice(c.getType() + " Cuisine", c.name());
        }
        return optionData;
    }

    private OptionData buildGeneralLocation() {
        OptionData optionData = new OptionData(OptionType.STRING, "location", "The general location of the restaurant", true);
        for (GeneralLocation l : GeneralLocation.values()) {
            optionData.addChoice(l.getLocation(), l.name());
        }
        return optionData;
    }
}
