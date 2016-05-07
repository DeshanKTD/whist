package com.co324.whistgame;

public enum GameFlow {
	WAITING{
		GameFlow onFlow(HandleWhist handle){
			handle.checkAllConnected();
			return DELIVERDECK;
		}
	},
	DELIVERDECK{
		GameFlow onFlow(HandleWhist handle){
			handle.getDeck().shuffleDeck();
			handle.getDeck().distributeDeck();
			System.out.println("Deck Distributed");
			
			return PLAYING;
		}
	},
	PLAYING{
		
	},
	END{
		
	};
	
	abstract GameFlow onFlow(HandleWhist handle);
	
}
