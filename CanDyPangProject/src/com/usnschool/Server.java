package com.usnschool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket server;
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	public Server() {
		try {
			server = new ServerSocket(7777);
			socket = server.accept();
			System.out.println("대상이 접속되었습니다.");
			oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			oos.writeUTF("서버에서 보내는 문자입니다.");
			oos.flush();
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			System.out.println(ois.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
