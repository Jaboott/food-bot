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
                        .addOptions(buildCuisine(true, "The type of cuisine"))
                        .addOptions(buildGeneralLocation(true, "The general location of the restaurant")),
                Commands.slash("random", "Chooses a random restaurant for you.")
                        .addOptions(buildCuisine(false, "Choose random restaurant with specific type of cuisine"))
                        .addOptions(buildGeneralLocation(false, "Choose random restaurant with specific location")),
                Commands.slash("remove", "Remove specified restaurant for you.")
                        .addOption(OptionType.STRING, "restaurant", "The name of the restaurant.", true, true)
        ).queue();

        System.out.println("Finish loading commands");
    }

    private OptionData buildCuisine(boolean isRequired, String description) {
        OptionData optionData = new OptionData(OptionType.STRING, "type", description, isRequired);
        // Building command options based on the different Enums under Cuisine
        for (Cuisine c : Cuisine.values()) {
            optionData.addChoice(c.getType() + " Cuisine", c.name());
        }
        return optionData;
    }

    private OptionData buildGeneralLocation(boolean isRequired, String description) {
        OptionData optionData = new OptionData(OptionType.STRING, "location", description, isRequired);
        // Building command options based on the different Enums under GeneralLocation
        for (GeneralLocation l : GeneralLocation.values()) {
            optionData.addChoice(l.getLocation(), l.name());
        }
        return optionData;
    }
}
