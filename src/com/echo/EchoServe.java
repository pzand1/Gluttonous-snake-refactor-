package com.echo;

import com.base.*;
import com.library.StdDraw;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServe implements Serializable {
	public static int TCP_Port = 8000;  //规定TCP连接的端口号为8000,并建立TCP连接
	public static int UDP_Port = 9999;  //规定TCP连接的端口号为8000,并建立TCP连接
	private ServerSocket serverSocket;
	private static int UserId = 1;
//	private Socket socket;

	private Map<Socket, SocketAccept> socketAccepts;
	private LinkedList<Socket> sockets;
	Thread broadcastPortThread;
	private HashMap<Integer, Snake> snakes = new HashMap<>();

	private LinkedList<Food> foods = new LinkedList<>();

	public void init() throws IOException {
		this.startBroadcast();
		this.startTCPConnect();
	}

//	public void start() throws IOException, InterruptedException {
//		System.out.println("TCP服务器已启动");
//		//1.创建线程池来解决一个服务器多个客户端的问题
//		ExecutorService ponds = Executors.newCachedThreadPool();
//		serverSocket = new ServerSocket(TCP_Port);
//
//		while (true) {
//
//
//			socket = this.broadCast();
//			for (int i = 0; i < 10; i++) {
//				Food food = new Food();
//				food.createFood(snakes);
//				Foods.add(food);
//			}
//			Snake snake = new Snake(String.valueOf(UserId));
//			snakes.put(snake.hashCode(), snake);
//
//			System.out.println("本来蛇的hash" + snake.hashCode());
//
//			ponds.submit(new Runnable() {
//				@Override
//				public void run() {
//					//把蛇发给客户端后  游戏开始
//					try {
//						Send(snake);
//						System.out.println("lail");
//						game();
//						System.out.println("jieshul");
//					} catch (IOException e) {
//						e.printStackTrace();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			});
//			this.UserId++;  //控制蛇的编号
//		}
//	}
//
//	public void game() throws InterruptedException, IOException {
//		//游戏基本逻辑
//		while (true) {
//			String request = Accept();
//			System.out.println("requesr:" + request);
//			pocess(request);
//
////			snakes.get(pocess(request)).move();
////			for(Map.Entry<Integer,Snake> entry : snakes.entrySet()){
////				Send(entry.getValue());
////			}
//		}
//	}
//
//	public static void main(String[] args) throws IOException, InterruptedException {
//		EchoServe echoClient = new EchoServe();
//		echoClient.start();
//	}

	//开始接受TCP连接
	public void startTCPConnect() throws IOException {
		ServerSocket serverSocket = new ServerSocket(TCP_Port);
		while(true){
			Socket socket = serverSocket.accept();
			sockets.add(socket);
			socketAccepts.put(socket, new SocketAccept(socket));

			System.out.println("客户端通过TCP连接成功");
		}
	}

	/**
	 * 广播信号
	 */
	private void startBroadcast() throws IOException {
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
	public void start() throws IOException {
		//关闭广播
		this.closeBroadcast();
		//让客户端接受初始化对象
		for(Socket socket : sockets) {
			Snake snake = new Snake(String.valueOf(UserId));
			send(socket, snake);
			snakes.put(UserId++, snake);
		}
		//初始化服务器的对象
		for(int i = 1;i <= 5;i++) {
			foods.add(new Food(snakes));
			foods.add(new bigFood(snakes));
		}
		//跑
		while(true){

		}
	}

	public String acceptSockeyReturn(Socket socket) {
		SocketAccept socketAccept = socketAccepts.get(socket);
		return socketAccept.Get();
	}

	private void send(Socket socket, Draw draw) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(draw);
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
//			ObjectInputStream objectInputStream = null;
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
			return request;
		}
	}

	/**
	 * 类名, HashCode, 操作 , 数值
	 */
	private void pocess(String request){
		String[] arr = request.split(",");
		if(arr[0].equals("class com.base.Snake")) {
			Snake snake = snakes.get(Integer.parseInt(arr[1]));
			if (arr[2].equals("move")) {
				control.trun_To_SpeDir(snake, 'l', Integer.parseInt(arr[3]));
//				System.out.println(arr[1]);
//				snakes.get(arr[1]).move();
//				return Integer.parseInt(arr[1]);
			}
		}
	}
	private class Serve{

	}
}