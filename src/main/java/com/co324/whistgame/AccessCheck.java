package com.co324.whistgame;

import java.util.ArrayList;

public enum AccessCheck {
	ZEROCONNECTED{
		AccessCheck onConnect(String[] players){
			System.out.println("One player connected");
			SendingMessage message = new SendingMessage(new Deck(),new ArrayList<Integer>(),new String[4],false,
					false,"One player connected, 3 more to connect....",false,false,0);
			BackBone.sendMessage(BackBone.playerConnections.get(players[0]), message.getJSONString());
			return ONECONNECTED;
		}
	},
	ONECONNECTED{
		AccessCheck onConnect(String[] players){
			System.out.println("TWO player connected");
			SendingMessage message = new SendingMessage(new Deck(),new ArrayList<Integer>(),new String[4],false,
					false,"Two players connected, 2 more to connect.....",false,false,0);
			for(int i=0;i<2;i++){
				BackBone.sendMessage(BackBone.playerConnections.get(players[i]), message.getJSONString());
			}
			return TWOCONNECTED;
		}
	},
	TWOCONNECTED{
		AccessCheck onConnect(String[] players){
			System.out.println("THREE player connected");
			SendingMessage message = new SendingMessage(new Deck(),new ArrayList<Integer>(),new String[4],false,
					false,"Three players connected, one more to connect.....",false,false,0);
			for(int i=0;i<3;i++){
				BackBone.sendMessage(BackBone.playerConnections.get(players[i]), message.getJSONString());
			}
			return THREECONNECTED;
		}
	},
	THREECONNECTED{
		AccessCheck onConnect(String[] players){
			System.out.println("FOUR player connected");
			SendingMessage message = new SendingMessage(new Deck(),new ArrayList<Integer>(),new String[4],false,
					false,"All players connected, lets start .......",false,false,0);
			for(int i=0;i<4;i++){
				BackBone.sendMessage(BackBone.playerConnections.get(players[i]), message.getJSONString());
			}
			return FOURCONNECTED;
		}
	},
	FOURCONNECTED{
		AccessCheck onConnect(String[] players){
			System.out.println("FOUR player connected");
			SendingMessage message = new SendingMessage(new Deck(),new ArrayList<Integer>(),new String[4],false,
					false,"All players connected, game is going.....",false,false,0);
			for(int i=0;i<4;i++){
				BackBone.sendMessage(BackBone.playerConnections.get(players[i]), message.getJSONString());
			}
			return FOURCONNECTED;
		}
	};
	
	abstract AccessCheck onConnect(String[] players);
	
}
