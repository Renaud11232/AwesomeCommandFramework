package be.renaud11232.awesomecommand.adapter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PlayerValueAdapter implements ArgumentValueAdapter<Player> {
    @Override
    public Player apply(String value, Field field) {
        return Bukkit.getPlayer(value);
    }
}
