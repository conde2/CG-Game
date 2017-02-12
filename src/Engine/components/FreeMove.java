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
import Engine.core.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class FreeMove extends GameComponent
{
	private float m_speed;
	private int   m_forwardKey;
	private int   m_backKey;
	private int   m_leftKey;
	private int   m_rightKey;

	public FreeMove(float speed)
	{
		this(speed, GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D);
	}

	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey)
	{
		this.m_speed = speed;
		this.m_forwardKey = forwardKey;
		this.m_backKey = backKey;
		this.m_leftKey = leftKey;
		this.m_rightKey = rightKey;
	}

	@Override
	public void Input(float delta)
	{
		float movAmt = m_speed * delta;

		if(Input.IsKeyDown(m_forwardKey))
			Move(GetTransform().GetRot().GetForward(), movAmt);
		if(Input.IsKeyDown(m_backKey))
			Move(GetTransform().GetRot().GetBack(), movAmt);
		if(Input.IsKeyDown(m_leftKey))
			Move(GetTransform().GetRot().GetLeft(), movAmt);
		if(Input.IsKeyDown(m_rightKey))
			Move(GetTransform().GetRot().GetRight(), movAmt);
		//LIMITANDO MOVIMENTO
		if(GetTransform().GetPos().GetX()>66.0f)
			GetTransform().GetPos().SetX(66.0f);
		if(GetTransform().GetPos().GetX()<0.0f)
			GetTransform().GetPos().SetX(0.0f);
		if(GetTransform().GetPos().GetZ()>66.0)
			GetTransform().GetPos().SetZ(66.0f);
		if(GetTransform().GetPos().GetZ()<0.0f)
			GetTransform().GetPos().SetZ(0.0f);
		
		
	}
	
	

	private void Move(Vector3f dir, float amt)
	{
		GetTransform().SetPos(GetTransform().GetPos().Add(dir.Mul(amt)));
	}
	public void setSpeed(float speed){
		m_speed=speed;
	}
}
