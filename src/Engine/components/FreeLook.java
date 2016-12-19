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

package Engine.components;

import Engine.core.Input;
import Engine.core.Vector2f;
import Engine.core.Vector3f;
import Engine.rendering.Window;

import static org.lwjgl.glfw.GLFW.*;

public class FreeLook extends GameComponent
{
	private static final Vector3f Y_AXIS = new Vector3f(0,1,0);

	private boolean m_mouseLocked = false;
	private float   m_sensitivity;
	private int     m_unlockMouseKey;

	public FreeLook(float sensitivity)
	{
		this(sensitivity, GLFW_KEY_E);
	}

	public FreeLook(float sensitivity, int unlockMouseKey)
	{
		this.m_sensitivity = sensitivity;
		this.m_unlockMouseKey = unlockMouseKey;
	}

	@Override
	public void Input(float delta)
	{
		Vector2f centerPosition = new Vector2f(Window.GetWidth()/2, Window.GetHeight()/2);

		if(Input.IsKeyPressed(m_unlockMouseKey))
		{
			Input.SetCursorEnabled();
			m_mouseLocked = false;
		}
		if(Input.IsMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT))
		{
			Input.SetMousePosition(centerPosition);
			Input.SetCursorDisabled();
			m_mouseLocked = true;
		}

		if(m_mouseLocked)
		{
			Vector2f deltaPos = Input.GetMousePosition().Sub(centerPosition);

			boolean rotY = deltaPos.GetX() != 0;
			boolean rotX = deltaPos.GetY() != 0;

			if(rotY)
				GetTransform().Rotate(Y_AXIS, (float) Math.toRadians(deltaPos.GetX() * m_sensitivity));
			if(rotX)
				GetTransform().Rotate(GetTransform().GetRot().GetRight(), (float) Math.toRadians(-deltaPos.GetY() * m_sensitivity));

			if(rotY || rotX)
				Input.SetMousePosition(centerPosition);
		}
	}
}
