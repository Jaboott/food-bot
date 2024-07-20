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
                        .addOption(OptionType.STRING, "message", "The message to repeat.")
        ).queue();
    }
}
