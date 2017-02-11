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
import Engine.core.*;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;

public class Enemy extends GameComponent
{
	private RenderingEngine m_renderingEngine;
	private float speed = 2.0f;

	@Override
	public void Update(float delta)
	{
		
		if(m_renderingEngine != null)
		{
			Quaternion newRot = GetTransform().GetLookAtRotation(m_renderingEngine.GetMainCamera().GetTransform().GetTransformedPos(),
					new Vector3f(0, 1, 0));
					//GetTransform().GetRot().GetUp());

			GetTransform().SetRot(GetTransform().GetRot().NLerp(newRot, delta * 5.0f, true));
			//GetTransform().SetRot(GetTransform().GetRot().SLerp(newRot, delta * 5.0f, true));
			
			
			// Follow player
			GetTransform().SetPos(GetTransform().GetPos().Add(GetTransform().GetRot().GetForward().Mul(delta*speed)));
		}
	}

	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		this.m_renderingEngine = renderingEngine;
	}
	
	public void OnCollide(GameObject object)
	{
		
		if (object.GetTag() == "Enemy")
		{
			if(object.IsEnabled()){
				System.out.println("EnemyXEnemy");
				object.SetEnabled(false);
			}
			
		}
	}
}
