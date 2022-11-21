package com.echo;


import com.base.Entity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.LinkedList;

public class EchoServer {
	public static Socket serve = null;//客户端通过serve给服务器发送数据

	/**
	 * 使客户端和服务器建立连接
	 * @throws IOException
	 */
	public void Connect() throws IOException {
//		MulticastSocket socket=new MulticastSocket(9999);
//		socket.joinGroup(new InetSocketAddress(InetAddress.getByName("224.0.1.1"),9999), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
//
//		byte[] buffer=new byte[1024*64];
//		DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
//
//		socket.receive(packet);
//
//		int len=packet.getLength();
//		String rs=new String(buffer,0,len);
//
//		System.out.println("收到了IP为："+packet.getAddress()+"\n端口为："+packet.getPort()+" 的消息");
//		System.out.println("消息内容："+rs);

//		Socket serve = new Socket(packet.getAddress(), Integer.parseInt(rs));
		serve = new Socket("127.0.0.1", 8000);
		System.out.println("服务器连接成功");

		//socket.close();
	}

	/**
	 * 客户端把蛇的坐标发送给服务器
	 * @param list 蛇的body
	 * @throws IOException
	 */
	public void SendDate(LinkedList<Entity> list) throws IOException {
		String request = "";
		for(int i = 0;i < list.size();i++){
			request+=list.get(i).x;
			request+=list.get(i).y;
		}
		try(OutputStream outputStream = serve.getOutputStream();){
			PrintWriter writer = new PrintWriter(outputStream);
			writer.println(request);
			writer.flush();
		}finally {
			serve.close();
		}
	}


	public static void main(String[] args) throws Exception {
		System.out.println("=====接收端启动======");
		LinkedList<Entity> list = new LinkedList<>();
		for(int i = 0;i < 30;i++){
			list.add(new Entity(0.0,0.0));
		}

		EchoServer echoServer = new EchoServer();
		echoServer.Connect();
		echoServer.SendDate(list);
	}
}