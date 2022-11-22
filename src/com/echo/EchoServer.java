package com.echo;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

public class EchoServer {
	public static int UDP_Port = 9999;
	/**
	 * 通过广播的方式接受服务端的ip和端口号
	 * 建立TCP连接
	 */
	public static Socket connect() throws IOException {
		MulticastSocket socket = new MulticastSocket(9999);
		socket.joinGroup(new InetSocketAddress(InetAddress.getByName("225.0.1.1"),UDP_Port), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));

		byte[] buffer=new byte[1024*64];
		DatagramPacket packet=new DatagramPacket(buffer,buffer.length);

		socket.receive(packet);

		//获取端口号
		int len=packet.getLength();
		String rs=new String(buffer,0,len);

//		System.out.println("收到了IP为："+packet.getAddress()+"\n端口为："+packet.getPort()+" 的消息");
//		System.out.println("消息内容："+rs);

		//建立socket连接
		Socket serve = new Socket(packet.getAddress(), Integer.parseInt(rs));
		System.out.println("服务器连接成功");
		//关闭广播
		socket.close();
		return serve;
	}

	public static void main(String[] args) throws IOException {
		Socket serve = EchoServer.connect();
		InputStream inputStream = serve.getInputStream();
		Scanner scan = new Scanner(inputStream);
		while(true){
			if(scan.hasNext()){
				System.out.println(scan.next());
			}
		}
	}
}