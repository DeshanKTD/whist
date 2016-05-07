package com.co324.whistgame;

public enum CardPlay {
	PONECHANCE{
		CardPlay onPlay(HandleWhist handle){
			handle.setSendingMessage("PlayerOne chance to play");
			handle.sendToAll();
			
			
			if(handle.getRequestPlayer().equals("playerOne")){
				if(handle.getDeck().checkValidCard(0, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[0]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(0, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard();
					handle.setRequestPlayer();
					
					if((handle.getTrickWinnter()-1)%4==0){
						int player = handle.checkWinTrick();
						handle.setTrickWinner(player);
						handle.getTrickWins()[player]++;
						handle.resetTrickCards();
						handle.setSendingMessage("Player "+handle.playermap.get(player)+" won the trick");
						handle.sendToAll();
						
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
						
					}
					handle.setSendingMessage("PlayerTwo chance to play");
					handle.sendToAll();
					return PTWOCHANCE;
				}
				
				handle.setCurrentPlayerCard();
				handle.setRequestPlayer();
				
			}
			return PONECHANCE;
			
		}
	},
	PTWOCHANCE{
		CardPlay onPlay(HandleWhist handle){
			
			System.out.println("player two chance");
			handle.setSendingMessage("PlayerTwo chance to play");
			handle.sendToAll();
			
			System.out.println(handle.getRequestPlayer() + " CHANCE two play");
			if(handle.getRequestPlayer().equals("playerTwo")){
				System.out.println("player Two ok");
				if(handle.getDeck().checkValidCard(1, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[1]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(1, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard();
					handle.setRequestPlayer();
					
					if((handle.getTrickWinnter()-1)%4==1){
						int player = handle.checkWinTrick();
						handle.setTrickWinner(player);
						handle.getTrickWins()[player]++;
						handle.resetTrickCards();
						handle.setSendingMessage("Player "+handle.playermap.get(player)+" won the trick");
						handle.sendToAll();
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
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
						
					}
				
					handle.setSendingMessage("PlayerThree chance to play");
					handle.sendToAll();
					return PTHREECHANCE;
				}
				handle.setCurrentPlayerCard();
				handle.setRequestPlayer();
			}
			return PTWOCHANCE;
			
		}
		
	},
	PTHREECHANCE{
		CardPlay onPlay(HandleWhist handle){
			
			System.out.println("player three chance");
			handle.setSendingMessage("PlayerThree chance to play");
			handle.sendToAll();
			
			if(handle.getRequestPlayer().equals("playerThree")){
				if(handle.getDeck().checkValidCard(2, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[2]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(2, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard();
					handle.setRequestPlayer();
					
					if((handle.getTrickWinnter()-1)%4==2){
						int player = handle.checkWinTrick();
						handle.setTrickWinner(player);
						handle.getTrickWins()[player]++;
						handle.resetTrickCards();
						handle.setSendingMessage("Player "+handle.playermap.get(player)+" won the trick");
						handle.sendToAll();
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
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
						
					}
					
					handle.setSendingMessage("PlayerFour chance to play");
					handle.sendToAll();
					return PFOURCHANCE;
				}
				handle.setCurrentPlayerCard();
				handle.setRequestPlayer();
			}
			return PTHREECHANCE;
			
		}
		
	},
	PFOURCHANCE{
		CardPlay onPlay(HandleWhist handle){
			
			System.out.println("player four chance");
			handle.setSendingMessage("PlayerFour chance to play");
			handle.sendToAll();
			
			if(handle.getRequestPlayer().equals("playerFour")){
				if(handle.getDeck().checkValidCard(3, handle.getCurrentPlayerCard())){
					handle.getTrickCards()[3]=handle.getDeck().decodeToCard(handle.getCurrentPlayerCard());
					handle.getDeck().removeCard(3, handle.getCurrentPlayerCard());
					handle.setCurrentPlayerCard();
					handle.setRequestPlayer();
					
					System.out.println((handle.getTrickWinnter()-1)%4);
					
					if((handle.getTrickWinnter()-1)%4==3){
						System.out.println("Trick winner entered");
						int player = handle.checkWinTrick();
						handle.setTrickWinner(player);
						handle.getTrickWins()[player]++;
						handle.resetTrickCards();
						handle.setSendingMessage("Player "+handle.playermap.get(player)+" won the trick");
						handle.sendToAll();
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
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
						
					}
					
					handle.setSendingMessage("PlayerOne chance to play");
					handle.sendToAll();
					return PONECHANCE;
				}
				handle.setCurrentPlayerCard();
				handle.setRequestPlayer();
			}
			return PFOURCHANCE;
			
		}
		
	};
	
	
	
	abstract CardPlay onPlay(HandleWhist handle);
	
	
	
}
