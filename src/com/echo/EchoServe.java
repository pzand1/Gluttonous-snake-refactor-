package com.echo;

import com.base.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServe implements Serializable {
	public static int TCP_Port = 8000;  //规定TCP连接的端口号为8000,并建立TCP连接
	public static int UDP_Port = 9999;  //规定TCP连接的端口号为8000,并建立TCP连接
	private static int UserId = 1;
	private ServerSocket serverSocket;
	//	private Socket socket;
	Thread broadcastPortThread;
	private LinkedList<Socket> sockets = new LinkedList<>();
	private Map<Socket, SocketAccept> socketAccepts = new HashMap<>();

	private HashMap<Integer, Snake> snakes = new HashMap<>();

	private LinkedList<Food> foods = new LinkedList<>();

	public void init() throws IOException {
		this.startBroadcast();
		this.startTCPConnect();
	}

	//开始接受TCP连接
	public void startTCPConnect() throws IOException {
		serverSocket = new ServerSocket(TCP_Port);
		Socket socket = serverSocket.accept();
		for(int i = 1;i <= 1;i++){
			sockets.add(socket);
			SocketAccept socketAccept = new SocketAccept(socket);
			socketAccept.start();
			socketAccepts.put(socket, socketAccept);

			System.out.println("客户端通过TCP连接成功");
		}
	}

	/**
	 * 广播信号
	 */
	private void startBroadcast() {
		//开始广播端口号
		broadcastPortThread = new BroadcastPort();
		broadcastPortThread.start();
		System.out.println("广播启动");
	}

	/**
	 * 关闭广播
	 */
	private void closeBroadcast() {
		broadcastPortThread.interrupt();
		System.out.println("广播关闭");
	}

	//广播线程类
	private class BroadcastPort extends Thread {
		@Override
		public void run() {
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
			String TCP_PortToString = String.valueOf(TCP_Port);
			DatagramPacket datagramPacket = null;

			try {
				datagramPacket = new DatagramPacket(TCP_PortToString.getBytes(), TCP_PortToString.getBytes().length,
						InetAddress.getByName("255.255.255.255"), UDP_Port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			while (true) {
				try {
					socket.send(datagramPacket);
					Thread.sleep(1000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}

	//开始服务器的操作
	public void start() throws IOException, InterruptedException {
		//关闭广播
		this.closeBroadcast();
		//让客户端接受初始化对象
		for(Socket socket : sockets) {
			Snake snake = new Snake(UserId);

			LinkedList<Draw> list = new LinkedList<>();
			list.add(snake);
			send(socket, list);

//			snakes.put(Objects.hash(UserId++), snake);
//			System.out.println("Objects.hash(UserId++)" + Objects.hash(1));
		}
		//初始化服务器的对象
		for(int i = 1;i <= 5;i++) {
			foods.add(new Food(snakes));
			foods.add(new bigFood(snakes));
		}

		//跑
		while(true){
			long start = System.nanoTime();
			//accept
			for(Socket socket : sockets){
				acceptSockeyMessage(socket);
			}
			//handle
			for(Map.Entry<Integer, Snake> entity : snakes.entrySet()){
				Snake snake = entity.getValue();
				snake.move();
				snake.eatFood(snakes, foods);
			}
			//send
			LinkedList<Draw> data = new LinkedList<>();
			for(Map.Entry<Integer, Snake> entity : snakes.entrySet()){
				data.add(entity.getValue());
			}
			for(Food food : foods){
				data.add(food);
			}
			for (Socket socket : sockets){
				send(socket, data);
			}
			//pause
			long end = System.nanoTime();
			Thread.sleep(18);
		}
	}

	public void acceptSockeyMessage(Socket socket) throws InterruptedException {
		SocketAccept socketAccept = socketAccepts.get(socket);
		String message = socketAccept.Get();
		this.parseMessage(message);
	}

	private void send(Socket socket, Object drawList) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(drawList);
		objectOutputStream.flush();
	}

	private class SocketAccept extends Thread {
		public String request;
		private final Socket socket;

		public SocketAccept(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				InputStream inputStream = socket.getInputStream();
				Scanner scan = new Scanner(inputStream);
				while(true) {
					request = scan.nextLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public String Get() {
			return this.request;
		}
	}

	/**
	 * 类名, HashCode, 操作 , 数值
	 */
	private void parseMessage(String request){
		if(request == null){
			return;
		}
		String[] arr = request.split(",");
		if(arr[0].equals("class com.base.Snake")) {
			Snake snake = snakes.get(Integer.parseInt(arr[1]));
			if (arr[2].equals("move")) {
				control.trun_To_SpeDir(snake, 'l', Double.parseDouble(arr[3]));
				snake.move();
				snake.eatFood(snakes, foods);

//				System.out.println(arr[1]);
//				return Integer.parseInt(arr[1]);
			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServe serve = new EchoServe();
		serve.init();
		Thread.sleep(100);
		serve.start();
	}
}