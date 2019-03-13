# Java/JVM Game Server

Creates a socket server (default port 5433) that serves games. To use this server, you provide functions that will be called on game or player actions, and call functions to communicate with players or to broadcast game state to spectators.

## Integrating

Install the dependency:

Gradle (see [Bintray](https://bintray.com/socialgorithm/org.socialgorithm/game-server) for others): 

```
repositories {
  jcenter()
}

dependencies {
  implementation 'org.socialgorithm:game-server:${version}'`
}
```

Start the game server and supply a new game function:

```
  new GameServer(gameInfoMessage, newGameFunction);
```

The new game function must accept game start parameters and can take an output channel on which to communicate with players/spectators. It must return an implementation of the `Game` interface (i.e. must implement callback to listen for player communication).

```
  newGameFunction(GameStartMessage gameStartMessage, GameOutputChannel outputChannel): Game {
    logger.debug("Started new game");
    return new MyGame(gameStartMessage.players, outputChannel);
  }
```

In your game, you can then use `GameOutputChannel` to communicate with players or spectators (e.g. the Tournament Server). 

## Contributing

The rest of this guide is for contributors.

### Publishing to BinTray/Maven

You must have local.properties in your repo directory, containing:

```
bintray.user=<USERNAME>
bintray.apikey=<API-KEY>
```

Then run

```
./gradlew build bintrayUpload
```
