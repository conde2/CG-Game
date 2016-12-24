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
import java.util.ArrayList;

public class ObstacleManager extends GameComponent
{
	public float m_radius = 100.0f;
	public float m_speed = 1.0f;
	public int m_numberOfObstacles = 9;
	public Vector3f m_center = new Vector3f(150.0f, 600.0f, 0.0f);
	public ArrayList<GameObject> m_obstacles;

	public enum Cores{
		BRANCO(new Vector3f(1.0f, 1.0f, 1.0f)),
            VERMELHO(new Vector3f(1.0f, 0.0f, 0.0f)),
            VERDE(new Vector3f(0.0f,1.0f,0.0f)),
            AZUL(new Vector3f(0.0f, 0.0f, 1.0f));
		
		public Vector3f cor;
		
		Cores(Vector3f cor){
			this.cor = cor;
		}
		
		public Vector3f getCor(){
			return cor;
		}
	}

	@Override
	public void Start()
	{
		m_obstacles = new ArrayList<GameObject>();

		GetTransform().SetPos(m_center);
		float littleRadius = (float)Math.floor(Math.PI*m_radius/m_numberOfObstacles);
		for(int i = 0; i < m_numberOfObstacles; i++)
		{
			
			GameObject circle = new GameObject();
			// Usando as 3 cores (mod 3 para definir)
            int resto = i % 3;
            if (resto == 0)
			    circle.SetColor(Cores.AZUL.getCor());
            else if (resto == 1)
                circle.SetColor(Cores.VERDE.getCor());
            else
                circle.SetColor(Cores.BRANCO.getCor());
			
			BoundingSphere boundingSphere = new BoundingSphere(m_center, littleRadius);
			Collider collider = new Collider(boundingSphere);
			circle.AddComponent(collider);
			
			Circulo circuloComponent = new Circulo(littleRadius);
			circle.AddComponent(circuloComponent);
			
			circle.GetTransform().SetPos(new Vector3f((float)(GetTransform().GetPos().GetX()+m_radius*Math.cos(i*2*Math.PI/m_numberOfObstacles)),
					(float)(GetTransform().GetPos().GetY()+m_radius*Math.sin(i*2*Math.PI/m_numberOfObstacles)),0.0f));

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
			obstacle.GetTransform().SetPos(new Vector3f((float)(obstacle.GetTransform().GetPos().GetX()*Math.cos(m_speed*delta/5)- obstacle.GetTransform().GetPos().GetY()*Math.sin(m_speed*delta/5)),
															(float)(obstacle.GetTransform().GetPos().GetX()*Math.sin(m_speed*delta/5)+ obstacle.GetTransform().GetPos().GetY()*Math.cos(m_speed*delta/5)),0.0f));
			//Retorna centro ao ponto original
			obstacle.GetTransform().SetPos(obstacle.GetTransform().GetPos().Add(new Vector3f(
																	GetTransform().GetPos().GetX(),
																	GetTransform().GetPos().GetY(),0.0f)));

		}
	}
}
