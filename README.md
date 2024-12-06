# grug-toys

This is the first Minecraft mod written in grug, and serves as a testing ground.

See [my blog post](https://mynameistrez.github.io/2024/02/29/creating-the-perfect-modding-language.html) for an introduction to the grug modding language.

The mod is written for [Minecraft Forge - MC 1.20.6](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.6.html), specifically the recommended `forge-1.20.6-50.1.0-mdk.zip` version.

## Building this mod

Make sure to be on Linux, and to use VS Code.

- Run `./gradlew genVSCodeRuns` to generate `launch.json` and `tasks.json`, which allow you to test the mod by launching the game.

- Select the `runClient` launch config in the `Run and Debug` tab, and press `F5` to boot the game.

- See the [Getting Started with Forge](https://docs.minecraftforge.net/en/latest/gettingstarted/) page from Forge's website for more information.
