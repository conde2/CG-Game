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

package Engine.core;

import Engine.components.Circulo;
import Engine.components.GameComponent;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;

import java.util.ArrayList;

public class Obstaculo extends GameObject
{
	float radius;
	int nCircles;
	int speed;
	public Obstaculo(float radius,int numberOfCircles,Vector3f center,int speed)
	{
		super();
		GetTransform().SetPos(center);
		this.radius = radius;
		this.speed=speed;
		nCircles = numberOfCircles;
		float littleRadius = (float)Math.floor(Math.PI*radius/nCircles);	
		for(int i=0;i<nCircles;i++){
			GameObject circle = new GameObject();
			Circulo circuloComponent = new Circulo(littleRadius);
			circle.GetTransform().SetPos(new Vector3f((float)(GetTransform().GetPos().GetX()+radius*Math.cos(i*2*Math.PI/nCircles)),
														(float)(GetTransform().GetPos().GetY()+radius*Math.sin(i*2*Math.PI/nCircles)),0.0f));
			circle.AddComponent(circuloComponent);
			AddChild(circle);
		}
	}
	
	public void SetSpeed(int s){
		speed=s;
	}
	
	public int GetSpeed(int s){
		return speed;
	}
	
	@Override
	public void UpdateAll(float delta)
	{
		Update(delta);
		for(GameObject child : m_children){
			//MOVE OBJETO
			//TRANSLADA PARA 0,0 o centro e tira 20*delta
			child.GetTransform().SetPos(child.GetTransform().GetPos().Add(new Vector3f(
																	-GetTransform().GetPos().GetX(),
																	-GetTransform().GetPos().GetY()-20*speed*delta,0.0f))); //adiciona -20*speed*delta em Y do centro de cada circulo do obsaculo
			//Rotaciona delta graus
			child.GetTransform().SetPos(new Vector3f((float)(child.GetTransform().GetPos().GetX()*Math.cos(speed*delta/5)- child.GetTransform().GetPos().GetY()*Math.sin(speed*delta/5)),
															(float)(child.GetTransform().GetPos().GetX()*Math.sin(speed*delta/5)+ child.GetTransform().GetPos().GetY()*Math.cos(speed*delta/5)),0.0f));
			//Retorna centro ao ponto original
			child.GetTransform().SetPos(child.GetTransform().GetPos().Add(new Vector3f(
																	GetTransform().GetPos().GetX(),
																	GetTransform().GetPos().GetY(),0.0f)));

			child.UpdateAll(delta);
		}
	}
	
	public boolean Collision(Vector3f ponto){
		for(GameObject child : m_children)
			for(GameComponent circle: child.m_components)
				if (circle instanceof Circulo){
					if(((Circulo) circle).Inside(ponto))
						return true;
				}
			
		return false;
	}
	@Override
	public void Update(float delta)
	{
		GetTransform().SetPos(GetTransform().GetPos().Add(new Vector3f(0.0f,-20*speed*delta,0.0f))); //adiciona -20*speed*delta em Y do centro do obstaculo
		//GetTransform().Rotate(new Vector3f(1,1,0),10);
		for(GameComponent component : m_components)
			component.Update(delta);
	}
	/*
	public void RenderAll(Shader shader, RenderingEngine renderingEngine)
	{
		Render(shader, renderingEngine);

		for(Obstaculo child : m_children)
			child.RenderAll(shader, renderingEngine);
	}

	public void Input(float delta)
	{
		m_transform.Update();

		for(GameComponent component : m_components)
			component.Input(delta);
	}

	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		for(GameComponent component : m_components)
			component.Render(shader, renderingEngine);
	}

	public ArrayList<Obstaculo> GetAllAttached()
	{
		ArrayList<Obstaculo> result = new ArrayList<Obstaculo>();

		for(Obstaculo child : m_children)
			result.addAll(child.GetAllAttached());

		result.add(this);
		return result;
	}

	public Transform GetTransform()
	{
		return m_transform;
	}

	public void SetEngine(CoreEngine engine)
	{
		if(this.m_engine != engine)
		{
			this.m_engine = engine;

			for(GameComponent component : m_components)
				component.AddToEngine(engine);

			for(Obstaculo child : m_children)
				child.SetEngine(engine);
		}
	}*/
}
