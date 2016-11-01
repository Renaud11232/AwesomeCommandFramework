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

import java.util.HashMap;
import java.util.Map;

public abstract class ComplexElement<T> {

    private String permission;
    private T element;
    private Map<String, T> subElements;

    public ComplexElement(SubElement<T>... subElements){
        this((String) null, null, subElements);
    }

    public ComplexElement(String permission, SubElement<T>... subElements){
        this(permission, null, subElements);
    }

    public ComplexElement(Permission permission, SubElement<T>... subElements){
        this(permission, null, subElements);
    }

    public ComplexElement(T executor, SubElement<T>... subElements){
        this((String) null, executor, subElements);
    }

    public ComplexElement(String permission, T executor, SubElement<T>... subElements){
        this.subElements = new HashMap<>();
        setPermission(permission);
        set(executor);
        addSubElements(subElements);
    }

    public ComplexElement(Permission permission, T executor, SubElement<T>... subElements){
        this((String) null, executor, subElements);
        setPermission(permission);
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

    public T get(){
        return element;
    }

    public void set(T element) {
        this.element = transformElement(element);
    }

    protected abstract T transformElement(T element);

    protected Map<String, T> getSubElements(){
        return subElements;
    }

    public void removeSubElement(SubElement<T> subElement){
        subElements.remove(subElement.getName());
        subElement.getAliases().forEach(subElements::remove);
    }
    public void addSubElement(SubElement<T> subElement) {
        subElements.put(subElement.getName(), subElement.get());
        subElement.getAliases().forEach(alias -> {
            subElements.put(alias, subElement.get());
        });
    }
    public void addSubElements(SubElement<T>... subElements) {
        for (SubElement<T> sub : subElements) {
            addSubElement(sub);
        }
    }
}
