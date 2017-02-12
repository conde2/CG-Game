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
import Engine.components.SpotLight;
import Engine.core.*;
import Engine.rendering.Attenuation;
import Engine.rendering.Material;
import Engine.rendering.Mesh;
import Engine.rendering.Texture;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class Player extends GameComponent
{
	private int coliderCounter=0;
	
	private ArrayList<GameObject> m_bullets;

	private GameObject spotLightObject;
	private GameObject plano;
	
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

		for(int i = 0; i < 20; i++)
		{
			GameObject bullet = new GameObject();
			bullet.SetEnabled(false);
			bullet.SetTag("Bullet");

			Mesh bulletMesh = new Mesh("sheep.obj" );
			MeshRenderer bulletRederer = new MeshRenderer(bulletMesh, bulletMaterial);
			bullet.AddComponent(bulletRederer);
			
			Bullet bulletComponent = new Bullet();
			bullet.AddComponent(bulletComponent);

			BoundingSphere bulletBoudingSphere = new BoundingSphere(bullet.GetTransform().GetPos(), 1);
			Collider bulletCollider = new Collider(bulletBoudingSphere);
			bullet.AddComponent(bulletCollider);
			

			m_bullets.add(bullet);
			Game.AddObject(bullet);
		}

		plano = new GameObject();
		plano.SetEnabled(false);
		plano.SetTag("plano");
		Mesh planoMesh = new Mesh("plane3.obj");
		Material planoMaterial = new Material(new Texture("numbers/"+String.valueOf(1)+".png"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
		MeshRenderer planoRenderer = new MeshRenderer(planoMesh, planoMaterial);
		plano.AddComponent(planoRenderer);
		Game.AddObject(plano);
		plano.GetTransform().SetPos(GetTransform().GetPos());
		plano.GetTransform().SetRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-90.0f)));
		plano.SetEnabled(true);
		plano.GetTransform().SetScale(new Vector3f(0.1f, 0.1f, 0.1f));
		plano.GetTransform().GetPos().Set(GetTransform().GetPos());

		
		SpotLight spotLight = new SpotLight(new Vector3f(1,1,1), 4.4f,
				new Attenuation(0,0,0.1f), 0.65f);

		
		spotLightObject = new GameObject();
		spotLightObject.AddComponent(spotLight);

		spotLightObject.GetTransform().GetPos().Set(GetTransform().GetPos());
		
		Game.AddObject(spotLightObject);

	}
	
	@Override
	public void Update(float delta)
	{
		spotLightObject.GetTransform().SetRot(GetTransform().GetRot());
		spotLightObject.GetTransform().SetPos(GetTransform().GetPos().Add(GetTransform().GetRot().GetForward().Mul(1.5f)));
		// score
		plano.GetTransform().SetPos(GetTransform().GetPos()
				.Add(GetTransform().GetRot().GetForward().Mul(10.5f))	// em frente
				.Add(GetTransform().GetRot().GetUp().Mul(6.5f))			// acima
				.Add(GetTransform().GetRot().GetLeft().Mul(12))			// esquerda
		);
	}
	

	public void Input(float delta) 
	{
		if(Input.IsKeyPressed(GLFW_KEY_SPACE))
		{
			//System.out.println("SHOOT");
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
		if (object.GetTag().equals("Enemy"))
		{
			System.out.println("PlayerXEnemy"+coliderCounter++);
			GetParent().GetEngine().Reset();
		}
	}
}
