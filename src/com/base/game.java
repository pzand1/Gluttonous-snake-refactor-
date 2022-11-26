package com.base;
import com.echo.EchoClient;
import com.library.StdDraw;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class game {
	public static ReentrantLock r1 = new ReentrantLock();
	public static void main(String[] args) throws InterruptedException, IOException {

		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(config.canvasWidth, config.canvasHeight);
		StdDraw.setXscale(-config.x_Size, config.x_Size);
		StdDraw.setYscale(-config.y_Size, config.y_Size);


		EchoClient client = new EchoClient();
		Snake snake = (Snake) client.accept().getFirst();
		snake.draw();
		StdDraw.show();
		control.mouse_control(snake);
//		client.Send(snake.getClass()+ "," + snake.hashCode() + ","
//				+ "move" + "," + snake.hasdAgree + "\n");
		//类名, HashCode, 操作 , 数值

		while(true) {
			StdDraw.clear();
//			snake.draw();
			LinkedList<Draw> drawList = client.accept();
			for(Draw draws : drawList){
				draws.draw();
			}
			StdDraw.show();

			r1.lock();

			client.Send(snake.getClass()+ "," + snake.hashCode() + ","
					+ "move" + "," + snake.hasdAgree + "\n");
			snake.move();

			r1.unlock();

			Thread.sleep(20);
		}
	}
}
