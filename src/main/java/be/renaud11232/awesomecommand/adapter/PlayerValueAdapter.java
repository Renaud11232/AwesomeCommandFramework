package be.renaud11232.awesomecommand.adapter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;

/**
 * The {@link PlayerValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Player} compatible {@link Type}.
 */
public class PlayerValueAdapter implements SingleArgumentValueAdapter<Player> {

    /**
     * Converts the provided {@link String} value to a {@link Player}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Player}
     * @see Bukkit#getPlayer(String)
     */
    @Override
    public Player apply(Type type, String value) {
        return Bukkit.getPlayer(value);
    }
}
