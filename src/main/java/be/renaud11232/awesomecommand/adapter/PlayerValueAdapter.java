package be.renaud11232.awesomecommand.adapter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * The {@link PlayerValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Player} compatible type
 */
public class PlayerValueAdapter implements SingleArgumentValueAdapter<Player> {

    /**
     * Converts the provided {@link String} value to a {@link Player} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Player}
     * @see Bukkit#getPlayer(String)
     */
    @Override
    public Player apply(Type type, String value) {
        return Bukkit.getPlayer(value);
    }
}
