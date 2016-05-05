package com.co324.whistgame;

import java.util.ArrayList;

public class HandleWhist extends Thread{
	
	private String players[] = {"playerOne","playerTwo","playerThree","playerFour"};
	private int[] trickCards = new int[4];
	private int[] trickWins = new int[4];
	private int triumph;
	private int startedPlayer;
	private int trickWinPlayer;
	private int currentPlayingPlayer;
	private SendingMessage[] playerMessages = new SendingMessage[4];
	
	private AccessCheck checkConnects = AccessCheck.ZEROCONNECTED;
	private Deck deck;
	
	public HandleWhist(){
		deck = new Deck();
		startedPlayer = 0;
		currentPlayingPlayer = 0;
	}
	
	
	
	public void run(){
		System.out.println("start Handle");
		checkAllConnected();
		System.out.println("shuffling....");
		deck.shuffleDeck();
		deck.distributeDeck();
		System.out.println("deck distributed....");
		tempMethodToDistributeCards();
	}
	
	
	//check whether four players connected
	private void checkAllConnected(){
		boolean set1 = false;
		boolean set2 = false;
		boolean set3 = false;
		boolean set4 = false;
		
		
		while(true){
			if(BackBone.counter==1 && set1==false){
				checkConnects = checkConnects.onConnect(players);
				set1=true;
			}
			else if(BackBone.counter==2 && set2==false){
				checkConnects = checkConnects.onConnect(players);
				set2=true;
			}
			else if(BackBone.counter==3 && set3==false){
				checkConnects = checkConnects.onConnect(players);
				set3=true;
			}
			else if(BackBone.counter==4 && set4==false){
				checkConnects = checkConnects.onConnect(players);
				set4=true;
				break;
			}
			
			//break operaion
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("All connected....");
		return;
	}
	
	
	//temporary hard coded
	private void tempMethodToDistributeCards(){
		for(int i=0;i<4;i++){
			playerMessages[i]=new SendingMessage(this.deck,deck.getPlayersHand()[i],new String[4],
					true,true,"PlayerOne Chance to play",false,false,0);
			System.out.println(playerMessages[i].getJSONString());
			BackBone.sendMessage(BackBone.playerConnections.get(players[i]), playerMessages[i].getJSONString());
		}
	}
	
	///////////////////////////////////////////////////////////////////
								//Game logic//
	//////////////////////////////////////////////////////////////////
	
	
	
	
}
