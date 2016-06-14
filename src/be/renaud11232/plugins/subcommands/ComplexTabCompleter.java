package be.renaud11232.plugins.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class ComplexTabCompleter implements TabCompleter {

    private Map<String, TabCompleter> subCompleters;
    private TabCompleter completer;

    public static final TabCompleter DEFAULT_TAB_COMPLETER = new TabCompleter() {
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            List<String> completion = new LinkedList<>();
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(strings.length == 0 || player.getName().startsWith(strings[strings.length - 1])) {
                        completion.add(player.getName());
                    }
                }
            return completion;
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
        completer = null;
    }

    public ComplexTabCompleter(TabCompleter completer, SubTab... subTabs) {
        this();
        setCompleter(completer);
        addSubTabs(subTabs);
    }

    public void setCompleter(TabCompleter completer) {
        this.completer = completer;
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
        /*List<String> completion = new LinkedList<>();
        if (strings.length == 0) {
            completion.addAll(subCompleters.keySet());
        } else {
            if (subCompleters.containsKey(strings[0])) {
                if(strings.length > 1) {
                    return subCompleters.get(strings[0]).onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                } else {
                    return completion;
                }
            } else {
                for (String str : subCompleters.keySet()) {
                    if (str.startsWith(strings[0])) {
                        completion.add(str);
                    }
                }
            }
        }
        if (completer != null) {
            completion.addAll(completer.onTabComplete(commandSender, command, s, strings));
        }
        return completion;*/
        return DEFAULT_TAB_COMPLETER.onTabComplete(commandSender, command, s, strings);
    }
}
