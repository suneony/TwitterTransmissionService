package com.suneony;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {
		private static final int PORT = 5050;
		public void listener() {
			System.out.println("TCP Service Start");
			try {
				ServerSocket socket = new ServerSocket(TCPServer.PORT);
				System.out.println("Listening Port: " + TCPServer.PORT);
				while (true) {
					Socket client = socket.accept();
					System.out.println("A client entering, the IP is: " + client.getInetAddress() + " and the Port is: "
							+ client.getPort());
					new GetFileNameThread(client).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public class GetFileNameThread extends Thread{
			private Socket client=null;
			public GetFileNameThread(Socket client){
				this.client=client;
			}
			public void run(){
				BufferedWriter writer=null;
				BufferedReader reader=null;
				try {
					reader=new BufferedReader(new FileReader(new File(".\transmission.txt")));
					writer=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
					writer.write(reader.readLine());
					writer.flush();
					reader.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		public static void main(String[] args){
			TCPServer server=new TCPServer();
			server.listener();
		}
}
