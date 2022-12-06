# AwesomeCommand Framework [![Build Status](https://travis-ci.org/Renaud11232/AwesomeCommandFramework.svg?branch=master)](https://travis-ci.org/Renaud11232/AwesomeCommandFramework) [![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=UD54SHVYDV6NU&source=url)

AwesomeCommand Framework is a Bukkit/Spigot framework that can be used to build any Bukkit plugin needing to use
commands and sub commands.

### *AwesomeCommand Framework Goals*

* Provide a clear and easy way to define plugin commands and sub commands.
* Provide a simple way to manage command (and sub command) autocompletion.
* Make commands and sub commands easy to maintain, or add.
* Making the implementation of these sub commands as close as the regular commands.

### *How to use*

This framework gives a way to describe command using classes and annotations.

###### Javadoc :

The API documentation can be found [here](https://renaud11232.github.io/AwesomeCommandFramework)

###### Example :

The following example will create aplugin with one commands and two subcommands :

* cmd1
  * sub1
  * sub2

`plugin.yml` :

```yaml
#...
commands:
  cmd1:
    description: Command 1
    usage: /<command>
    permission: awesome.cmd1
#...
```

`AwesomePlugin.java` :

```java
public class MyAwesomePlugin extends AwesomePlugin {

  @Override
  public void onEnable() {
    initCommand(Command1.class);
  }

}
```

`Command1.java` :

```java

@AwesomeCommand(
        name = "cmd1",
        description = "Command 1",
        permission = "awesome.cmd1",
        subCommands = {
                Sub1.class,
                Sub2.class
        }
)
public class Command1 {
}

```

`Sub1.java` :

```java

@AwesomeCommand(
        name = "sub1",
        description = "Subcommand 1",
        permission = "awesome.cmd1.sub1"
)
public class Sub1 implements AwesomeCommandExecutor, AwesomeTabCompleter {

  @CommandSenderParameter
  private CommandSender commandSender;

  @Override
  public boolean execute() {
    commandSender.sendMessage("command 1 > subcommand 1");
    return true;
  }

  @Override
  public List<String> tabComplete() {
    return Collections.emptyList();
  }
}
```

`Sub2.java` :

```java

@AwesomeCommand(
        name = "sub2",
        description = "Subcommand 2",
        permission = "awesome.cmd1.sub2"
)
public class Sub2 implements AwesomeCommandExecutor, AwesomeTabCompleter {

  @CommandSenderParameter
  private CommandSender commandSender;

  @Override
  public boolean execute() {
    commandSender.sendMessage("command 1 > subcommand 2");
    return true;
  }

  @Override
  public List<String> tabComplete() {
    return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
  }
}

```