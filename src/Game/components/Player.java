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
import Engine.core.Vector3f;
import Engine.rendering.Texture;
import Engine.rendering.Window;

import java.util.ArrayList;
import java.util.Vector;

public class Player extends GameComponent
{

	private static final int INCSPEED = 1;
	private ArrayList<GameObject> m_playerLifes;
	private ArrayList<GameObject> m_playerScore;
	private Vector<Sprite> m_numberSprites;
	private ArrayList<Texture> m_textures;
	private GameObject m_lastLifeTaken = null;
	public int m_lifes 						= 	 3;
	public float m_blinkTimer				= 0.0f;
	public float m_scoreTimer 				= 0.0f;
	private float m_powerUpTimer 			= 0.0f;
	public float m_blinkInterval 			= 0.3f;
	public int m_blinkTimes 				=    0;
	private int m_score 					=    0;
	
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
			m_textures.add(new Texture("numbers/"+String.valueOf(i)+".png"));
			m_numberSprites.add(new Sprite(m_textures.get(i)));
			m_numberSprites.get(i).setScale(new Vector3f(0.8f, 0.8f, 1.0f));
		}
		// setting up the score by its parts
		for (int i=0; i<=5; i++) { // i=5 unidade, 4 dezenas, 3 centenas...
			GameObject number = new GameObject();
			number.GetTransform().SetPos(new Vector3f((i+1)*17.0f, Window.GetHeight() - 15.0f, 1.0f));
			number.AddComponent(m_numberSprites.get(i));
			number.SetEnabled(false);
			GetParent().AddChild(number);
			m_playerScore.add(number);
		}
		m_playerScore.get(5).SetEnabled(true);	// enabling units
		addScore(0);	// start score

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
		
		if (m_scoreTimer >=1 )
		{
			addScore(1);
			m_scoreTimer=0.0f;
		}
		
		if (m_powerUpTimer >=5.0f )
		{
			GetParent().GetComponent(FreeMove.class).setSpeed(80.0f+INCSPEED*GameManager.GetGameLevel());
		}
		m_powerUpTimer	+= delta;
		m_blinkTimer 	+= delta;
		m_scoreTimer 	+= delta;
		
	}
	
	@Override
	public void OnCollide(GameObject object)
	{
		
		if (object.GetTag() == "PowerUp")
		{
			object.SetEnabled(false);
			GetParent().GetComponent(FreeMove.class).setSpeed(100.0f+INCSPEED*GameManager.GetGameLevel());
			m_powerUpTimer = 0.0f;
		}
		else if (object.GetTag() == "GamePoint")
		{
			object.SetEnabled(false);
			// add score
			addScore(10);
		}
		else if (object.GetTag() == "Obstacle")
		{
			if(m_lifes>0)
			{
				GameObject life = m_playerLifes.get(3-m_lifes); // pega vidas no vetor de 0 a 2
				if(life.IsEnabled())//sempre sera pois esta pegando na ordem
				{
					m_blinkTimes = 5;
					m_lastLifeTaken = life;
					m_lifes--;
				}
			}
			//PLAYER IS DEAD
			else{
				CoreEngine.Stop();
			}
			
			
			object.SetEnabled(false);
		}
		
	}

	public void addScore(int points) {
		m_score += points;
		// units
		int num = m_score % 10;
		m_playerScore.get(5).GetComponent(Sprite.class).setTexture(m_textures.get(num));
		for (int i=2; i<=6; i++)//i=2 dezena, 3 centena, 4 milhar...
		{ 
			int div = (int) Math.pow(10, i);
			int remainder = m_score % div;	// remove right numbers
			int div2 = (int) Math.pow(10, i-1);
			num = remainder/div2;			// remove left numbers
			if (num!=0) m_playerScore.get(6-i).SetEnabled(true);
			m_playerScore.get(6-i).GetComponent(Sprite.class).setTexture(m_textures.get(num));
//			System.out.println("m_score: " + m_score + " div: " + div + " remainder: " + remainder +
//					"div2: "+ div2 + " num: " + num);
		}
	}

	public void resetScore() {
		m_score = 0;
		for (int i=0; i<=4; i++)
			m_playerScore.get(i).SetEnabled(false);
		addScore(0);
	}
}
