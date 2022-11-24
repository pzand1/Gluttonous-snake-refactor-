package com.echo;

import com.base.Draw;
import com.base.Entity;
import com.base.Snake;
import com.base.config;
import com.library.StdDraw;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServe  implements Serializable {
	public static int TCP_Port = 8000;  //规定TCP连接的端口号为8000,并建立TCP连接
	public static int UDP_Port = 9999;  //规定TCP连接的端口号为8000,并建立TCP连接
	private ServerSocket serverSocket;
	private static int UserId = 1;
	private Socket socket;
	private Draw draw;
	private Accept accept;
	private HashMap<Integer,Snake> snakes = new HashMap<>();
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
		while(true){
			socket =  this.broadCast();
			Snake snake = new Snake(String.valueOf(UserId));
			snakes.put(snake.hashCode(),snake);
			ponds.submit(new Runnable() {
				@Override
				public void run() {
					try {
						Send(snake);
//						for(Map.Entry<Snake,Integer> entry : snakes.entrySet()){
//							Send(entry.getKey());
//						}
						//Draw draw = Accept();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			this.UserId++;  //控制蛇的编号
		}
	}


	/**
	 * 广播信号,并建立连接
	 * @return 返回连接的socket
	 * @throws IOException
	 */
	public  Socket broadCast() throws IOException{
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
		public void run(){
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
	private class Accept extends Thread{
		public String request;
		@Override
		public void run() {
			InputStream inputStream = null;
			ObjectInputStream objectInputStream = null;
			try {
				inputStream = socket.getInputStream();
				Scanner scan = new Scanner(inputStream);
				request = scan.next();
			}  catch(IOException e) {
				e.printStackTrace();
			}
		}
		public String Get(){
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
	 * HashCode , 类名 , 操作 , 数值
	 * @param request
	 */
	private void pocess(String request){
		String[] arr = request.split(",");
		Snake snake = snakes.get(Integer.parseInt(arr[0]));
		if(arr[2].equals("move")){

		}
	}







	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServe echoClient = new EchoServe();
		echoClient.start();

//		Snake snake = new Snake();
//		for(int i = 0;i < 30;i++){
//			snake.getBody().add(new Entity(0,0));
//		}
//		echoClient.socket = echoClient.broadCast();//记得把serve关掉
//		echoClient.Send(snake);

	}
}