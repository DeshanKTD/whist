package com.co324.whistgame;

public enum CardPlay {
	PONECHANCE{
		CardPlay onPlay(HandleWhist handle){
			if(handle.getRequestPlayer().equals("playerOne")){
				if(handle.getDeck().checkValidCard(0, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[0]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(0, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard(null);
					handle.setRequestPlayer(null);
					
					if((handle.getTrickWinnter()-1)%4==0){
						int player = handle.checkWinTrick();
						if(player == 0){
							return PONECHANCE;
						}
						else if(player == 1){
							return PTWOCHANCE;
						}
						else if(player == 2){
							return PTHREECHANCE;
						}
						else if(player == 3){
							return PFOURCHANCE;
						}	
						handle.resetTrickCards();
					}
					return PTWOCHANCE;
				}
			}
			return PONECHANCE;
			
		}
	},
	PTWOCHANCE{
		CardPlay onPlay(HandleWhist handle){
			if(handle.getRequestPlayer().equals("playerTwo")){
				if(handle.getDeck().checkValidCard(1, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[1]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(1, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard(null);
					handle.setRequestPlayer(null);
					
					if((handle.getTrickWinnter()-1)%4==1){
						int player = handle.checkWinTrick();
						if(player == 0){
							return PONECHANCE;
						}
						else if(player == 1){
							return PTWOCHANCE;
						}
						else if(player == 2){
							return PTHREECHANCE;
						}
						else if(player == 3){
							return PFOURCHANCE;
						}	
						handle.resetTrickCards();
					}
					return PTHREECHANCE;
				}
			}
			return PTWOCHANCE;
			
		}
		
	},
	PTHREECHANCE{
		CardPlay onPlay(HandleWhist handle){
			if(handle.getRequestPlayer().equals("playerThree")){
				if(handle.getDeck().checkValidCard(2, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[2]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(2, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard(null);
					handle.setRequestPlayer(null);
					
					if((handle.getTrickWinnter()-1)%4==2){
						int player = handle.checkWinTrick();
						if(player == 0){
							return PONECHANCE;
						}
						else if(player == 1){
							return PTWOCHANCE;
						}
						else if(player == 2){
							return PTHREECHANCE;
						}
						else if(player == 3){
							return PFOURCHANCE;
						}	
						handle.resetTrickCards();
					}
					return PFOURCHANCE;
				}
			}
			return PTHREECHANCE;
			
		}
		
	},
	PFOURCHANCE{
		CardPlay onPlay(HandleWhist handle){
			if(handle.getRequestPlayer().equals("playerFour")){
				if(handle.getDeck().checkValidCard(3, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[3]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(3, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard(null);
					handle.setRequestPlayer(null);
					
					if((handle.getTrickWinnter()-1)%4==3){
						int player = handle.checkWinTrick();
						if(player == 0){
							return PONECHANCE;
						}
						else if(player == 1){
							return PTWOCHANCE;
						}
						else if(player == 2){
							return PTHREECHANCE;
						}
						else if(player == 3){
							return PFOURCHANCE;
						}	
						handle.resetTrickCards();
					}
					return PONECHANCE;
				}
			}
			return PFOURCHANCE;
			
		}
		
	};
	
	
	
	abstract CardPlay onPlay(HandleWhist handle);
}
