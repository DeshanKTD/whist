package com.co324.whistgame;


/*
 * This class handle the total whist game currently
 * The game logic and message api is implemented in here
 * The all varibales needed for game to play are in here
 */

import java.util.HashMap;


public class HandleWhist extends Thread{
	
	//maps to map player to its numerical values and name
	public HashMap<Integer,String> playermap = new HashMap<Integer,String>();
	public HashMap<String,Integer> playermap2 = new HashMap<String,Integer>();
	//player names
	private String players[] = {"playerOne","playerTwo","playerThree","playerFour"};
	
	
	
	
	
	private int[] trickCards = new int[4];							//cards on the board
	private int[] trickWins = new int[4];							//tricks win by each player
	private int startedPlayer;										//game started player
	private int trickWinPlayer;										//final trick winner
	private int currentPlayingPlayer;								//currently playing player
	private SendingMessage[] playerMessages = new SendingMessage[4];//messages that sends to each player
	private String currentlyPlayedCard;								//finaly played card
	private String requestPlayer;									//finaly player which requested card play
	private boolean allConnected;									//check all connected
	
	
	
	
	private AccessCheck checkConnects = AccessCheck.ZEROCONNECTED;	//player connect state change
	private CardPlay play;											//game play state change
	
	
	
	private Deck deck;				//the all about deck
	
	
	
	
	public HandleWhist(){
		deck = new Deck();
		startedPlayer = 0;
		trickWinPlayer = 0;
		currentPlayingPlayer = 0;
		currentlyPlayedCard = null;
		requestPlayer = null;
		for(int i=0;i<4;i++){
			playermap.put(i, players[i]);
			playermap2.put(players[i], i);
			trickCards[i]=-1;
			trickWins[i] =0;
		}
	}
	
	
	
	public void run(){
		System.out.println("start Handle");
		this.allConnected=checkAllConnected();
		System.out.println("shuffling....");
		deck.shuffleDeck();
		deck.distributeDeck();
		System.out.println("deck distributed....");
		System.out.println("game started");
		this.setSendingMessage("Player One chance to Play");
		this.sendToAll();
	}
	

	
	
	/////////////////////////////////////////////////////////////////////////////////////
	/// get set api
	/////////////////////////////////////////////////////////////////////////////////////
	
	
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
	
	//reset currentplayer card
	synchronized public void setCurrentPlayerCard(){
		this.currentlyPlayedCard=null;
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
	
	//reset request player
	synchronized public void setRequestPlayer(){
		this.requestPlayer=null;
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
	
	//set trickWinPlayer
	synchronized public void setTrickWinner(int player){
		this.trickWinPlayer = player;
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
					true,true,"lets play",false,false,0);
			System.out.println(playerMessages[i].getJSONString());
			BackBone.sendMessage(BackBone.playerConnections.get(players[i]), playerMessages[i].getJSONString());
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////
		//message api
	///////////////////////////////////////////////////////////////////////////////
	
	//config sending messges
	synchronized public void setSendingMessage(String message){
		for(int i=0;i<4;i++){
			this.playerMessages[i] = new SendingMessage(this,i,message);
			//System.out.println(this.playerMessages[i].getJSONString());
		}
	}
	
	//initalize message
	synchronized public void setInitialMessage(String message){
		for(int i=0;i<4;i++){
			this.playerMessages[i] = new SendingMessage(message);
		}
	}
	
	//send messages to all
	synchronized public void sendToAll(){
		for(int i=0;i<4;i++){
			BackBone.sendMessage(BackBone.playerConnections.get(this.playermap.get(i)),
					this.playerMessages[i].getJSONString());
		}
	}
	
	//send messages to selected
	synchronized public void requestUpdate(String player){
		BackBone.sendMessage(BackBone.playerConnections.get(player),
				this.playerMessages[this.playermap2.get(player)].getJSONString());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////
		//Game logic//
	//////////////////////////////////////////////////////////////////
	
	
	//check whether four players connected
	public boolean checkAllConnected(){
		boolean set1 = false;
		boolean set2 = false;
		boolean set3 = false;
		boolean set4 = false;
		
		
		while(true){
			if(BackBone.counter==1 && set1==false){
				checkConnects = checkConnects.onConnect(this);
				set1=true;
			}
			else if(BackBone.counter==2 && set2==false){
				checkConnects = checkConnects.onConnect(this);
				set2=true;
			}
			else if(BackBone.counter==3 && set3==false){
				checkConnects = checkConnects.onConnect(this);
				set3=true;
			}
			else if(BackBone.counter==4 && set4==false){
				checkConnects = checkConnects.onConnect(this);
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
		play = CardPlay.PONECHANCE;
		return true;
	}

	public void playingGame(){
		if(checkWinner()){
			return;
		}
		play = play.onPlay(this);
		
	}
	
	public boolean checkWinner(){
		for(int i=0;i<4;i++){
			if(this.trickWins[i]==10){
				return true;
			}
		}
		return false;
	}
	
	public int checkGameWinner(){
		for(int i=0;i<4;i++){
			if(this.trickWins[i]==10);
				return i;
		}
		return -1;
	}
	
	
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
