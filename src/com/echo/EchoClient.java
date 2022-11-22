//package com.echo;
//
//import com.base.*;
//import com.library.StdDraw;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.*;
//import java.util.LinkedList;
//import java.util.Scanner;
//
//public class EchoClient {
//	public static LinkedList<Snake> snakes = new LinkedList<>();
//	public static int k = 0;  // 目前蛇的数量
//	LinkedList<Food> Foods = new LinkedList<>();
//
//	public EchoClient() {
//		//Foods.add(new Food().createFood(snakes));
//		Foods.add(new bigFood());
//	}
//
//	/**
//	 * UDP广播建立连接  转换成TCP连接
//	 *
//	 * @return
//	 * @throws IOException
//	 */
//	public Socket broadCast() throws IOException {
//		DatagramSocket socket = null;
//		ServerSocket serverSocket = null;
//		System.out.println("发送方启动");
//		Thread a = new Thread(() -> {
//			String str = "8000";
//			DatagramPacket datagramPacket = null;
//			try {
//				datagramPacket = new DatagramPacket(str.getBytes(), str.getBytes().length,
//						InetAddress.getByName("255.255.255.255"), 9999);
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			}
//			while (true) {
//				try {
//					socket.send(datagramPacket);
//					Thread.sleep(1000);
//				} catch (IOException e) {
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					//e.printStackTrace();
//					break;
//				}
//			}
//		});
//		//a.start();
//		String str = "8000";  //规定TCP连接的端口号为8000,并建立TCP连接
//		//serverSocket = new ServerSocket(Integer.parseInt(str));
//		serverSocket = new ServerSocket(8000);
//		Socket request = serverSocket.accept();
//		System.out.println("TCP连接成功");
//		a.interrupt();
//		return request;
//	}
//
//
//	/**
//	 * 根据socket接受数据并传回去
//	 *
//	 * @param socket TCP连接 accept传回来的socket
//	 * @throws IOException
//	 */
//	public void acceptDate(Socket socket) throws IOException {
//		InputStream inputStream = socket.getInputStream();
//		OutputStream outputStream = socket.getOutputStream();
//		Scanner scan = new Scanner(inputStream);
//		PrintWriter writer = new PrintWriter(outputStream);
//
//		String request = "";
//		String response = "";
//		while(scan.hasNext()){
//			request = scan.next();
//			PocessClient(request);//处理数据
//			response = getSnake();
//			writer.println(response);
//			writer.flush();
//		}
//	}
//
//	/**
//	 * 客户端处理数据
//	 *
//	 * @param str 客户端传过来的蛇的坐标字符串/食物坐标？
//	 */
//	public void PocessClient(String str) {
//		Snake snake = new Snake();
//		String x = "";
//		String y = "";
//		for (int i = 0; i < str.length(); i = i + 6) {
//			x = str.substring(i, i + 3);
//			y = str.substring(i + 3, i + 6);
//			snake.getBody().add(new Entity(Double.parseDouble(x), Double.parseDouble(y)));
//		}
//		snakes.add(snake);
//		k++;
//	}
//
//	/**
//	 * @return 返回所有蛇坐标构成的字符串
//	 */
//	public String getSnake() {
//		String str = "";
//		for (Snake snake : snakes) {
//			for (int i = 0; i < snake.getBody().size(); i++) {
//				str += snake.getBody().get(i).x;
//				str += snake.getBody().get(i).y;
//			}
//		}
//		return str;
//	}
//}


package com.echo;

		import com.base.*;
		import com.library.StdDraw;

		import java.io.IOException;
		import java.io.InputStream;
		import java.io.OutputStream;
		import java.io.PrintWriter;
		import java.net.*;
		import java.util.LinkedList;
		import java.util.Scanner;

public class EchoClient {
	public static LinkedList<Snake> snakes = new LinkedList<>();
	public static int k = 0;  // 目前蛇的数量
	LinkedList<Food> Foods = new LinkedList<>();

	public EchoClient() {
		//Foods.add(new Food().createFood(snakes));
		Foods.add(new bigFood());
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
		a.start();
		String str = "8000";  //规定TCP连接的端口号为8000,并建立TCP连接
		serverSocket = new ServerSocket(Integer.parseInt(str));
		//serverSocket = new ServerSocket(8000);
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
		InputStream inputStream = socket.getInputStream();
		Scanner scan = new Scanner(inputStream);
		while(scan.hasNext()){
			String request = scan.next();
			PocessClient(request);
		}


	}

	/**
	 * 客户端处理数据
	 *
	 * @param str 客户端传过来的蛇的坐标字符串/食物坐标？
	 */
	public void PocessClient(String str) {
		Snake snake = new Snake();
		String x = "";
		String y = "";
		for (int i = 0; i < str.length(); i = i + 6) {
			x = str.substring(i, i + 3);
			y = str.substring(i + 3, i + 6);
			snake.getBody().add(new Entity(Double.parseDouble(x), Double.parseDouble(y)));
		}
		snakes.add(snake);
		k++;
	}


	/**
	 * 服务器发送数据
	 *
	 * @param socket 利用socket给客户端发送数据
	 * @throws IOException
	 */
	public void sendDate(Socket socket, String response) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(response);
		writer.flush();
	}

	/**
	 * @return 返回所有蛇坐标构成的字符串
	 */
	public String getSnake() {
		String str = "";
		for (Snake snake : snakes) {
			for (int i = 0; i < snake.getBody().size(); i++) {
				str += snake.getBody().get(i).x;
				str += snake.getBody().get(i).y;
			}
		}
		return str;
	}

	public static void main(String[] args) throws IOException {
		EchoClient echoClient = new EchoClient();
		Socket socket = echoClient.broadCast();
		while (true) {
			String str = "123456789";
			echoClient.sendDate(socket, str);
		}
	}
}