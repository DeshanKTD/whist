package com.co324.whistgame;

import java.util.ArrayList;
import java.util.HashMap;


public class HandleWhist extends Thread{
	public HashMap<Integer,String> playermap = new HashMap<Integer,String>();
	public HashMap<String,Integer> playermap2 = new HashMap<String,Integer>();
	
	private String players[] = {"playerOne","playerTwo","playerThree","playerFour"};
	private int[] trickCards = new int[4];
	private int[] trickWins = new int[4];
	private int startedPlayer;
	private int trickWinPlayer;
	private int currentPlayingPlayer;
	private SendingMessage[] playerMessages = new SendingMessage[4];
	private String currentlyPlayedCard;
	private String requestPlayer;
	
	private AccessCheck checkConnects = AccessCheck.ZEROCONNECTED;
	private Deck deck;
	
	public HandleWhist(){
		deck = new Deck();
		startedPlayer = 0;
		currentPlayingPlayer = 0;
		currentlyPlayedCard = null;
		requestPlayer = null;
		for(int i=0;i<4;i++){
			playermap.put(i, players[i]);
			playermap2.put(players[i], i);
		}
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
	public void checkAllConnected(){
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
	
	
	//get Deck
	public Deck getDeck(){
		return this.deck;
	}
	
	//set currentplayercard
	synchronized public void setCurrentPlayerCard(String card){
		if(this.currentlyPlayedCard==null){
			this.currentlyPlayedCard=card;
		}
	}
	
	//get currentplayercard
	synchronized public String getCurrentPlayerCard(){
		return this.currentlyPlayedCard;
	}
	
	//set request player
	synchronized public void setRequestPlayer(String player){
		if(this.requestPlayer==null){
			this.requestPlayer=player;
		}
	}
	
	//get request player
	synchronized public String getRequestPlayer(){
		return this.requestPlayer;
	}
	
	//get table
	synchronized public int[] getTrickCards(){
		return this.trickCards;
	}
	
	//get trickWinplayr
	synchronized public int getTrickWinnter(){
		return this.trickWinPlayer;
	}
	
	//get StartedPlayer
	synchronized public int getRoundStarter(){
		return this.startedPlayer;
	}
	
	
	//get trick wins
	synchronized public int[] getTrickWins(){
		return this.trickWins;
	}
	
	//reset tablecards
	public void resetTrickCards(){
		for(int i=0;i<4;i++){
			this.trickCards[i]=-1;
		}
	}
	
	//reset winner log
	public void restTrickWins(){
		for(int i=0;i<4;i++){
			this.trickWins[i]=-1;
		}
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

	
	//config sending messges
	public void setSendingMessage(String message){
		for(int i=0;i<4;i++){
			this.playerMessages[i] = new SendingMessage(this,i,message);
		}
	}
	
	//send messages to all
	public void sendToAll(){
		for(int i=0;i<4;i++){
			BackBone.sendMessage(BackBone.playerConnections.get(this.playermap.get(i)),
					this.playerMessages[i].getJSONString());
		}
	}
	
	//send messages to selected
	public void requestUpdate(String player){
		BackBone.sendMessage(BackBone.playerConnections.get(player),
				this.playerMessages[this.playermap2.get(player)].getJSONString());
			
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////
								//Game logic//
	//////////////////////////////////////////////////////////////////
	
	public int checkWinTrick(){
		int currentType = trickCards[trickWinPlayer]/13;
		int triumph = deck.getTriumph()/13;
		int winner=0;
		for(int i=0;i<4;i++){
			if(this.trickCards[i]/13==triumph){
				if(this.trickCards[i]==triumph*13){
					return i;
				}
				else if(this.trickCards[winner]/13==triumph){
					if(this.trickCards[i]>this.trickCards[winner]){
						winner = i;
					}
				}
				else{
					winner=i;
				}
			}
			
			else if(this.trickCards[i]/13==currentType){
				if(this.trickCards[i]==currentType*13){
					winner = i;
				}
				else if(this.trickCards[winner]/13==currentType){
					if(this.trickCards[i]>this.trickCards[winner]){
						winner = i;
					}
				}
			}
		}
		return winner;
	}
	
	
}
