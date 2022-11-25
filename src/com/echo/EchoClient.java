package com.echo;

import com.base.Draw;

import java.io.*;
import java.net.*;

public class EchoClient implements Serializable  {
	public static int UDP_Port = 9999;
	private Socket serve;
	private Draw draw;
	private Accept accept1;
	/**
	 * 通过广播的方式接受服务端的ip和端口号
	 * 建立TCP连接
	 */
	public EchoClient() throws IOException {
		this.connect();
	}
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

	public class Accept extends Thread{
		@Override
		public void run() {
			InputStream inputStream = null;
			ObjectInputStream objectInputStream = null;
			try {
				inputStream = serve.getInputStream();
				objectInputStream = new ObjectInputStream(inputStream);
				draw = (Draw) objectInputStream.readObject();
				System.out.println(draw);
				System.out.println("接收到了");
			}  catch(IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public Draw accept() throws InterruptedException {
		accept1 = new Accept();
		accept1.start();
		accept1.join();
		return this.draw;
	}

	/**
	 * 发送操作
	 * @param request
	 * @throws IOException
	 */
	public void Send(String request) throws IOException {
		OutputStream outputStream = serve.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.write(request);
		writer.flush();
	}
}