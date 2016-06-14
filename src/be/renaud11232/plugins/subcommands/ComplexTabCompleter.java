package be.renaud11232.plugins.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class ComplexTabCompleter implements TabCompleter {

    private Map<String, TabCompleter> subCompleters;

    public static final TabCompleter DEFAULT_COMPLETER = new TabCompleter() {
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            /*List<String> completion = new LinkedList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (strings.length == 0 || player.getName().startsWith(strings[strings.length - 1])) {
                    completion.add(player.getName());
                }
            }
            return completion;*/
            return null;
        }
    };
    public static final TabCompleter NO_TAB_COMPLETER = new TabCompleter() {
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            return Collections.emptyList();
        }
    };

    public ComplexTabCompleter() {
        subCompleters = new HashMap<>();
    }

    public ComplexTabCompleter(SubTab... subTabs) {
        this();
        addSubTabs(subTabs);
    }

    public void addSubTab(SubTab subTab) {
        subCompleters.put(subTab.getName(), subTab.getCompleter());
        for (String alias : subTab.getAliases()) {
            subCompleters.put(alias, subTab.getCompleter());
        }
    }

    public void addSubTabs(SubTab... subTabs) {
        for (SubTab subTab : subTabs) {
            addSubTab(subTab);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            List<String> completion = new LinkedList<>();
            if (subCompleters.containsKey(strings[0])) {
                return subCompleters.get(strings[0]).onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            } else {
                if(strings.length == 1) {
                    for (String subCommand : subCompleters.keySet()) {
                        if (subCommand.startsWith(strings[0])) {
                            completion.add(subCommand);
                        }
                    }
                }
                return completion;
            }
        } else {
            return null;
        }
    }
}
