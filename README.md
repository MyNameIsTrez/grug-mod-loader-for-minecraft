# grug-toys

This is the first Minecraft mod written in grug, and serves as a testing ground.

See [my blog post](https://mynameistrez.github.io/2024/02/29/creating-the-perfect-modding-language.html) for an introduction to the grug modding language.

The mod is written for [Minecraft Forge - MC 1.20.6](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.6.html), specifically the recommended `forge-1.20.6-50.1.0-mdk.zip` version.

## Building this mod

Make sure to be on Linux, and to use VS Code.

1. Run `git submodule update --init` to clone the subrepositories.
2. Press `Ctrl+Shift+B` to open the task menu, and select `Recompile`.
3. Run `./gradlew runClient` to initialize the `run/` directory, and launch the game.
4. From now on you can use `F5` to launch the game.

See the [Getting Started with Forge](https://docs.minecraftforge.net/en/latest/gettingstarted/) page from Forge's website for more information.
