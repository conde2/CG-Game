package com.joaocosta.game;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import Maths.Vector2f;
import Maths.Vector3f;
import Geometry.Circle;
import Geometry.Line;
import Geometry.Line.*;
import Geometry.Rect;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main implements Runnable {

	private int WIDTH = 640;
	private int HEIGHT = 480;
	
	private Thread thread;
	private boolean running = false;
	private long window;
	
	private GLFWKeyCallback keyCallback;
	
	@Override
	public void run() {
		
		init();
		while(running)
		{
			update();
			render();
			
			if (glfwWindowShouldClose(window))
			{
				running = false;
			}
		}
		
		keyCallback.free();
	}
	
	void start()
	{
		running = true;
		thread = new Thread(this, "GameCore");
		thread.start();	
	}
	
	void init()
	{
		// Initialise GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, "Game!", NULL, NULL);
		if (window == NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
			window,
			(vidmode.width() - WIDTH) / 2,
			(vidmode.height() - HEIGHT) / 2
		);

		// Set our key callback
		glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		// Set the clear color
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		
		
	}
	
	void update()
	{
		glfwPollEvents();
		
		if(KeyboardHandler.isKeyDown(GLFW_KEY_SPACE))
            System.out.println("Space Key Pressed");
	}
	
	void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
				
		
		// Draw after clear the screen
		Line line = new Line(new Vector2f(0.0f, 20.0f), new Vector2f(50.0f, 40.0f));
		//line.setColor(1.0f, 0.0f, 0.0f);
		line.draw();
		
		Circle circle = new Circle(100.0f, 100.0f, 15.0f);
		//circle.setColor(1.0f,0.0f,0.0f);
		circle.setFill(false);
		circle.draw();

		Rect rect = new Rect(new Vector2f(150.0f, 100.0f), new Vector2f(30.0f, 30.0f));
		//rect.setColor(1.0f,0.0f,0.0f);
		rect.setFill(false);
		rect.draw();
		
		glfwSwapBuffers(window);

		
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}