package org.socialgorithm

class GameOutputChannel(
  sendPlayerMessage: (player: Player, payload: Any) -> Unit,
  sendGameUpdate: (payload: Any) -> Unit,
  sendGameEnd: (message: GameEndedMessage) -> Unit
)
