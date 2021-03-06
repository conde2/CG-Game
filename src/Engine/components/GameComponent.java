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

package Engine.components;

import Engine.core.CoreEngine;
import Engine.core.GameObject;
import Engine.rendering.RenderingEngine;
import Engine.core.Transform;
import Engine.rendering.Shader;

public abstract class GameComponent
{
	private GameObject m_parent;
	private boolean m_enabled;

	public void Start() {};
	public void Input(float delta) {}
	public void Update(float delta) {}
	public void OnCollide(GameObject object) {}
	public void Render(Shader shader, RenderingEngine renderingEngine) {}

	public void SetParent(GameObject parent)
	{
		this.m_parent = parent;
	}
	
	public GameObject GetParent()
	{
		return m_parent;
	}

	public Transform GetTransform()
	{
		return m_parent.GetTransform();
	}
	
	public boolean IsEnabled()
	{
		return m_enabled;
	}
	
	public void SetEnabled(boolean enabled)
	{
		m_enabled = enabled;
	}

	public void AddToEngine(CoreEngine engine) {}
}

