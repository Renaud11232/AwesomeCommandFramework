package be.renaud11232.plugins.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class ComplexTabCompleter implements TabCompleter {

    private Map<String, TabCompleter> subCompleters;
    private TabCompleter completer;

    public static final TabCompleter DEFAULT_COMPLETER = new TabCompleter() {
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
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
        completer = DEFAULT_COMPLETER;
    }

    public ComplexTabCompleter(TabCompleter completer, SubTab... subTabs) {
        this();
        addSubTabs(subTabs);
        setCompleter(completer);
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

    public void setCompleter(TabCompleter completer){
        this.completer = completer == null ? DEFAULT_COMPLETER : completer;
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
                if(completion.isEmpty()){
                    return completer.onTabComplete(commandSender, command, s, strings);
                }else {
                    return completion;
                }
            }
        } else {
            return null;
        }
    }
}
