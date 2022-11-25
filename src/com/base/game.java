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
import com.library.StdDraw;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class game {
	static int n = 0;

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


		EchoClient echoClient = new EchoClient();
		Draw snake = echoClient.accept();
		snake.draw();
		StdDraw.show();
		//类名, HashCode, 操作 , 数值
		System.out.println(snake.getClass() + "," + snake.hashCode() + ","
				+ "move" + "," + ((Snake)snake).hasdAgree);
		echoClient.Send(snake.getClass()+ "," + snake.hashCode() + ","
						+ "move" + "," + ((Snake)snake).hasdAgree + "\n");

		while(true) {
			Draw draw = echoClient.accept();
			draw.draw();
			StdDraw.show();
			Thread.sleep(10);
		}

		//echoClient.Send(snake);
//        Snake snakes = snakess.getLast();
//        snakess.getLast().draw();
//        StdDraw.show();
//        if (config.mouse_control)
//            control.mouse_control(snakess);
//        else if(config.Key_control)
//            control.ThreadTest(snakess);
//
////        while (!snakes.isdead(snakess)) {
//        while (true) {
//            StdDraw.clear();
//            for (Snake s : snakess) {
//                s.draw();
//                s.move();
//                s.eatFood(snakess, foods);
//            }
//            for (Food f : foods) {
//                //f.draw();
//            }
//            StdDraw.show();
//            StdDraw.pause(config.dT);
//        }
	}
}
