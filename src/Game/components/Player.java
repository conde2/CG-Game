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

import Engine.collision.BoundingSphere;
import Engine.collision.Collider;

import Engine.components.GameComponent;
import Engine.components.MeshRenderer;
import Engine.core.Game;
import Engine.core.GameObject;
import Engine.core.Input;
import Engine.core.Vector3f;
import Engine.rendering.Material;
import Engine.rendering.Mesh;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import Engine.rendering.Texture;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameComponent
{

	private RenderingEngine m_renderingEngine;
	private ArrayList<GameObject> m_bullets;
	private int m_score = 0;
	
	public Player()
	{
	}
	
	@Override
	public void Start()
	{
		// Player life
		m_bullets = new ArrayList<GameObject>();

		Material bulletMaterial = new Material(new Texture("wood2.png"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);

		for(int i = 0; i < 50; i++)
		{
			GameObject playerBullet = new GameObject();
			
			Mesh bullet = new Mesh("sheep.obj" );
			MeshRenderer bulletRederer = new MeshRenderer(bullet, bulletMaterial);
			playerBullet.AddComponent(bulletRederer);
			
			Bullet bulletComponent = new Bullet();
			playerBullet.AddComponent(bulletComponent);

			playerBullet.SetEnabled(false);
			m_bullets.add(playerBullet);
			Game.AddObject(playerBullet);
		}

	}
	
	@Override
	public void Update(float delta)
	{
				
	}
	

	public void Input(float delta) 
	{
		if(Input.IsKeyPressed(GLFW_KEY_SPACE))
		{
			System.out.println("SHOOT");
			for (GameObject bullet : m_bullets)
			{
				if(bullet.IsEnabled())
					continue;
				

				Bullet component = bullet.GetComponent(Bullet.class);
				component.SetDirection(GetTransform().GetRot().GetForward().Normalized());
				bullet.GetTransform().SetPos(GetTransform().GetPos());
				bullet.SetEnabled(true);
				break;
			}
		}
	}

	@Override
	public void OnCollide(GameObject object)
	{
		
		if (object.GetTag() == "PowerUp")
		{
			object.SetEnabled(false);
		}
	}
	
	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		this.m_renderingEngine = renderingEngine;
	}

}
