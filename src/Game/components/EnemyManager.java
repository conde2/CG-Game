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

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Engine.core.Game;
import Engine.collision.BoundingSphere;
import Engine.collision.Collider;
import Engine.components.GameComponent;
import Engine.components.MeshRenderer;
import Engine.core.GameObject;
import Engine.core.Quaternion;
import Engine.core.Vector3f;
import Engine.rendering.Material;
import Engine.rendering.Mesh;
import Engine.rendering.Texture;

public class EnemyManager extends GameComponent
{
	
	private float m_spawnTimer = 0.0f;
	private float m_spawnInterval = 3.0f;

	public ArrayList<GameObject> m_enemies;
	
	public EnemyManager()
	{
	}
	
	
	@Override
	public void Start()
	{
		m_enemies = new ArrayList<GameObject>();

		for(int i = 0; i < 5; i++)
		{
			GameObject enemy = new GameObject();
			enemy.SetEnabled(false);
			enemy.SetTag("Enemy");

			Material enemyMaterial = new Material(new Texture("pokeball.png"), 1, 8,
					new Texture("elk.png"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);

			Mesh enemyMesh = new Mesh("pokeball.obj");

			enemy.AddComponent(new Enemy()).AddComponent(new MeshRenderer(enemyMesh, enemyMaterial));

			BoundingSphere boundingSphere = new BoundingSphere(enemy.GetTransform().GetPos(), 2);
			Collider collider = new Collider(boundingSphere);
			enemy.AddComponent(collider);
			
			enemy.GetTransform().SetScale(new Vector3f(0.01f,0.01f,0.01f));
			
			Game.AddObject(enemy);
			m_enemies.add(enemy);
		}

	}
	
	public void HideAll(){
		for (GameObject enemy: m_enemies){
			enemy.SetEnabled(false);
		}
		
	}
	
	public void Spawn(GameObject object)
	{

		int x = ThreadLocalRandom.current().nextInt(0, 70);
		int z = ThreadLocalRandom.current().nextInt(0, 70);
		
		object.GetTransform().SetPos(new Vector3f(x, 8, z));
		object.SetEnabled(true);	
	}

	@Override
	public void Update(float delta)
	{		

		if(m_spawnTimer >= m_spawnInterval)
		{
			for(GameObject enemy : m_enemies)
			{
				if(!enemy.IsEnabled())
				{
					Spawn(enemy);
					break;
				}
			}
			m_spawnTimer = 0.0f;
		}
		
		m_spawnTimer += delta;
	}

}
