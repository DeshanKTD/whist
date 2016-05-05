package com.co324.whistgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck {
	//cards map its value to card name
	private HashMap<Integer,String> setOfNamedCards = new HashMap<Integer,String>();
	//set of cards by its values
	private ArrayList<Integer> setOfCards = new ArrayList<Integer>();
	//the each players hand
	@SuppressWarnings("unchecked")
	private ArrayList<Integer>[] playersHand = new ArrayList[4];
	
	
	//constructor
	public Deck(){
		initiateDeck();
		initiatePlayerHand();
	}
	
	//initiate set of cards
	private void initiateDeck(){
		int type;
		int cardValue;
		String cardName="";
		for(int i=0;i<52;i++){
			this.setOfCards.add(i);
			type = i/13;
			cardValue = (i%13)+1;
			cardName= "cards/"+type+"_"+cardValue+".png";
			this.setOfNamedCards.put(i, cardName);
		}
	}
	
	
	//decode the cardName to get card value
	public int decodeToCard(String card){
		String cardName = card.split("/")[1];
		cardName = cardName.split(".")[0];
		int type = Integer.parseInt(cardName.split("_")[0]);
		int val = Integer.parseInt(cardName.split("_")[1])-1;
		
		return type*13+val;
	}
	
	//initate players hand
	private void initiatePlayerHand(){
		for(int i=0;i<4;i++){
			this.playersHand[i] = new ArrayList<Integer>();
		}
	}
	
	//shuffle the deck
	public void shuffleDeck(){
		Collections.shuffle(setOfCards);
		//clear the hand of each player
		for(int i=0;i<4;i++){
			this.playersHand[i].clear();
		}
	}
	
	//distribute deck
	public void distributeDeck(){
		for(int i=0;i<52;i++){
			this.playersHand[i%4].add(this.setOfCards.get(i));
		}
	}
	
	//remove card from hand
	public void removeCard(int player,String card){
		int cardVal = decodeToCard(card);
		int i=0;
		for(i=0;i<this.playersHand[player].size();i++){
			if(this.playersHand[player].get(i)==cardVal){
				break;
			}
		}
		this.playersHand[player].remove(i);
		
	}

	
	//check the played card is with the player
	public boolean checkValidCard(int player,String card){
		Integer cardVal = decodeToCard(card);
		if(this.playersHand[player].contains(cardVal)){
			return true;
		}
		return false;
		
	}
	
	
	//check player has cards from current type
	public boolean checkCardsInCorrectType(String currentType,String playedType, int player){
		int currenttype = decodeToCard(currentType)/13;
		int playedtype = decodeToCard(playedType)/13;
		
		if(currenttype == playedtype){
			return true;
		}
		else{
			for(int i=0;i<this.playersHand[player].size();i++){
				if(this.playersHand[i].get(i)/13 == currenttype)
					return false;
			}
			return true;
		}
	}
	
	
	//return for game handle class
	
	public HashMap<Integer,String> getSetOfCardNames(){
		return this.setOfNamedCards;
	}
	
	public int getTriumph(){
		return this.setOfCards.get(51);
	}
	
	public ArrayList<Integer>[] getPlayersHand(){
		return this.playersHand;
	}
	
	
	
}
