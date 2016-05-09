package com.co324.whistgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class BackBone extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public static Object counterLock = new Object();
	public static int counter = 0;
	public HandleWhist handleGame;
	
	//keeping the player names for user ids
	private String connection = null;
	private String players[] = {"playerOne","playerTwo","playerThree","playerFour"};
	public static Object connectionLock = new Object();
	public static ConcurrentHashMap<String, AsyncContext> playerConnections = new ConcurrentHashMap<String,AsyncContext>();
	
	
	public void init(ServletConfig config){
		handleGame = new HandleWhist();
		handleGame.start();
		
	}
	
	
	
	//handle sse 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		System.out.println("sse connected");
		if(checkSession(request,response)){
			processRequset(request,response);
		}
	}
	
	//handle ajax
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		System.out.println("post req");
		if(!checkSession(request,response)){
			this.handleGame.setRequestPlayer(this.connection);
			this.handleGame.setCurrentPlayerCard(request.getParameter("card"));
			this.handleGame.playingGame();
		}
		
	}

	
	//check for session
	protected boolean checkSession(HttpServletRequest request,HttpServletResponse response){
	
		HttpSession session = request.getSession(  );
		this.connection = (String) session.getAttribute("userID");
		//System.out.println(connection);
		//System.out.println(counter);
		
		if(connection == null){
			session.setAttribute("userID",players[counter]);
			System.out.println("New Session Created");
			synchronized(BackBone.counterLock){
				counter++;
			}
			return true;
		}
		else if(counter>5){
			return false;
		}
		else{
			System.out.println("session exists");
			System.out.println(connection);
			processRequset(request,response,this.connection);
			this.handleGame.requestUpdate(this.connection);
			
			return false;
		}
		
	}
	
	
	//process sse
	protected void processRequset(HttpServletRequest request, HttpServletResponse response ){
		
		// This a Tomcat specific - makes request asynchronous
		request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
		
		response.setContentType("text/event-stream");	
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Connection", "keep-alive");
		
		try {
			System.out.println("sending data");
			PrintWriter writer = response.getWriter();
			writer.write("data: "+ players[counter-1] +"\n\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		checkAsync(request);
	}
	
	//
	//process sse for second request
		protected void processRequset(HttpServletRequest request, HttpServletResponse response,String player ){
			
			// This a Tomcat specific - makes request asynchronous
			request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
			
			response.setContentType("text/event-stream");	
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Connection", "keep-alive");
			
			try {
				System.out.println("sending data");
				PrintWriter writer = response.getWriter();
				writer.write("data: reconnects\n\n");
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			checkAsync(request,player);
		}
	
		
		
	//send message
	public static void sendMessage(AsyncContext async,String message){
		PrintWriter writer;
		try {
			writer = async.getResponse().getWriter();
			writer.write("data: "+ message +"\n\n");
			writer.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	// Start asynchronous context and add listeners to remove it in case of errors
	
	private void checkAsync(HttpServletRequest request ){
			
		final AsyncContext ac = request.startAsync();
		ac.setTimeout(900000000);
		ac.addListener(new AsyncListener(){
			 public void onComplete(AsyncEvent event) throws IOException {
				 //	playerConnections.remove(connection);
	         }
	         public void onError(AsyncEvent event) throws IOException {
	        	 	//playerConnections.remove(connection);
	         }
	         public void onStartAsync(AsyncEvent event) throws IOException {
	                // Do nothing
	         }
	         public void onTimeout(AsyncEvent event) throws IOException {
	            	//playerConnections.remove(connection);
	         }
		});
		
		System.out.println("access async");
		//add current connections to a hashmap
		synchronized(connectionLock){
			playerConnections.put(players[counter-1], ac);
			System.out.println("HashMap : "+BackBone.playerConnections.size());
			//this.handleGame.getAccessStatus().onConnect(players);
		}
	}
	
	//for 2nd connections
	private void checkAsync(HttpServletRequest request,String name ){
		
		final AsyncContext ac = request.startAsync();
		ac.setTimeout(900000000);
		ac.addListener(new AsyncListener(){
			 public void onComplete(AsyncEvent event) throws IOException {
				 //	playerConnections.remove(connection);
	         }
	         public void onError(AsyncEvent event) throws IOException {
	        	 	//playerConnections.remove(connection);
	         }
	         public void onStartAsync(AsyncEvent event) throws IOException {
	                // Do nothing
	         }
	         public void onTimeout(AsyncEvent event) throws IOException {
	            	//playerConnections.remove(connection);
	         }
		});
		
		System.out.println("access async");
		//add current connections to a hashmap
		synchronized(connectionLock){
			playerConnections.put(name, ac);
			System.out.println("HashMap : "+BackBone.playerConnections.size());
			//this.handleGame.getAccessStatus().onConnect(players);
		}
	}
	
	
	
	
	//check whether all players are still connected
	public static boolean checkPlayersOnline(){
		if(playerConnections.size()==4){
			return true;
		}
		else{
			return false;
		}
	}

	
	
}



