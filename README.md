# grug-toys

This is the first Minecraft mod written in grug, and serves as a testing ground.

See [my blog post](https://mynameistrez.github.io/2024/02/29/creating-the-perfect-modding-language.html) for an introduction to the grug modding language.

The mod is written for [Minecraft Forge - MC 1.21.4](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.21.4.html).

## Building this mod

Make sure to be on Linux, and to use VS Code.

- Run `./gradlew genVSCodeRuns` to generate `launch.json` and `tasks.json`, which allow you to test the mod by launching the game.

- `Ctrl`+`Shift`+`B`

- `./gradlew tasks`

- `./gradlew build`

Use the `runClient` launch task in VS Code to launch the game.

See the [Getting Started with Forge](https://docs.minecraftforge.net/en/latest/gettingstarted/) page from Forge's website for more information.
