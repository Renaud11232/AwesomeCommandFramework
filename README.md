# AwesomeCommand Framework

AwesomeCommand Framework is a Bukkit/Spigot framework that can be used to build any Bukkit plugin needing to use commands and sub commands.

### *AwesomeCommand Framework Goals*
  * Provide a clear and easy way to define plugin commands and sub commands
  * Provide a simple way to manage command (and sub command) autocompletion
  * Make commands and sub commands easy to maintain, or add
  * Making the implementation of these sub commands as close as the regular commands.

### *How to use*

This framework gives a way to create sub commands the same way regular commands are created using the original Bukkit classes.

##### plugin.yml


First, you'll need to define your sub commands in your `plugin.yml`file.
To define sub commands simply add a `subcommands` object. A sub command can also have sub commands.

Sample file:

```yaml
main: com.example.plugin.Main
name: AwesomePlugin
version: 1.0
commands:
    foo:
        description: Foo desc
        usage: /<command>
        permission: complex.foo
        subcommands:
            foobar:
                description: Foobar desc
                permission: complex.foo.bar
                aliases:
                    - fb
                    - foba
    bar:
        description: Bar desc
        usage: /<command>
        permission: complex.bar
        aliases: aliasbar

```

Each sub command have the same attributes than a regular command.

##### CommandExecutor and TabCompleter

Once you have defined your commands in the `plugin.yml`file, you can attach to each of these commands `CommandExecutor`'s and `TabCompleter`'s using
the `getAwesomeCommand(String name)`method which works like the usual `getCommand(String name)`.

For instance
```java
public class MyAwesomePlugin extends AwesomePlugin {
    @Override
    public void onEnable() {
        getAwesomeCommand("foo").setExecutor((commandSender, command, s, strings) -> {
            commandSender.sendMessage("foo");
            return true;
        });
        getAwesomeCommand("foo.foobar").setExecutor((commandSender, command, s, strings) -> {
            commandSender.sendMessage("foo foobar");
            return true;
        });
        getAwesomeCommand("bar").setExecutor((commandSender, command, s, strings) -> {
            commandSender.sendMessage("bar");
            return true;
        });
    }
}
```
Notice the `getAwesomeCommand("foo.foobar")`. When referencing sub commands you need to use `.` to separate parent commands from their sub commands.

The exact same thing can be done to add a `TabCompleter` to a command or sub command, just use `setTabCompleter(TabCompleter completer)` instead of `setExecutor(CommandExecutor executor)`.

##### Constants

There are 4 basic executors and completers that might be useful :
  * `AwesomeCommand.DEFAULT_EXECUTOR` : `CommandExecutor`that has the same behavior as the default one, which always returns false
  * `AwesomeCommand.NO_EXECUTOR`: `CommandExecutor`which always returns true
  * `AwesomeCommand.DEFAULT_COMPLETER`: `TabCompleter`that has the same behavior as the default one, which always returns null
  * `AwesomeCommand.NO_COMPLETER`: `TabCompleter` which always returns an empty list


### *License*

AwesomeCommand Framework is released under the [MIT](https://opensource.org/licenses/MIT) license
```
Copyright (c) 2018 R. Gaspard

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```