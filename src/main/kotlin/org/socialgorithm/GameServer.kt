package org.socialgorithm

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import java.util.concurrent.ConcurrentHashMap

class GameServer(
  gameInfoMessage: GameInfoMessage,
  newGameFn: (StartGameMessage, GameOutputChannel) -> Game,
  serverOptions: ServerOptions
) {

  private val games = ConcurrentHashMap<SocketIOClient, Game>()

  init {
    val config = Configuration()
    config.hostname = "localhost"
    config.port = serverOptions.port

    val server = SocketIOServer(config)

    server.addConnectListener { client ->
      client.sendEvent(GameSocketMessage.GAME_INFO, gameInfoMessage)
    }

    server.addEventListener(GameSocketMessage.START_GAME, StartGameMessage::class.java)
    { client, startGameMessage, _ ->
      val game = newGameFn(
        startGameMessage,
        GameOutputChannel(
          sendPlayerMessage = { player: Player, payload: Any ->
            client.sendEvent(GameSocketMessage.GAME__PLAYER, GameToPlayerMessage(player, payload))
          },
          sendGameUpdate = { payload: Any ->
            client.sendEvent(GameSocketMessage.GAME_UPDATED, GameUpdatedMessage(payload))
          },
          sendGameEnd = { message: GameEndedMessage ->
            client.sendEvent(GameSocketMessage.GAME_ENDED, message)
          }
        )
      )
      games[client] = game
    }

    server.addEventListener(GameSocketMessage.GAME__PLAYER, PlayerToGameMessage::class.java)
    { client, message, _ ->
      games[client]?.onPlayerMessage(message.player, message.payload)
    }

    server.start()
  }
}
