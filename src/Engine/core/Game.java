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

import Engine.components.GameComponent;
import Engine.rendering.RenderingEngine;

public abstract class Game
{
	private GameObject m_root;

	public void Init() {}

	public void Input(float delta)
	{
		GetRootObject().InputAll(delta);
	}

	public void Update(float delta)
	{
		GetRootObject().UpdateAll(delta);
		CheckCollision();
	}
	
	public void CheckCollision()
	{
		for(GameObject child : GetRootObject().GetAllAttached())
		{
			if (child.GetCollider() == null)
				continue;
					
			for(GameObject other :  GetRootObject().GetAllAttached())
			{
				if (other.GetCollider() == null)
					continue;
				
				if (child == other)
					continue;

				if (child.GetCollider().Intersect(other.GetCollider())
						&& !child.GetColor().equals(other.GetColor())) //se tem intersec��o
				{
//					System.out.println("Cores Child: " + child.GetColor() + "other: " + other.GetColor());
					//rotina de colisao para child e other e seus componentes
					for(GameComponent component : child.GetAllComponents()) 
					{
						component.OnCollide(other);
					}
					for(GameComponent component : other.GetAllComponents()) 
					{
						component.OnCollide(child);
					}
				}

			}
		}

	}

	public void Render(RenderingEngine renderingEngine)
	{
		renderingEngine.Render(GetRootObject());
	}

	public void AddObject(GameObject object)
	{
		GetRootObject().AddChild(object);
	}

	protected GameObject GetRootObject()
	{
		if(m_root == null)
			m_root = new GameObject();

		return m_root;
	}

	public void SetEngine(CoreEngine engine) { GetRootObject().SetEngine(engine); }
}
