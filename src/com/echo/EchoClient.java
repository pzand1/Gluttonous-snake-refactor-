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
	public static Snake[] snake = new Snake[2];
	public static int k = 0;  // 目前蛇的数量

	public EchoClient() {
	}

	/**
	 * UDP广播建立连接  转换成TCP连接
	 *
	 * @return
	 * @throws IOException
	 */
	public Socket broadCast() throws IOException {
		DatagramSocket socket = null;
		ServerSocket serverSocket = null;
		System.out.println("发送方启动");
		Thread a = new Thread(() -> {
			String str = "8000";
			DatagramPacket datagramPacket = null;
			try {
				datagramPacket = new DatagramPacket(str.getBytes(), str.getBytes().length,
						InetAddress.getByName("255.255.255.255"), 9999);
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
					//e.printStackTrace();
					break;
				}
			}
		});
		//a.start();
		String str = "8000";  //规定TCP连接的端口号为8000,并建立TCP连接
		//serverSocket = new ServerSocket(Integer.parseInt(str));
		serverSocket = new ServerSocket(8000);
		Socket request = serverSocket.accept();
		System.out.println("TCP连接成功");
		a.interrupt();
		return request;
	}


	/**
	 * 根据socket接受数据
	 *
	 * @param socket TCP连接 accept传回来的socket
	 * @throws IOException
	 */
	public void acceptDate(Socket socket) throws IOException {
		try (InputStream inputStream = socket.getInputStream();) {
			//利用Scanner和PrintWriter简化代码
			Scanner scan = new Scanner(inputStream);
			while (scan.hasNext()) {
				String request = scan.next();
				pocess(request);
			}
		} finally {
			socket.close();
		}
	}

	/**
	 * 解析客户端传来的数据并添加进自身的蛇数组
	 *
	 * @param str 客户端传过来的蛇的坐标字符串
	 */
	public void pocess(String str) {
		snake[k] = new Snake();
		String x = "";
		String y = "";
		for (int i = 0; i < str.length(); i = i + 6) {
			x = str.substring(i, i + 3);
			y = str.substring(i + 3, i + 6);
			snake[k].getBody().add(new Entity(Double.parseDouble(x), Double.parseDouble(y)));
		}
		k++;
	}


	/**
	 * 服务器发送数据
	 *
	 * @param socket 利用socket给客户端所有蛇的数据
	 * @throws IOException
	 */
	public void sendDate(Socket socket, String response) throws IOException {
		try (OutputStream outputStream = socket.getOutputStream();) {
			PrintWriter writer = new PrintWriter(outputStream);
			writer.println(response);
			writer.flush();
		} finally {
			socket.close();
		}
	}

	/**
	 *
	 * @return 返回所有蛇坐标构成的字符串
	 */
	public String getSnake() {
		String str = "";
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < snake[i].getBody().size(); j++) {
				str += snake[i].getBody().get(j).x;
				str += snake[i].getBody().get(j).y;
			}
		}
		return str;
	}

	public static void main(String[] args) throws IOException {
		EchoClient echoClient = new EchoClient();
		Socket socket = echoClient.broadCast();
		echoClient.acceptDate(socket);


		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(config.canvasWidth, config.canvasHeight);
		StdDraw.setXscale(-config.x_Size, config.x_Size);
		StdDraw.setYscale(-config.y_Size, config.y_Size);
		snake[0].draw();
		StdDraw.show();
	}
}