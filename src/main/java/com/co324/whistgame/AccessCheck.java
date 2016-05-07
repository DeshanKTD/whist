package com.co324.whistgame;


public enum AccessCheck {
	ZEROCONNECTED{
		AccessCheck onConnect(HandleWhist handle){
			System.out.println("One player connected");
			handle.setInitialMessage("One player conneceted, Three more to Connect...");
			handle.requestUpdate(handle.playermap.get(0));
			return ONECONNECTED;
		}
	},
	ONECONNECTED{
		AccessCheck onConnect(HandleWhist handle){
			System.out.println("TWO player connected");
			handle.setInitialMessage("Two Players conneceted. Two more to Connect....");
			for(int i=0;i<2;i++){
				handle.requestUpdate(handle.playermap.get(i));
			}
			return TWOCONNECTED;
		}
	},
	TWOCONNECTED{
		AccessCheck onConnect(HandleWhist handle){
			System.out.println("THREE player connected");
			handle.setInitialMessage("Three Players conneceted. One more to Connect....");
			for(int i=0;i<3;i++){
				handle.requestUpdate(handle.playermap.get(i));
			}
			return THREECONNECTED;
		}
	},
	THREECONNECTED{
		AccessCheck onConnect(HandleWhist handle){
			System.out.println("FOUR player connected");
			handle.setInitialMessage("All players Connected.. lets start....");
			for(int i=0;i<4;i++){
				handle.requestUpdate(handle.playermap.get(i));
			}
			return FOURCONNECTED;
		}
	},
	FOURCONNECTED{
		AccessCheck onConnect(HandleWhist handle){
			System.out.println("FOUR player connected");
			handle.setInitialMessage("All players Connected.. lets start....");
			for(int i=0;i<4;i++){
				handle.requestUpdate(handle.playermap.get(i));
			}
			return FOURCONNECTED;
		}
	};
	
	abstract AccessCheck onConnect(HandleWhist handle);
	
}
