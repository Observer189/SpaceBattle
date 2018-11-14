package com.mygdx.game.control;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.requests.BattleInfo;
import com.mygdx.game.view.PreparingToBattle;

/**
 * Created by Sash on 30.10.2018.
 */

public class ServerListener extends Listener {
    public void connected(Connection c)
    {

    }
    public void received(Connection c, Object p) {
        if(p instanceof BattleInfo)
        {
            BattleInfo info=(BattleInfo) p;
            System.out.println("Ответ от сервера:"+info.getMessage());

            PreparingToBattle.messageReceived=true;
        }
    }
}
