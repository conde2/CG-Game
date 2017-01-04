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

import Engine.components.FreeMove;
import Engine.components.GameComponent;
import Engine.components.Sprite;
import Engine.core.CoreEngine;
import Engine.core.GameObject;
import Engine.core.Input;
import Engine.core.Vector3f;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import Engine.rendering.Texture;
import Engine.rendering.Window;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

public class Player extends GameComponent
{

	private ArrayList<GameObject> m_playerLifes;
	private GameObject m_lastLifeTaken = null;
	public int m_lifes = 3;
	public float m_blinkTimer = 0.0f;
	public float m_blinkInterval = 0.3f;
	public int m_blinkTimes = 0;
	
	public Player()
	{
	}
	
	@Override
	public void Start()
	{
		m_playerLifes = new ArrayList<GameObject>();

		for(int i = 0; i < m_lifes; i++)
		{
			GameObject playerLife = new GameObject();
			playerLife.GetTransform().SetPos(new Vector3f(Window.GetWidth() - m_lifes*25.0f + i*25, Window.GetHeight() - 15.0f, 1.0f));
			Sprite lifeSprite = new Sprite(new Texture("life.png"), 1.0f);
			lifeSprite.setScale(new Vector3f(0.1f, 0.1f, 1.0f));
			playerLife.AddComponent(lifeSprite);

			m_playerLifes.add(playerLife);
			GetParent().AddChild(playerLife);
		}
	}
	
	@Override
	public void Update(float delta)
	{
		
		if(m_blinkTimes > 0 && m_blinkTimer >= m_blinkInterval)
		{
			if (m_lastLifeTaken != null)
			{
				m_lastLifeTaken.SetEnabled(!m_lastLifeTaken.IsEnabled());
			}
			m_blinkTimer = 0.0f;
			m_blinkTimes--;
			
			if (m_blinkTimes <= 0)
			{
				m_lastLifeTaken.SetEnabled(false);				
			}
		}
		
		m_blinkTimer += delta;
		
	}
	
	@Override
	public void OnCollide(GameObject object)
	{
		
		if (object.GetTag() == "PowerUp")
		{
			object.SetEnabled(false);
			GetParent().GetComponent(FreeMove.class).setSpeed(200.0f);
		}
		else if (object.GetTag() == "GamePoint")
		{
			object.SetEnabled(false);	
		}
		else if (object.GetTag() == "Obstacle")
		{
			for(GameObject life : m_playerLifes)
			{
				if(life.IsEnabled())
				{
					m_blinkTimes = 5;
					m_lastLifeTaken = life;
					break;
				}
				
				// Player DEAD
			}
			
			object.SetEnabled(false);
		}
		
	}
}
