package usnschool.com.candypangproject;

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
    private final int REGIST_ACTION = 3;
    private final int REGIST_SUCCESS = 31;
	DBConnector connector;
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
									
									case Constants.ID_PASSWORD_CHECK:{ //로그인
										String id = ois.readUTF();
										String password = ois.readUTF();
										int result = 0;
										if(connector.getCheckIdPassword(id, password)){
											result = Constants.RIGHT_PASSWORD;
											System.out.println("성공");
										}else{
											result = Constants.WRONG_PASSWORD;
											System.out.println("실패");
											
										}
										oos.writeInt(result);
										oos.flush();
										break;
									}
									
									case 	REGIST_ACTION: {//가입		
										String id = ois.readUTF();
										String password = ois.readUTF();
										if(connector.getIdExist(id)){
											oos.writeInt(Constants.REGIST_ID_EXIST);
											oos.flush();
										}else {
											connector.insertUserInfo(id, password);
											oos.writeInt(REGIST_SUCCESS);
											oos.flush();
										}
										

										break;
									}
									case Constants.UPDATE_RECORD : {
										String id = ois.readUTF();
										int score = ois.readInt();
										connector.updateScore(id, score);
										
										break;
									}
									
									case Constants.GET_RANKER_AND_ME : {
										String id = ois.readUTF();
										
										int myrank = connector.getMyRank(id);
										int myscore = connector.getMyScore(id);
										ArrayList<RankerData> rankerlist = connector.getRankerList();
										
										oos.writeInt(myrank);
										oos.flush();
										oos.writeInt(myscore);
										oos.flush();
										oos.writeObject(rankerlist);
										oos.flush();
										System.out.println("기록데이터를 전송했습니다");
									}
									
									
								}
							} catch (IOException e) {
								if(oos!=null){
									try {
										oos.close();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
								if(ois!=null){
									try {
										ois.close();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
								e.printStackTrace();
								System.out.println("클라이언트 하나가 끊어졌습니다");
								break;
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
