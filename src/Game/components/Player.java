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
import Engine.core.GameObject;
import Engine.core.Vector3f;
import Engine.rendering.Texture;
import Engine.rendering.Window;

import java.util.ArrayList;
import java.util.Vector;

public class Player extends GameComponent
{

	private ArrayList<GameObject> m_playerLifes;
	private ArrayList<GameObject> m_playerScore;
	private Vector<Sprite> m_numberSprites;
	private ArrayList<Texture> m_textures;
	private GameObject m_lastLifeTaken = null;
	public int m_lifes = 3;
	public float m_blinkTimer = 0.0f;
	public float m_blinkInterval = 0.3f;
	public int m_blinkTimes = 0;
	private int m_score = 0;
	
	public Player()
	{
	}
	
	@Override
	public void Start()
	{
		// Player life
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

		// Player Score
		m_playerScore = new ArrayList<>();
		m_numberSprites = new Vector<>();
		m_textures = new ArrayList<>();
		// adding Textures, Sprites and setting scale
		for (int i=0; i<10; i++) {
			m_textures.add(new Texture("numbers\\"+String.valueOf(i)+".png"));
			m_numberSprites.add(new Sprite(m_textures.get(i)));
			m_numberSprites.get(i).setScale(new Vector3f(1.0f, 1.0f, 1.0f));
		}
		// setting up the score by its parts
		for (int i=0; i<6; i++) {
			m_playerScore.add(new GameObject());
			m_playerScore.get(i).GetTransform().SetPos(new Vector3f((i+1)*20.0f, Window.GetHeight() - 25.0f, 1.0f));
			m_playerScore.get(i).AddComponent(m_numberSprites.get(i));
			GetParent().AddChild(m_playerScore.get(i));
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
			m_playerScore.get(3).GetComponent(Sprite.class).setTexture(m_textures.get(7));
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
