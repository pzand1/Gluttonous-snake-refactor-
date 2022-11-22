package com.echo;

import com.base.Entity;
import com.base.Snake;
import com.base.config;
import com.library.StdDraw;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
	public static int TCP_Port = 8000;  //规定TCP连接的端口号为8000,并建立TCP连接
	public static int UDP_Port = 9999;  //规定TCP连接的端口号为8000,并建立TCP连接
	public Socket socket = null;
	/**
	 * UDP广播建立连接  转换成TCP连接
	 *
	 * @return
	 * @throws IOException
	 */
	public void broadCast() throws IOException, InterruptedException {
		//开始广播端口号
		Thread broadcastPort = new BroadcastPort();
		broadcastPort.start();
		System.out.println("发送方启动");

		ServerSocket serverSocket = new ServerSocket(TCP_Port);
		Socket request = serverSocket.accept();
		System.out.println("TCP连接成功");
		//中断广播
		broadcastPort.interrupt();
		socket = request;
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

	private class Accept extends Thread{
		@Override
		public void run() {
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
		}
	}
	private void Send(String response) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(response);
		writer.flush();
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoClient echoClient = new EchoClient();
		echoClient.broadCast();

	}
}