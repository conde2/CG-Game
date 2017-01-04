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

import java.util.concurrent.ThreadLocalRandom;

public class ObstacleManager extends GameComponent
{
	public float m_radius = Window.GetWidth()/2;
	public float m_speed = 1.0f;
	public int m_numberOfObstacles = 10;

	int clockwise = 1;
	public Vector3f m_center = new Vector3f(Window.GetWidth()/2, Window.GetHeight()+m_radius, 0.0f);
	
	
	public ArrayList<GameObject> m_obstacles;

	public static enum Cores{
		BRANCO(new Vector3f(1.0f, 1.0f, 1.0f)),
        VERMELHO(new Vector3f(1.0f, 0.0f, 0.0f)),
        VERDE(new Vector3f(0.0f,1.0f,0.0f)),
        AZUL(new Vector3f(0.0f, 0.0f, 1.0f)),
        //PRETO(new Vector3f(0.0f, 0.0f, 0.0f)),
        ROXO(new Vector3f(1.0f, 0.0f, 1.0f)),
        AMARELO(new Vector3f(1.0f, 1.0f, 0.0f)),
        LARANJA(new Vector3f(1.0f, 0.5f, 0.0f)),
        CIANO(new Vector3f(0.0f, 1.0f, 1.0f)),
        MARROM(new Vector3f(107.0f/255.0f, 66.0f/255.0f, 38.0f/255.0f)),
        PINK(new Vector3f(1.0f, 28.0f/255.0f, 174.0f/255.0f));
		
		private final  Vector3f cor;
		
		Cores(Vector3f cor){
			this.cor = cor;
		}
		
		private Vector3f getCor(){
			return cor;
		}
	}
	public ObstacleManager(){
		super();
	}
	
	public ObstacleManager(float speed,int numberOfObstacles){
		super();
		m_speed = speed;
		m_numberOfObstacles = numberOfObstacles;
	}
	
	@Override
	public void Start()
	{
		m_obstacles = new ArrayList<GameObject>();

		for(int i = 0; i < 15; i++)
		{
			GameObject obstacle = new GameObject();
			obstacle.SetEnabled(false);
			obstacle.SetTag("Obstacle");

			Sprite obstacleSprite = new Sprite(new Texture("meteor.png"), 0.0f);
			obstacleSprite.setScale(new Vector3f(0.15f, 0.15f, 1.0f));
			
			BoundingSphere boundingSphere = new BoundingSphere(m_center, 15);
			Collider collider = new Collider(boundingSphere);

			obstacle.AddComponent(collider);
			obstacle.AddComponent(obstacleSprite);

			GetParent().AddChild(obstacle);
			m_obstacles.add(obstacle);
		}
		
		Spawn();
	}

	private void Spawn()
	{
		GetTransform().SetPos(m_center);
		
		clockwise = Math.random() > 0.5 ? 1 : -1;
		m_numberOfObstacles = ThreadLocalRandom.current().nextInt(5, 12);
		float littleRadius= (float)Math.floor(Math.PI * m_radius / (m_numberOfObstacles + Math.PI));
		
		
		int alive = 1;
		for(GameObject obstacle : m_obstacles)
		{
			if (alive >= m_numberOfObstacles)
			{
				obstacle.SetEnabled(false);
				continue;
			}

			Vector3f color = Cores.values()[alive==m_numberOfObstacles/2? 0:ThreadLocalRandom.current().nextInt(Cores.values().length)].getCor();
			if (color.equals(Cores.BRANCO.getCor()))
			{
				alive++;
				continue;
			}

			Sprite sprite = obstacle.GetComponent(Sprite.class);
			sprite.setScale(new Vector3f(0.46f - 0.025f * m_numberOfObstacles, 0.46f - 0.025f * m_numberOfObstacles, 1.0f));
			sprite.SetColor(color);
	
			Collider collider = obstacle.GetComponent(Collider.class);
			BoundingSphere boudingSphere = (BoundingSphere)collider.GetBoundingCollider();
			boudingSphere.SetRadius(littleRadius);
			
			obstacle.GetTransform().SetPos(new Vector3f(
					GetTransform().GetPos().GetX()+(m_radius-littleRadius)*(float)Math.cos(alive*(float)(2*Math.PI/m_numberOfObstacles)),
					GetTransform().GetPos().GetY()+(m_radius-littleRadius)*(float)Math.sin(alive*(float)(2*Math.PI/m_numberOfObstacles)),
					0.0f));
			
			obstacle.SetEnabled(true);
			alive++;

		}
	}
	
	public void SetSpeed(int speed){
		m_speed = speed;
	}
	
	public float GetSpeed(){
		return m_speed;
	}
	
	public float GetRadius(){
		return m_radius;
	}
	
	@Override
	public void Update(float delta)
	{
		GetTransform().SetPos(GetTransform().GetPos().Add(new Vector3f(0.0f,-1-m_speed*delta,0.0f))); //adiciona -20*speed*delta em Y do centro do obstaculo

		for(GameObject obstacle : m_obstacles)
		{
			//MOVE OBJETO
			//TRANSLADA PARA 0,0 o centro e tira 20*delta
			obstacle.GetTransform().SetPos(obstacle.GetTransform().GetPos().Add(new Vector3f(
																	-GetTransform().GetPos().GetX(),
																	-GetTransform().GetPos().GetY()-1-m_speed*delta,0.0f))); //adiciona -20*speed*delta em Y do centro de cada circulo do obsaculo
			//Rotaciona delta graus
			obstacle.GetTransform().SetPos(new Vector3f(
					obstacle.GetTransform().GetPos().GetX()*(float)Math.cos(clockwise*m_speed*delta/5)- obstacle.GetTransform().GetPos().GetY()*(float)Math.sin(clockwise*m_speed*delta/5),
					obstacle.GetTransform().GetPos().GetX()*(float)Math.sin(clockwise*m_speed*delta/5)+ obstacle.GetTransform().GetPos().GetY()*(float)Math.cos(clockwise*m_speed*delta/5),
					0.0f));
			//Retorna centro ao ponto original
			obstacle.GetTransform().SetPos(obstacle.GetTransform().GetPos().Add(new Vector3f(
																	GetTransform().GetPos().GetX(),
																	GetTransform().GetPos().GetY(),0.0f)));

		}

		if(GetTransform().GetPos().GetY() < -Window.GetHeight() + m_radius)
		{
			Spawn();
		}
	}
}

