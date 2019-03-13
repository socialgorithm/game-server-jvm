package org.socialgorithm

interface Game {
  fun onPlayerMessage(player: Player, payload: Any)
}
