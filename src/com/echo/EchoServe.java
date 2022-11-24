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
	private Socket socket;
	private Draw draw;
	private Accept accept;
	private HashMap<Integer, Snake> snakes = new HashMap<>();
	private LinkedList<Food> Foods = new LinkedList<>();

	/**
	 * UDP广播建立连接  转换成TCP连接
	 *
	 * @return
	 * @throws IOException
	 */
	public void start() throws IOException, InterruptedException {
		System.out.println("TCP服务器已启动");
		//1.创建线程池来解决一个服务器多个客户端的问题
		ExecutorService ponds = Executors.newCachedThreadPool();
		serverSocket = new ServerSocket(TCP_Port);

		while (true) {
			socket = this.broadCast();
			for (int i = 0; i < 10; i++) {
				Food food = new Food();
				food.createFood(snakes);
				Foods.add(food);
			}
			Snake snake = new Snake(String.valueOf(UserId));
			snakes.put(snake.hashCode(), snake);

			System.out.println("本来蛇的hash" + snake.hashCode());

			ponds.submit(new Runnable() {
				@Override
				public void run() {
					//把蛇发给客户端后  游戏开始
					try {
						Send(snake);
						System.out.println("lail");
						game();
						System.out.println("jieshul");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			this.UserId++;  //控制蛇的编号
		}
	}

	public void game() throws InterruptedException, IOException {
		//游戏基本逻辑
		while (true) {
			String request = null;
			request = Accept();
			System.out.println("requesr:" + request);
			pocess(request);
//			snakes.get(pocess(request)).move();
//			for(Map.Entry<Integer,Snake> entry : snakes.entrySet()){
//				Send(entry.getValue());
//			}
		}
	}

	/**
	 * 广播信号,并建立连接
	 *
	 * @return 返回连接的socket
	 * @throws IOException
	 */
	public Socket broadCast() throws IOException {
		//开始广播端口号
		Thread broadcastPort = new BroadcastPort();
		broadcastPort.start();
		System.out.println("发送方启动");

		//ServerSocket serverSocket = new ServerSocket(TCP_Port);
		Socket request = serverSocket.accept();

		System.out.println("TCP连接成功");
		//中断广播
		broadcastPort.interrupt();
		return request;
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


	//接受数据类
	private class Accept extends Thread {
		public String request;

		@Override
		public void run() {
			InputStream inputStream = null;
			ObjectInputStream objectInputStream = null;
			try {
				inputStream = socket.getInputStream();
				Scanner scan = new Scanner(inputStream);
				request = scan.nextLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public String Get() {
			return request;
		}
	}

	/**
	 *
	 * @return 返回接受到的对象
	 * @throws InterruptedException
	 */
	public String Accept() throws InterruptedException {
		accept = new Accept();
		accept.start();
		accept.join();
		return accept.Get();
	}

	private void Send(Draw draw) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(draw);
		objectOutputStream.flush();
	}

	/**
	 * 类名, HashCode, 操作 , 数值
	 * @param request
	 */
	private void pocess(String request){
		String[] arr = request.split(",");
		if(arr[0].equals("class Snake")) {
			Snake snake = snakes.get(Integer.parseInt(arr[1]));
			if (arr[2].equals("move")) {
				control.trun_To_SpeDir(snake, 'l', Integer.parseInt(arr[3]));
				System.out.println(arr[1]);
				snakes.get(arr[1]).move();
				//return Integer.parseInt(arr[1]);
			}
		}

		//return 0;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServe echoClient = new EchoServe();
		echoClient.start();
	}
}