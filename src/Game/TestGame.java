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

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Engine.collision.BoundingSphere;
import Engine.collision.Collider;
import Engine.components.BaseLight;
import Engine.components.Camera;
import Engine.components.DirectionalLight;
import Engine.components.FreeLook;
import Engine.components.FreeMove;
import Engine.components.MeshRenderer;
import Engine.components.PointLight;
import Engine.components.SpotLight;
import Engine.components.Sprite;
import Engine.core.Game;
import Engine.core.GameObject;
import Engine.core.Matrix4f;
import Engine.core.Quaternion;
import Engine.core.Vector3f;
import Engine.rendering.Attenuation;
import Engine.rendering.Material;
import Engine.rendering.Mesh;
import Engine.rendering.Texture;
import Engine.rendering.Window;
import Game.components.GameManager;
import Game.components.ObstacleManager;
import Game.components.Player;


public class TestGame extends Game
{
	public void Init()
	{
		Material material2 = new Material(new Texture("bricks.jpg"), 1, 8,
				new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f);

		Material material = new Material(new Texture("bricks2.jpg"), 1, 8,
				new Texture("bricks2_normal.png"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);
		
		Mesh tempMesh = new Mesh("monkey3.obj");
		
		Material grassMaterial = new Material(new Texture("grass2.png"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
		
		Material treeMaterial = new Material(new Texture("wood2.png"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
		
		float x, z;
		int count;
		Random random = new Random(5666778);
		// Construindo Terreno
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				
				count = ThreadLocalRandom.current().nextInt(0, 3);
				for (int k = 0; k < count; k++)
				{
					x = ThreadLocalRandom.current().nextInt(0, 16);
					z = ThreadLocalRandom.current().nextInt(0, 16);
					
					GameObject treeObject = new GameObject();
					Mesh tree = new Mesh("tree2.obj" );
					MeshRenderer treeRederer = new MeshRenderer(tree, treeMaterial);
					treeObject.AddComponent(treeRederer);
					treeObject.GetTransform().GetPos().Set(i*16 + x, 0, j*16 + z);
					treeObject.SetUpdate(false);
					AddObject(treeObject);
				}

				count = ThreadLocalRandom.current().nextInt(1, 6);
				for (int k = 0; k < count; k++)
				{
					x = ThreadLocalRandom.current().nextInt(0, 16);
					z = ThreadLocalRandom.current().nextInt(0, 16);
					
					GameObject grassObject = new GameObject();
					Mesh grass = new Mesh("grass"  + ThreadLocalRandom.current().nextInt(1, 3) + ".obj");
					MeshRenderer grassRenderer = new MeshRenderer(grass, grassMaterial);
					grassObject.AddComponent(grassRenderer);
					grassObject.GetTransform().GetPos().Set(i*16 + x, 0, j*16 + z);
					grassObject.SetUpdate(false);
					AddObject(grassObject);
				}
				
				GameObject planeObject = new GameObject();
				Mesh mesh = new Mesh("plane3.obj");
				MeshRenderer meshRenderer = new MeshRenderer(mesh, grassMaterial);
				planeObject.AddComponent(meshRenderer);
				planeObject.GetTransform().GetPos().Set(i*16.1f, 0, j*16.1f);
				planeObject.SetUpdate(false);
				AddObject(planeObject);
			}
		}

	


			
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0,0,1), 0.4f);

		directionalLightObject.AddComponent(directionalLight);

		GameObject pointLightObject = new GameObject();
		pointLightObject.AddComponent(new PointLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0, 0, 1)));

		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
				new Attenuation(0,0,0.1f), 0.7f);

		GameObject spotLightObject = new GameObject();
		spotLightObject.AddComponent(spotLight);

		spotLightObject.GetTransform().GetPos().Set(5, 0, 5);
		spotLightObject.GetTransform().SetRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(90.0f)));


		AddObject(directionalLightObject);
		AddObject(pointLightObject);
		AddObject(spotLightObject);

		GameObject testMesh3 = new GameObject().AddComponent(new LookAtComponent()).AddComponent(new MeshRenderer(tempMesh, material));

		GameObject player = (new GameObject().AddComponent(new FreeLook(0.5f)).AddComponent(new FreeMove(10.0f))
						.AddComponent(new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(70.0f),
								(float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f))));

		AddObject(testMesh3);

		testMesh3.GetTransform().GetPos().Set(15, 5, 5);
		testMesh3.GetTransform().SetRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(-70.0f)));

		GameObject monkey = new GameObject().AddComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2));
		monkey.GetTransform().GetPos().Set(0, 1, 0);
		
		directionalLight.GetTransform().SetRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));

		BoundingSphere boundingSphere = new BoundingSphere(player.GetTransform().GetPos(), 1);
		Collider collider = new Collider(boundingSphere);
		monkey.AddComponent(collider);

		BoundingSphere playerBoudingSphere = new BoundingSphere(player.GetTransform().GetPos(), 1);
		Collider playerCollider = new Collider(playerBoudingSphere);
		player.AddComponent(playerCollider);
		
		
		Player playerComponent = new Player();
		player.AddComponent(playerComponent);
		
		GameObject baseLightObject = new GameObject();
		PointLight light = new PointLight(new Vector3f(1.0f,1.0f,1.0f), 10000.0f, new Attenuation(5.0f, 10.0f, 15.0f));
		baseLightObject.AddComponent(light);
		baseLightObject.GetTransform().GetPos().Set(15, 25, 15);
		AddObject(baseLightObject);
		
		
		AddObject(monkey);
		AddObject(player);

		
	}
}


/*public class TestGame extends Game
{
	public void Init() {
		// For text

		// Add our camera
		GameObject camera = new GameObject();
		camera.AddComponent(new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(90.0f),
				(float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f)));

		GameObject player = new GameObject();
		player.GetTransform().SetPos(new Vector3f((float) Window.GetWidth() /2,(float) Window.GetHeight()/2,0.0f));

		Player playerComponent = new Player();
		player.AddComponent(playerComponent);
		
		BoundingSphere boundingSphere = new BoundingSphere(player.GetTransform().GetPos(), 13);
		Collider collider = new Collider(boundingSphere);
		player.AddComponent(collider);

		player.AddComponent(new FreeMove(78.0f));
		
		Sprite playerSprite = new Sprite(new Texture("spaceship.png"), 0.2f);
		playerSprite.setScale(new Vector3f(0.15f,0.15f,1.0f));
		playerSprite.SetColor(new Vector3f(1.0f, 1.0f, 1.0f));
		player.AddComponent(playerSprite);
		
		GameObject background = new GameObject();
		Sprite backgroundSprite = new Sprite(new Texture("background.jpg"), -0.5f);
		background.AddComponent(backgroundSprite);

		GameObject obstacleManager = new GameObject();
		ObstacleManager obstacleManagerComponent = new ObstacleManager();
		obstacleManager.AddComponent(obstacleManagerComponent);
		
		GameObject gameManager = new GameObject();
		GameManager gameManagerComponent = new GameManager();
		gameManager.AddComponent(gameManagerComponent);

		// Desenhar primeiro os objetos com menor Z !!
		AddObject(camera);
		AddObject(background);
		AddObject(gameManager);
		AddObject(obstacleManager);
		AddObject(player);
	}
}*/
