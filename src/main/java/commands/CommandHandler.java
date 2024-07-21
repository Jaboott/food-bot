package commands;

import database.DatabaseManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
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
                    String restaurant = event.getOption("restaurant").getAsString();
                    databaseManager.addRestaurant(restaurant);
                    event.reply(restaurant + " added").queue();
                } catch (SQLException e) {
                    event.reply( "Failed to add restaurant").queue();
                }
                break;
            case "random":
                try {
                    String restaurant = databaseManager.getRestaurant();
                    event.reply(responseRandomizer(restaurant)).queue();
                } catch (SQLException e) {
                    event.reply( "Failed to retrieve restaurant").queue();
                }
                break;
        }
    }

    private String responseRandomizer(String restaurant) {
        String response = GET_RESTAURANT_RESPONSE.get(random.nextInt(GET_RESTAURANT_RESPONSE.size()-1));
        response = String.format(response, restaurant);
        return response;
    }

}
