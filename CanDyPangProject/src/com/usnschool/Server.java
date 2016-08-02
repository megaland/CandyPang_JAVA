package com.usnschool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	ServerSocket server;
	Socket socket;
	private final int ID_PASSWORD_CHECK = 2;
    private final int RIGHT_PASSWORD = 21;
    private final int WRONG_PASSWORD = 22;
    private final int REGIST_ACTION = 3;
    private final int REGIST_SUCCESS = 31;
	DBConnector connector;
	//ArrayList<ObjectOutputStream> ooslist;
	//ArrayList<ObjectInputStream> oislist;
	public Server() {
		connector = DBConnector.getConnector();
		try {
			server = new ServerSocket(7777);
			while(true){
				socket = server.accept();
				System.out.println("대상이 접속되었습니다.");
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				//ooslist.add(oos);
				oos.writeUTF("서버에서 보내는 문자입니다.");
				oos.flush();
				
				
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			
				System.out.println(ois.readUTF());
				//oislist.add(ois);
				new Thread(){
					public void run() {
						while(true){
							try {
								int check = ois.readInt();
								System.out.println(check);
								switch(check){
									case 1:{
										System.out.println("1번을 받았습니다.");
										
										break;
									}
									case ID_PASSWORD_CHECK:{ //로그인
										String id = ois.readUTF();
										String password = ois.readUTF();
										System.out.println("성공");
										break;
									}
									case 	REGIST_ACTION: {//가입		
										String id = ois.readUTF();
										String password = ois.readUTF();
										connector.insertUserInfo(id, password);
										oos.writeInt(REGIST_SUCCESS);
										oos.flush();
										break;
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					};
				}.start();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
