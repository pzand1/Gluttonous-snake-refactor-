package com.base;//import java.util.LinkedList;
//
//public class test.game {
//    static int n = 0;
//
//    public static void main(String[] args) throws InterruptedException {
//
//        LinkedList<test.Snake> snakess = new LinkedList<>();
//        snakess.add(new test.Snake(0, 0, "???"));
//
//
//        for(int i = 0;i < 30;i++){
//            snakess.getLast().getBody().add(new test.Entity(0,0));
//        }
//        LinkedList<test.Food> foods = new LinkedList<>();
//        foods.add(new test.Food());
//        foods.add(new test.bigFood());
//
//        Library.StdDraw.enableDoubleBuffering();
//        Library.StdDraw.setCanvasSize(test.config.canvasWidth, test.config.canvasHeight);
//        Library.StdDraw.setXscale(-test.config.x_Size, test.config.x_Size);
//        Library.StdDraw.setYscale(-test.config.y_Size, test.config.y_Size);
//
//
//        test.Snake snakes = snakess.getLast();
//        snakess.getLast().draw();
//        Library.StdDraw.show();
//        if (test.config.mouse_control)
//            test.control.mouse_control(snakess);
//        else if(test.config.Key_control)
//            test.control.ThreadTest(snakess);
//
////        while (!snakes.isdead(snakess)) {
//        while (true) {
//            Library.StdDraw.clear();
//            for (test.Snake s : snakess) {
//                s.draw();
//                s.move();
//                s.eatFood(snakess, foods);
//            }
//            for (test.Food f : foods) {
//                f.draw();
//            }
//            Library.StdDraw.show();
//            Library.StdDraw.pause(test.config.dT);
//        }
//    }
//}


import com.echo.EchoClient;
import com.echo.EchoServe;
import com.library.StdDraw;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class game {
	public static ReentrantLock r1 = new ReentrantLock();
	public static void main(String[] args) throws InterruptedException, IOException {

//        LinkedList<Snake> snakess = new LinkedList<>();
//        snakess.add(new Snake(0, 0, "???"));
//
//
//        for(int i = 0;i < 30;i++){
//            snakess.getLast().getBody().add(new Entity(0,0));
//        }
//        LinkedList<Food> foods = new LinkedList<>();
//        foods.add(new Food());
//        foods.add(new bigFood());

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
			snake.draw();
			LinkedList<Draw> drawList = client.accept();
			for(Draw draws : drawList){
				draws.draw();
			}
			StdDraw.show();

			r1.lock();

			client.Send(snake.getClass()+ "," + snake.hashCode() + ","
					+ "move" + "," + snake.hasdAgree + "\n");
			System.out.println(snake.hasdAgree);
			snake.move();

			r1.unlock();

			Thread.sleep(20);
		}
	}
}
