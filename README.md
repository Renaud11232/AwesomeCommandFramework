# SubCommands Framework

SubCommands Framework is a Bukkit framework that can be used in any Bukkit plugin using commands and sub commands to
simplify the making of these sub commands and their autocompletion.

### *SubCommands Framework Goals*
  * Provide a clear and easy way to defin plugin commands and sub commands
  * Provide a simple way to manage command (and sub command) autocompletion
  * Make commands and sub commands easy to maintain, or add

### *How to use*

##### Commands / Sub commands

For this first example, let's say you'd like to create a command "mycommand" that has 2 sub commands "com1" and "com2".
Also you'd like "com2" to have 2 sub commands "foo" and "bar".
Each of those will just send messages for the example.

```
import be.renaud11232.plugins.awesomecommands.old.ComplexCommandExecutor;
import be.renaud11232.plugins.awesomecommands.old.SubCommandExecutor;
...
getCommand("mycommand").setExecutor(new ComplexCommandExecutor(ComplexCommandExecutor.DEFAULT_EXECUTOR,
        new SubCommand("com1", (commandSender, command, s, strings) -> {
            commandSender.sendMessage("You typed /mycommand com1");
            return true;
        }),
        new SubCommand("com2", new ComplexCommandExecutor(ComplexCommandExecutor.NO_EXECUTOR,
                new SubCommand("foo", (commandSender, command, s, strings) -> {
                    commandSender.sendMessage("You typed /mycommand com2 foo " + strings[0]);
                    return true;
                }),
                new SubCommand("bar", (commandSender, command, s, strings) -> {
                    commandSender.sendMessage("You typed /mycommand com2 bar");
                    return true;
                })
        ))
));
...
```
This will create such a command.
Notice the `ComplexCommandExecutor.DEFAULT_EXECUTOR` and `ComplexCommandExecutor.NO_EXECUTOR`.
They both do nothing but the first one always "fails" when the second one always "succeeds".

Which means that in this case using `/mycommand` will display a message showing you how to use the command when
`/mycommand com2` will not.

##### Command completion

Let's say that now you want to add completion for the previous command.
This will be very similar to the previous example.
```
import be.renaud11232.plugins.awesomecommands.old.ComplexTabCompleter;
import be.renaud11232.plugins.awesomecommands.old.SubTab;
...
getCommand("mycommand").setTabCompleter(new ComplexTabCompleter(ComplexTabCompleter.DEFAULT_COMPLETER,
        new SubTab("com1", ComplexTabCompleter.DEFAULT_COMPLETER),
        new SubTab("com2", new ComplexTabCompleter(ComplexTabCompleter.NO_TAB_COMPLETER,
                new SubTab("foor", ComplexTabCompleter.DEFAULT_COMPLETER),
                new SubTab("bar", ComplexTabCompleter.DEFAULT_COMPLETER)
        ))
));
...
```
And you're done !

Notice as for the commands the `ComplexTabCompleter.DEFAULT_COMPLETER` and `ComplexTabCompleter.NO_TAB_COMPLETER`.
The first one is equivalent to the default tab completer : the `onTabComplete` method always returns `null` so Bukkit
completes that with the connected player list.
The second one avoids any completion, so you can press TAB as much as you want, it wont complete anything.

So in this example, pressing tab after typing `/mycommand` will complete with the online players when doing the same
with `/mycommand com2` will complete with `foo` or `bar` until you typed something that does not match those 2 SubTabs.

#### Command arguments

As you can see in the first example, one of those commands displays a command argument (without bound verification).
This will actually show `/mycommand com2 foo` followed by the next token.
Each SubCommand executor works as it was a full part command with its arguments array starting at `0`.

The same logic appears for the command completion.

### *API Documentation*
  * [JavaDoc](http://doc.renaud11232.tk/SubCommandsFramework/)


### *Download*
  * [Click here](http://build.renaud11232.tk/SubCommandsFramework/)

You can choose which version you want and if you want it with build in dependencies or not

### *License*

SubCommands Framework is released under the [MIT](https://opensource.org/licenses/MIT) license
```
Copyright (c) 2016 R. Gaspard

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