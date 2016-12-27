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
import Engine.components.Circulo;
import Engine.components.GameComponent;
import Engine.core.GameObject;
import Engine.core.Vector3f;
import Engine.rendering.Window;

import java.util.ArrayList;
import java.util.Random;

public class ObstacleManager extends GameComponent
{
	public float m_radius = Window.GetWidth()/2;
	public float m_speed = 1.0f;
	public int m_numberOfObstacles = 10;
	float circunferenceLength = (float)(2*Math.PI/m_numberOfObstacles);
	int clockwise = Math.random()>0.5? 1:-1;
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
		m_numberOfObstacles=numberOfObstacles;
		circunferenceLength = (float)(2*Math.PI/m_numberOfObstacles);
	}
	
	@Override
	public void Start()
	{
		m_obstacles = new ArrayList<GameObject>();

		GetTransform().SetPos(m_center);
		float littleRadius=0.0f;
		//recursao para determinar melhor raio, pois o raio maior-raio menos = largura da tela
		littleRadius = (float)Math.PI*(m_radius-littleRadius)/m_numberOfObstacles;
		littleRadius = (float)Math.PI*(m_radius-littleRadius)/m_numberOfObstacles;
		littleRadius = (float)Math.PI*(m_radius-littleRadius)/m_numberOfObstacles;
		littleRadius = (float)Math.floor(Math.PI*(m_radius-littleRadius)/m_numberOfObstacles);
		
		//Adicionando pelo menos um circulo branco
		{
			GameObject circle = new GameObject();
			circle.SetColor(new Vector3f(1.0f, 1.0f, 1.0f)); //pega a inesima cor do enum, modulado no numero de cores total do enum
           
			
			BoundingSphere boundingSphere = new BoundingSphere(m_center, littleRadius);
			Collider collider = new Collider(boundingSphere);
			circle.AddComponent(collider);
			
			Circulo circuloComponent = new Circulo(littleRadius);
			circle.AddComponent(circuloComponent);
			
			circle.GetTransform().SetPos(new Vector3f(
													GetTransform().GetPos().GetX()+(m_radius-littleRadius),//cos(0)
													GetTransform().GetPos().GetY(), //sen(0)
													0.0f));

			GetParent().AddChild(circle);
			m_obstacles.add(circle);
		}
		for(int i = 1; i < m_numberOfObstacles; i++)
		{
			
			GameObject circle = new GameObject();
			Random randomNum = new Random();
			circle.SetColor(Cores.values()[i==m_numberOfObstacles/2? 0:randomNum.nextInt(Cores.values().length)].getCor()); //pega a inesima cor do enum,(se for o do meio eh branco) modulado no numero de cores total do enum
           
			
			BoundingSphere boundingSphere = new BoundingSphere(m_center, littleRadius);
			Collider collider = new Collider(boundingSphere);
			circle.AddComponent(collider);
			
			Circulo circuloComponent = new Circulo(littleRadius);
			circle.AddComponent(circuloComponent);
			
			circle.GetTransform().SetPos(new Vector3f(
					GetTransform().GetPos().GetX()+(m_radius-littleRadius)*(float)Math.cos(i*circunferenceLength),
					GetTransform().GetPos().GetY()+(m_radius-littleRadius)*(float)Math.sin(i*circunferenceLength),
					0.0f));

			GetParent().AddChild(circle);
			m_obstacles.add(circle);
	
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
		GetTransform().SetPos(GetTransform().GetPos().Add(new Vector3f(0.0f,-20*m_speed*delta,0.0f))); //adiciona -20*speed*delta em Y do centro do obstaculo

		for(GameObject obstacle : m_obstacles)
		{
			//MOVE OBJETO
			//TRANSLADA PARA 0,0 o centro e tira 20*delta
			obstacle.GetTransform().SetPos(obstacle.GetTransform().GetPos().Add(new Vector3f(
																	-GetTransform().GetPos().GetX(),
																	-GetTransform().GetPos().GetY()-20*m_speed*delta,0.0f))); //adiciona -20*speed*delta em Y do centro de cada circulo do obsaculo
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
	}
}
