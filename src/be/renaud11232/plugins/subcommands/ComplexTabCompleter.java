package be.renaud11232.plugins.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class ComplexTabCompleter implements TabCompleter {

    private Map<String, TabCompleter> subCompleters;
    private TabCompleter completer;

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
        List<String> completion = new LinkedList<>();
        if (strings.length == 0) {
            completion.addAll(subCompleters.keySet());
        } else {
            if (subCompleters.containsKey(strings[0])) {
                return subCompleters.get(strings[0]).onTabComplete(commandSender, command, s, strings.length == 1 ? new String[0] : Arrays.copyOfRange(strings, 1, strings.length - 1));
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
        return completion;
    }
}
