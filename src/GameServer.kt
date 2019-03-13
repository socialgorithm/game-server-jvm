import ServerOptions;
import GameInfo;
import GameSocketMessage;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

public class GameServer(
    val gameInfo: GameInfo, 
    val newGameFn: NewGameFn, 
    val serverOptions: ServerOptions) {

    init {
        val config = Configuration();
        config.setHostname("localhost");
        config.setPort(serverOptions.port);

        val server = SocketIOServer(config);

        server.addConnectListener({ client -> 
            client.sendEvent(GameSocketMessage.GAME_INFO.toString(), gameInfo);

            server.addEventListener(
                GameSocketMessage.START_GAME, 
                StartGameMessage.class, 
                
            }

        });

    }

    val startGameHandler = DataListener<StartGameMessage>() {

                    @Override
                    public void onData(SocketIOClient client, String username, AckRequest ackRequest) {

                    String isEncryptedPassword = new KOTS_EmployeeManager().getKOTS_User(KOTS_EmployeeManager.kotsUserType.CLIENT, username)

                    if(isEncryptedPassword != null)
                    {
                        //send back ack with encrypted password
                        ackRequest.sendAckData(isEncryptedPassword);

                    }else{
                        //send back ack with no user string
                        ackRequest.sendAckData("no user");
                    }
                }

shareeditflag


            client.addEventListener(GameSocketMessage.START_GAME, { startGameMessage: StartGameMessage -> 
                val bindings = GameOutputBindings(
                    //sendPlayerMessage(),
                    //sendGameUpdate(),
                    //sendGameEnd(),
                )
                val game = newGameFn(startGameMessage.payload, bindings)
                
                client.addEventListener(GameSocketMessage.GAME__PLAYER, game.onPlayerMessage)
            });
        });
    }
}
