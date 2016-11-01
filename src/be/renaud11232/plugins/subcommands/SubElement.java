/*
 * Copyright (c) 2016 R. Gaspard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.renaud11232.plugins.subcommands;

import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SubElement<T> {

    private String name;
    private String permission;
    private T element;
    private List<String> aliases;

    public SubElement(String name, String...aliases){
        this(name, (String) null, null, aliases);
    }

    public SubElement(String name, String permission, String... aliases){
        this(name, permission, null, aliases);
    }

    public SubElement(String name, Permission permission, String... aliases){
        this(name, permission, null, aliases);
    }

    public SubElement(String name, T element, String... aliases){
        this(name, (String) null, element, aliases);
    }

    public SubElement(String name, Permission permission, T element, String... aliases){
        this(name, (String) null, element, aliases);
        setPermission(permission);
    }

    public SubElement(String name, String permission, T element, String... aliases){
        this.aliases = new ArrayList<>();
        setName(name);
        setPermission(permission);
        set(element);
        addAliases(aliases);
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        Objects.requireNonNull(name, "Could not set the " + getClass().getSimpleName() + " name, it was null");
        if(name.contains(" ")){
            throw new IllegalArgumentException("Could not set the " + getClass().getSimpleName() + " name, name contains a \" \" : \"" + name + "\"");
        }
        this.name = name;
    }

    public String getPermission(){
        return permission;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }

    public void setPermission(Permission permission){
        this.permission = permission == null ? null : permission.getName();
    }

    public T get() {
        return element;
    }

    public void set(T element){
        this.element = transformElement(element);
    }

    protected abstract T transformElement(T element);

    public List<String> getAliases() {
        return new ArrayList<>(aliases);
    }

    public void addAlias(String alias){
        Objects.requireNonNull(alias, "Could not add a " + getClass().getSimpleName() + " alias, alias was null");
        if(alias.contains(" ")) {
            throw new IllegalArgumentException("Could not add a " + getClass().getSimpleName() + " alias, alias contains a \" \" : \"" + alias + "\"");
        }
        aliases.add(alias);
    }

    public void addAliases(String... aliases){
        Objects.requireNonNull(aliases, "Could not add " + getClass().getSimpleName() + " aliases, aliases was null");
        for(String alias : aliases){
            addAliases(alias);
        }
    }

    public boolean removeAlias(String alias){
        return aliases.remove(alias);
    }

}
