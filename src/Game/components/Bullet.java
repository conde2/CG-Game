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

import Engine.components.GameComponent;
import Engine.core.GameObject;
import Engine.core.Vector3f;

public class Bullet extends GameComponent
{
	public Vector3f m_direction = new Vector3f(0,0,0);
	public float m_speed = 30.0f;
	public float m_initialLifeTime = 10.0f;
	public float m_lifeTime = 10.0f;
	
	public Bullet()
	{
	}
	
	public void SetDirection(Vector3f direction)
	{
		m_direction = direction;
	}
	
	public Vector3f GetDirection()
	{
		return m_direction;
	}
	
	@Override
	public void Start()
	{
		// Player life

	}
	
	@Override
	public void Update(float delta)
	{		
		m_lifeTime -= delta;
		GetTransform().SetPos(GetTransform().GetPos().Add(m_direction.Mul(delta*m_speed)));
		
		if (m_lifeTime <= 0)
		{
			GetParent().SetEnabled(false);
			m_lifeTime = m_initialLifeTime;
		}

	}

	@Override
	public void OnCollide(GameObject object)
	{
		
		if (object.GetTag() == "Enemy")
		{
			object.SetEnabled(false);
			GetParent().SetEnabled(false);
		}

	}

}
