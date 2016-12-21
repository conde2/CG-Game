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
import Engine.components.FreeMove;
import Engine.components.GameComponent;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;

import java.util.ArrayList;

public class Player extends GameObject
{
	float radius;
	public Player(float radius,int speed)
	{
		super();
		this.radius = radius;
		GetTransform().SetPos(new Vector3f(400.0f,radius,0.0f));
		AddComponent(new FreeMove(speed));
		Circulo circuloComponent = new Circulo(radius);
		AddComponent(circuloComponent);
	}
	
	public ArrayList<Vector3f> Circumference(){
		ArrayList<Vector3f> lista = new ArrayList<Vector3f>();
		lista.add(GetTransform().GetPos().Add(new Vector3f(radius,0.0f,0.0f)));
		lista.add(GetTransform().GetPos().Add(new Vector3f(0.0f,radius,0.0f)));
		lista.add(GetTransform().GetPos().Add(new Vector3f(0.0f,-radius,0.0f)));
		return lista;
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
