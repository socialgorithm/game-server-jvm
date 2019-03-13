package org.socialgorithm

data class GameInfoMessage(val name: String)

data class StartGameMessage(val players: List<Player>)

data class PlayerToGameMessage(
  val player: Player,
  val payload: Any
)

typealias GameToPlayerMessage = PlayerToGameMessage

data class GameUpdatedMessage(
  val payload: Any
)

data class GameEndedMessage(
  val winner: Player?,
  val tie: Boolean,
  val duration: Long,
  val payload: Any,
  val message: String
)
