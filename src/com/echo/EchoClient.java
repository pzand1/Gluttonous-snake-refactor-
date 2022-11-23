package com.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

import static com.echo.EchoServe.TCP_Port;

public class EchoClient {
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

	public void accept(){
		Thread a = new Thread(()->{
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
		});
		a.start();
	}

	private void Send(String response) throws IOException {
		OutputStream outputStream = serve.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(response);
		writer.flush();
	}

	public static void main(String[] args) throws IOException {
//		EchoClient echoServer = new EchoClient();
//		echoServer.connect();
//		echoServer.accept();
//		for(int i = 0;i < 10;i++){
//			echoServer.Send("9999");
//		}
		Socket socket =  new Socket("127.0.0.1",TCP_Port);
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		for(int i = 0;i < 10;i++){
			writer.println("1234");
			writer.flush();
		}


		Socket socket1 =  new Socket("127.0.0.1",TCP_Port);
		OutputStream outputStream1 = socket1.getOutputStream();
		PrintWriter writer1 = new PrintWriter(outputStream1);
		writer1.println("aaaa");
		writer1.flush();
	}
}