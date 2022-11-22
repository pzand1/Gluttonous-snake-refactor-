package com.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class EchoServer {
	public static int UDP_Port = 9999;
	private Socket serve;
	/**
	 * 通过广播的方式接受服务端的ip和端口号
	 * 建立TCP连接
	 */
	public void connect() throws IOException {
		MulticastSocket socket = new MulticastSocket(9999);
		socket.joinGroup(new InetSocketAddress(InetAddress.getByName("225.0.1.1"),UDP_Port), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));

		byte[] buffer=new byte[1024*64];
		DatagramPacket packet=new DatagramPacket(buffer,buffer.length);

		socket.receive(packet);
		//获取端口号
		int len=packet.getLength();
		String rs=new String(buffer,0,len);

		//建立socket连接
		serve = new Socket(packet.getAddress(), Integer.parseInt(rs));
		System.out.println("服务器连接成功");
		//关闭广播
		socket.close();
	}




	private class Accept extends Thread{
		@Override
		public void run() {
			InputStream inputStream = null;
			try {
				inputStream = serve.getInputStream();
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
		OutputStream outputStream = serve.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(response);
		writer.flush();
	}


	public static void main(String[] args) throws IOException {
		EchoServer echoServer = new EchoServer();
		echoServer.connect();

	}
}