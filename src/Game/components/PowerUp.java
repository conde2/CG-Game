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

package Game.components;

import Engine.collision.*;
import Engine.components.GameComponent;
import Engine.components.Sprite;
import Engine.core.GameObject;
import Engine.core.Vector3f;
import Engine.rendering.Texture;
import Engine.rendering.Window;

import java.util.ArrayList;
import java.util.Random;

public class PowerUp extends GameComponent
{

	
	private float m_timer = 0.0f;
	private float m_interval = 3.0f;
	private float m_dropSpeed = 40.0f;
	public ArrayList<GameObject> m_powerUps;

	public PowerUp()
	{

	}
	
	@Override
	public void Start()
	{

	}

	public void SetPowerUpType()
	{
		
	}
		
	@Override
	public void Update(float delta)
	{
		GetTransform().SetPos(GetTransform().GetPos().Add(new Vector3f(0.0f, -m_dropSpeed * delta, 0.0f)));
		
		if( GetTransform().GetPos().GetY() < -Window.GetHeight())
		{
			GetParent().SetEnabled(false);
		}
	}
}

