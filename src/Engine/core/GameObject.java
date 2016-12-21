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

import Engine.collision.Collider;
import Engine.components.GameComponent;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;

import java.util.ArrayList;

public class GameObject
{
	private ArrayList<GameObject> m_children;
	private ArrayList<GameComponent> m_components;
	private Transform m_transform;
	private Collider m_collider;
	private CoreEngine m_engine;

	public GameObject()
	{
		m_children = new ArrayList<GameObject>();
		m_components = new ArrayList<GameComponent>();
		m_transform = new Transform();
		m_engine = null;
		m_collider = null;
	}

	public GameObject AddChild(GameObject child)
	{
		m_children.add(child);
		child.SetEngine(m_engine);
		child.GetTransform().SetParent(m_transform);
		
		return this;
	}

	public GameObject AddComponent(GameComponent component)
	{
		if (component instanceof Collider)
		{
			m_collider = (Collider)component;
		}

		m_components.add(component);
		component.SetParent(this);
		component.Start();

		return this;
	}

	public void InputAll(float delta)
	{
		Input(delta);

		for(GameObject child : m_children)
			child.InputAll(delta);
	}

	public void UpdateAll(float delta)
	{
		Update(delta);

		for(GameObject child : m_children)
			child.UpdateAll(delta);
	}
	
	public Collider GetCollider()
	{
		return m_collider;
	}

	public void CheckCollisionAll()
	{

	}
	
	public void RenderAll(Shader shader, RenderingEngine renderingEngine)
	{
		Render(shader, renderingEngine);

		for(GameObject child : m_children)
			child.RenderAll(shader, renderingEngine);
	}

	public void Input(float delta)
	{
		m_transform.Update();

		for(GameComponent component : m_components)
			component.Input(delta);
	}

	public void Update(float delta)
	{
		for(GameComponent component : m_components)
			component.Update(delta);
	}

	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		for(GameComponent component : m_components)
			component.Render(shader, renderingEngine);
	}

	public ArrayList<GameObject> GetAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for(GameObject child : m_children)
			result.addAll(child.GetAllAttached());

		result.add(this);
		return result;
	}
	
    public <U extends GameComponent> U GetComponent(Class<U> clazz) {
        for (GameComponent component : m_components) {
            if (clazz.isInstance(component)) {
                return clazz.cast(component);
            }
        }
        return null;
    }
	
	public ArrayList<GameComponent> GetAllComponents()
	{
		ArrayList<GameComponent> result = new ArrayList<GameComponent>();

		for(GameComponent component : m_components)
			result.add(component);

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

			for(GameObject child : m_children)
				child.SetEngine(engine);
		}
	}
}
