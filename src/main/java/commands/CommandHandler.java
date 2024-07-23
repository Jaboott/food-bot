package commands;

import database.DatabaseManager;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.sqlite.SQLiteErrorCode;
import restaurant.Cuisine;
import restaurant.GeneralLocation;
import restaurant.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandHandler extends ListenerAdapter {

    private DatabaseManager databaseManager;
    private Random random;
    private final List<String> GET_RESTAURANT_RESPONSE = List.of(
            "Yay! Your culinary adventure awaits at **%s**. Bon appétit and enjoy every bite!",
            "Congratulations! You're off to **%s** for a delightful meal. Enjoy!",
            "Fantastic choice! **%s** is ready to serve you something delicious. Have a wonderful dining experience!",
            "Awesome! **%s** has been selected for you. Get ready for some great food and fun times!",
            "Woo-hoo! **%s** it is! Savor every moment and every bite. Enjoy!",
            "Hooray! **%s** will be your dining spot. Have a tasty and memorable meal!",
            "Get excited! **%s** is waiting for you with delicious dishes. Enjoy your meal!",
            "Lucky you! **%s** is your destination for a fantastic dining experience. Bon appétit!",
            "Perfect pick! Head to **%s** and treat yourself to an amazing meal. Enjoy!",
            "Time to eat! **%s** has been chosen just for you. Have a great time and enjoy the food!"
    );


    public CommandHandler(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        random = new Random();
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "echo":
                event.reply(event.getOption("message").getAsString()).queue();
                break;
            case "add":
                try {
                    event.deferReply().queue();
                    Restaurant restaurant = restaurantBuilder(event);
                    databaseManager.addRestaurant(restaurant);
                    event.getHook().sendMessage("**" + restaurant.getName() + "** added").queue();
                } catch (SQLException e) {

                    // Check if the error comes from failing uniqueness
                    if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                        event.getHook().sendMessage("The restaurant is already in the database").queue();
                    } else {
                        event.getHook().sendMessage("Failed to add restaurant").queue();
                    }
                }
                break;
            case "random":
                try {
                    event.deferReply().queue();
                    Restaurant restaurant = databaseManager.getRestaurant();
                    event.getHook().sendMessage(responseRandomizer(restaurant.getName())).queue();
                } catch (SQLException e) {
                    event.reply( "Failed to retrieve restaurant").queue();
                }
                break;
            case "remove":
                try {
                    event.deferReply().queue();
                    String name = event.getOption("restaurant").getAsString();
                    databaseManager.deleteRestaurant(name);
                    event.getHook().sendMessage("**" + name + "** have been removed from the database").queue();
                } catch (SQLException e) {
                    event.getHook().sendMessage("Failed to delete restaurant from the database").queue();
                }
                break;
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        switch(event.getName()) {
            case "remove":
                try {
                    List<Restaurant> restaurants;
                    String currentText = event.getFocusedOption().getValue();

                    // Skip searching if string is empty
                    if (currentText.isEmpty()) {
                        break;
                    }
                    restaurants = databaseManager.searchRestaurants(currentText);

                    List<Command.Choice> options = new ArrayList<>();
                    for (Restaurant restaurant : restaurants) {
                        options.add(new Command.Choice(restaurant.getName(), restaurant.getName()));
                    }

                    event.replyChoices(options).queue();
                } catch (SQLException e) {
                    System.out.printf("Failed to retrieve restaurants matching: %s%n", event.getFocusedOption().getValue());
                }
                break;
        }
    }

    private String responseRandomizer(String restaurant) {
        String response = GET_RESTAURANT_RESPONSE.get(random.nextInt(GET_RESTAURANT_RESPONSE.size()-1));
        response = String.format(response, restaurant);
        return response;
    }

    private Restaurant restaurantBuilder(SlashCommandInteractionEvent event) {
        // Builds a restaurant object based on event
        String name = event.getOption("restaurant").getAsString();
        Cuisine type = Cuisine.valueOf(event.getOption("type").getAsString().toUpperCase());
        GeneralLocation generalLocation = GeneralLocation.valueOf(event.getOption("location").getAsString().toUpperCase());
        return new Restaurant(name, type, generalLocation);
    }

}
