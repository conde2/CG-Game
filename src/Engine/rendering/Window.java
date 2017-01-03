/*
 * Copyright (C) 2014 Benny Bobaganoosh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Engine.rendering;

import Engine.core.Input;
import Engine.core.Vector2f;
import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
//import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;


public class Window 
{
	private static long window;
	private static String windowTitle;
	
	// When resize change the viewport
    private static final GLFWFramebufferSizeCallback FRAMEBUFFER_SIZE_CALLBACK = new GLFWFramebufferSizeCallback(){
        @Override
        public void invoke(long window, int width, int height) {
            glViewport(0, 0, width, height);
        }
    };
    
    /*
    private static final GLFWWindowSizeCallback WINDOW_SIZE_CALLBACK = new GLFWWindowSizeCallback(){
        @Override
        public void invoke(long window, int width, int height) {
        }
    };
    */
	
	public static void CreateWindow(int width, int height, String title)
	{
		
		// Initialise GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		windowTitle = title;
		
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// Center our window
		glfwSetWindowPos(
			window,
			(vidmode.width() - width) / 2,
			(vidmode.height() - height) / 2
		);
		
		//glfwSetWindowSizeCallback(window, WINDOW_SIZE_CALLBACK);
		glfwSetFramebufferSizeCallback(window, FRAMEBUFFER_SIZE_CALLBACK);
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		glViewport(0, 0, width, height); //NEW
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, 0, height, -1, 1);

		glfwSetKeyCallback(window, Input.GetKeyboardCallback());
		glfwSetMouseButtonCallback(window, Input.GetMouseCallback());

		
	}
	
	public static long getCurrentWindow()
	{
		return window;
	}
	
	public static void Clear()
	{
		glEnable(GL_DEPTH_TEST); 
		glDepthFunc(GL_LEQUAL);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0);
		glClearDepth(1.0f); 
	    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	}
	
	public static void Render()
	{
		glfwSwapBuffers(window);

	}
	
	public static void Dispose()
	{
		//glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public static boolean IsCloseRequested()
	{
		return glfwWindowShouldClose(window);
	}
	
	public static int GetWidth()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);;
		glfwGetWindowSize(window, width, height);
		return width.get(0);
	}
	
	public static int GetHeight()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);;
		glfwGetWindowSize(window, width, height);
		return height.get(0);
	}
	
	public static String GetTitle()
	{
		return windowTitle;
	}
	
	public static void SetTitle(String title)
	{
		glfwSetWindowTitle(window, title);
	}

	public Vector2f GetCenter()
	{
		return new Vector2f(GetWidth()/2, GetHeight()/2);
	}
}
