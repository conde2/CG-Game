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

import java.util.concurrent.ThreadLocalRandom;

public class GameManager extends GameComponent
{

	private float m_powerUpTimer = 0.0f;
	private float m_powerUpInterval = 1.0f;
	
	private float m_gamePointsTimer = 0.0f;
	private float m_gamePointsInterval = 3.0f;
	
	public ArrayList<GameObject> m_powerUps;
	public ArrayList<GameObject> m_gamePoints;
	
	public GameManager()
	{
		
	}
	
	@Override
	public void Start()
	{
		m_powerUps = new ArrayList<GameObject>();

		for(int i = 0; i < 5; i++)
		{
			GameObject powerUp = new GameObject();
			powerUp.SetEnabled(false);
			powerUp.SetTag("PowerUp");
			
			Sprite powerUpSprite = new Sprite(new Texture("powerup.png"), -0.1f);
			powerUpSprite.setScale(new Vector3f(0.28f, 0.28f, 1.0f));
			
			BoundingSphere boundingSphere = new BoundingSphere(new Vector3f(100.0f, 100.0f, 0.0f), 15);
			Collider collider = new Collider(boundingSphere);
			
			PowerUp powerUpComponent = new PowerUp();


			powerUp.AddComponent(collider);
			powerUp.AddComponent(powerUpComponent);
			powerUp.AddComponent(powerUpSprite);

			GetParent().AddChild(powerUp);
			m_powerUps.add(powerUp);
		}
		
		
		m_gamePoints = new ArrayList<GameObject>();

		for(int i = 0; i < 5; i++)
		{
			GameObject gamePoint = new GameObject();
			gamePoint.SetEnabled(false);
			gamePoint.SetTag("GamePoint");
			
			Sprite gamePointSprite = new Sprite(new Texture("point.png"), -0.1f);
			gamePointSprite.setScale(new Vector3f(0.15f, 0.15f, 1.0f));
			
			BoundingSphere boundingSphere = new BoundingSphere(new Vector3f(100.0f, 100.0f, 0.0f), 15);
			Collider collider = new Collider(boundingSphere);
			
			PowerUp powerUpComponent = new PowerUp();


			gamePoint.AddComponent(collider);
			gamePoint.AddComponent(powerUpComponent);
			gamePoint.AddComponent(gamePointSprite);

			GetParent().AddChild(gamePoint);
			m_gamePoints.add(gamePoint);
		}
	}
	
	public void Spawn(GameObject object)
	{

		int x = ThreadLocalRandom.current().nextInt(20, Window.GetWidth() - 20);
		int y = Window.GetHeight();
		object.GetTransform().SetPos(new Vector3f(x, y, 0.0f));
		object.SetEnabled(true);	
	}

		
	@Override
	public void Update(float delta)
	{
		if(m_powerUpTimer >= m_powerUpInterval)
		{
			for(GameObject powerUp : m_powerUps)
			{
				if(!powerUp.IsEnabled())
				{
					Spawn(powerUp);
					break;
				}
			}
			m_powerUpTimer = 0.0f;
		}
		
		if(m_gamePointsTimer >= m_gamePointsInterval)
		{
			for(GameObject gamePoint : m_gamePoints)
			{
				if(!gamePoint.IsEnabled())
				{
					Spawn(gamePoint);
					break;
				}
			}
			m_gamePointsTimer = 0.0f;
		}
		
		m_gamePointsTimer += delta;
		m_powerUpTimer += delta;
	}
}

