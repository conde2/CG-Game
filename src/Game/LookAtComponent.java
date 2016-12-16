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

package Game;

import Engine.components.GameComponent;
import Engine.core.*;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;

public class LookAtComponent extends GameComponent
{
	private RenderingEngine m_renderingEngine;
	private float x = 0;
	private boolean start = false;
	private float timer = 0.05f;
	private Vector3f direction = new Vector3f(1,0,0);
	private Vector3f speed = new Vector3f(0.1f, 0, 0);
	@Override
	public void Update(float delta)
	{
		
		if (!start)
		{
			x = GetTransform().GetPos().GetX();	
			start = true;
		}
		
		if(m_renderingEngine != null)
		{
			Quaternion newRot = GetTransform().GetLookAtRotation(m_renderingEngine.GetMainCamera().GetTransform().GetTransformedPos(),
					new Vector3f(0, 1, 0));
					//GetTransform().GetRot().GetUp());

			GetTransform().SetRot(GetTransform().GetRot().NLerp(newRot, delta * 5.0f, true));
			//GetTransform().SetRot(GetTransform().GetRot().SLerp(newRot, delta * 5.0f, true));
			
			
			timer -= delta;
			if (timer <= 0)
			{
				if(GetTransform().GetPos().GetX() >= x + 3.0 || GetTransform().GetPos().GetX() <= x - 3.0)
				{
					direction = direction.Mul(-1);
				}
				GetTransform().SetPos(GetTransform().GetPos().Add(speed.Mul(direction)));
				
				timer = 0.05f;
			}
		}
	}

	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		this.m_renderingEngine = renderingEngine;
	}
}
