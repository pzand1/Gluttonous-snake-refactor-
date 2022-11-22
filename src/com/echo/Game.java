package com.echo;

import com.base.Entity;
import com.base.Snake;
import com.base.config;
import com.library.StdDraw;

import java.io.IOException;
import java.net.Socket;

public class Game {
	public static void main(String[] args) throws IOException {
		Thread a = new Thread(()->{
			EchoClient echoClient = new EchoClient();
			Socket socket = null;
			try {
				socket = echoClient.broadCast();
			} catch (IOException e) {
				e.printStackTrace();
			}

			while (true) {
				try {
					echoClient.acceptDate(socket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		a.start();
		Snake snake = new Snake();
		for (int i = 0; i < 30; i++) {
			snake.getBody().add(new Entity(0.0, 0.0));
		}
		EchoServer echoServer = new EchoServer();
		echoServer.Connect();

		while(true){
			String response = echoServer.SendDate(snake.getBody());
			System.out.println(response);
		}


//		String response = "";
//		StdDraw.enableDoubleBuffering();
//		StdDraw.setCanvasSize(config.canvasWidth, config.canvasHeight);
//		StdDraw.setXscale(-config.x_Size, config.x_Size);
//		StdDraw.setYscale(-config.y_Size, config.y_Size);
//
//		while(true){
//			snake.move();
//			response = echoServer.SendDate(snake.getBody());
//			System.out.println(response);
//			snake.draw(response);
//			StdDraw.show();
//		}
	}
}
