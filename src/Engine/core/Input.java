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

package Engine.core;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import Engine.rendering.Window;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

public final class Input {


    private static final int KEYBOARD_SIZE = 512;
    private static final int MOUSE_SIZE = 16;

    private static int[] keyStates = new int[KEYBOARD_SIZE];
    private static boolean[] activeKeys = new boolean[KEYBOARD_SIZE];

    private static int[] mouseButtonStates = new int[MOUSE_SIZE];
    private static boolean[] activeMouseButtons = new boolean[MOUSE_SIZE];
    private static long lastMouseNS = 0;
    private static long mouseDoubleClickPeriodNS = 1000000000 / 5; //5th of a second for double click.

    private static int NO_STATE = -1;
    
    private static class InputLoad {
        private static final Input INSTANCE = new Input();
    }

    private Input() {
		ResetKeyboard();
		ResetMouse();

        if (InputLoad.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static Input getInstance() {
        return InputLoad.INSTANCE;
    }

    // The GLFWKeyCallback class is an abstract method that
    // can't be instantiated by itself and must instead be extended
    protected static GLFWKeyCallback keyboardCallback = new GLFWKeyCallback()
    {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods)
        {
            activeKeys[key] = action != GLFW_RELEASE;
            keyStates[key] = action;
        }
    };

    protected static GLFWMouseButtonCallback mouseCallback = new GLFWMouseButtonCallback()
    {
        @Override
        public void invoke(long window, int button, int action, int mods)
        {
            activeMouseButtons[button] = action != GLFW_RELEASE;
            mouseButtonStates[button] = action;
        }
    };
    
    public static GLFWKeyCallback GetKeyboardCallback()
    {
    	return keyboardCallback;
    }
    
    public static GLFWMouseButtonCallback GetMouseCallback()
    {
    	return mouseCallback;
    }
    
    private static void ResetKeyboard()
    {
        for (int i = 0; i < keyStates.length; i++)
        {
            keyStates[i] = NO_STATE;
        }
    }

    private static void ResetMouse()
    {
        for (int i = 0; i < mouseButtonStates.length; i++)
        {
            mouseButtonStates[i] = NO_STATE;
        }

        long now = System.nanoTime();

        if (now - lastMouseNS > mouseDoubleClickPeriodNS)
            lastMouseNS = 0;
    }

	public static void Update()
	{		
		ResetKeyboard();
		ResetMouse();
		
		glfwPollEvents();
	}
	
	
	
    public static boolean IsKeyDown(int key)
    {
        return activeKeys[key];
    }

    public static boolean IsKeyPressed(int key)
    {
        return keyStates[key] == GLFW_PRESS;
    }

    public static boolean IsKeyReleased(int key)
    {
        return keyStates[key] == GLFW_RELEASE;
    }

    public static boolean IsMouseButtonDown(int button)
    {
        return activeMouseButtons[button];
    }

    public static boolean IsMouseButtonPressed(int button)
    {
        return mouseButtonStates[button] == GLFW_RELEASE;
    }

    public static boolean IsMouseButtonReleased(int button)
    {
        boolean flag = mouseButtonStates[button] == GLFW_RELEASE;

        if (flag)
            lastMouseNS = System.nanoTime();

        return flag;
    }

    public static boolean IsMouseButtonDoubleClicked(int button)
    {
        long last = lastMouseNS;
        boolean flag = IsMouseButtonReleased(button);

        long now = System.nanoTime();

        if (flag && now - last < mouseDoubleClickPeriodNS)
        {
            lastMouseNS = 0;
            return true;
        }

        return false;
    }
	
	public static Vector2f GetMousePosition()
	{
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Window.getCurrentWindow(), xBuffer, yBuffer);
		
		float x = (float)xBuffer.get(0);
		float y = (float)yBuffer.get(0);
		return new Vector2f(x,y);
	}
	
	public static void SetMousePosition(Vector2f pos)
	{
		glfwSetCursorPos(Window.getCurrentWindow(), pos.GetX(), pos.GetY());
	}

}
