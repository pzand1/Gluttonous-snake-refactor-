package com.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServe {
	public static int TCP_Port = 8000;  //规定TCP连接的端口号为8000,并建立TCP连接
	public static int UDP_Port = 9999;  //规定TCP连接的端口号为8000,并建立TCP连接
	public ServerSocket serverSocket;
//	public Socket socket = null;
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
			//serverSocket相当于在外面接待客人，然后带进店里给socket
			//Socket socket = serverSocket.accept();
			Socket socket =  this.broadCast();
			ponds.submit(new Runnable() {
				@Override
				public void run() {
					Accept(socket);
				}
			});
		}
	}


	public  Socket broadCast() throws IOException, InterruptedException {
		//开始广播端口号
		Thread broadcastPort = new BroadcastPort();
		broadcastPort.start();
		System.out.println("发送方启动");

		System.out.println(InetAddress.getLocalHost());

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

	public void Accept(Socket socket){
		Thread a  = new Thread(()->{
			InputStream inputStream = null;
			try {
				inputStream = socket.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Scanner scan = new Scanner(inputStream);
			while(scan.hasNext()){
				System.out.println(scan.next());
			}
		});
		a.start();
	}

	private void Send(Socket socket,String response) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(response);
		writer.flush();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServe echoClient = new EchoServe();
		echoClient.start();
	}
}