# grug mod loader for Minecraft

[![Coverage](.github/badges/jacoco.svg)](https://github.com/MyNameIsTrez/grug-toys/actions/workflows/build.yml)
[![Branches](.github/badges/branches.svg)](https://github.com/MyNameIsTrez/grug-toys/actions/workflows/build.yml)

This is the grug mod loader for Minecraft.

See [my blog post](https://mynameistrez.github.io/2024/02/29/creating-the-perfect-modding-language.html) for an introduction to the grug modding language.

This mod currently only works in Minecraft 1.20.6, for Minecraft Forge [50.1.0](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.6.html).

## Building this mod

You must be on Linux, and are recommended to use VS Code.

See [ubuntu.com its guide](https://ubuntu.com/tutorials/install-jre) for installing the latest version of Java.

1. Run `git submodule update --init` to clone the subrepositories.
2. Press `Ctrl+Shift+B` to open the task menu, and select `Recompile`.
3. Run `./gradlew runClient` to initialize the `run/` directory, and launch the game.
4. From now on you can use `F5` to launch the game.

See the [Getting Started with Forge](https://docs.minecraftforge.net/en/latest/gettingstarted/) page from Forge's website for more information.

If you get `Exception in thread "main" java.lang.reflect.InvocationTargetException`, with `Caused by: com.electronwill.nightconfig.core.io.ParsingException: Invalid bare key: ${mod_id}` when using `F5` to launch the game, you'll need to use `./gradlew runClient` instead. (Make sure to use `cd ..` if you are in the `run` directory.)

## Running the tests

Run `./gradlew jacocoTestReport`.
