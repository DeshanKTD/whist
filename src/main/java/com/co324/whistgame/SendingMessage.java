package com.co324.whistgame;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class SendingMessage {
	private ArrayList<Integer> cards;
	private String card1;
	private String card2;
	private String card3;
	private String card4;
	private boolean showHand;
	private boolean showCards;
	private String message;
	private boolean showNewGame;
	private boolean showLeaveGame;
	private int currentScore;
	private Deck deck;
	private String triumph;
	private String player;
	
	
	public SendingMessage(Deck deck,ArrayList<Integer> cards,String[] playerCards,boolean showHand,
			boolean showCards,String message,boolean showLeaveGame,boolean showNewGame,int currentScore){
		
		this.deck = deck;
		this.cards = cards;
		this.showCards = showCards;
		this.showHand = showHand;
		this.showNewGame = showNewGame;
		this.showLeaveGame = showLeaveGame;
		this.message = message;
		this.currentScore = currentScore;
		this.card1 = playerCards[0];
		this.card2 = playerCards[1];
		this.card3 = playerCards[2];
		this.card4 = playerCards[3];
		this.triumph= deck.getSetOfCardNames().get(deck.getTriumph());
		this.player = "not assigned";
	}
	
	public SendingMessage(HandleWhist handle,int player,String message){
		this.cards = handle.getDeck().getPlayersHand()[player];
		this.showCards = true;
		this.showHand = true;
		this.showNewGame = false;
		this.showLeaveGame = false;
		this.message =message;
		this.currentScore = handle.getTrickWins()[player];
		
		if(handle.getTrickCards()[0]>-1){
			this.card1=handle.getDeck().getSetOfCardNames().get(handle.getTrickCards()[0]);
		}
		if(handle.getTrickCards()[1]>-1){
			this.card1=handle.getDeck().getSetOfCardNames().get(handle.getTrickCards()[1]);
		}
		if(handle.getTrickCards()[2]>-1){
			this.card1=handle.getDeck().getSetOfCardNames().get(handle.getTrickCards()[2]);
		}
		if(handle.getTrickCards()[3]>-1){
			this.card1=handle.getDeck().getSetOfCardNames().get(handle.getTrickCards()[3]);
		}
		this.triumph = handle.getDeck().getSetOfCardNames().get(handle.getDeck().getTriumph());
		this.player = handle.playermap.get(player);
		
	}
	
	
	
	public String getJSONString(){
		JSONObject object = new JSONObject();
		try {
			object.put("cards", this.getJArray(cards));
			object.put("showCards", this.showCards);
			object.put("showHand", this.showHand);
			object.put("showNewGame", this.showNewGame);
			object.put("showLeaveGame", this.showLeaveGame);
			object.put("currentScore", this.currentScore);
			object.put("card1", this.card1);
			object.put("card2", this.card2);
			object.put("card3", this.card3);
			object.put("mycard", this.card4);
			object.put("message", this.message);
			object.put("triumph", this.triumph);
			object.put("player", this.player);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
		
	}
	
	
	
	
	private JSONArray getJArray(ArrayList<Integer> cards){
		JSONArray array = new JSONArray();
		JSONObject set;
		
		for(int i=0;i<cards.size();i++){
			set = new JSONObject();
			try {
				set.put("image", this.deck.getSetOfCardNames().get(cards.get(i)));
				array.put(set);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return array;
	}
	
}
