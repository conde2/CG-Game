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

import Engine.collision.BoundingSphere;
import Engine.collision.Collider;
import Engine.components.*;
import Engine.core.*;
import Engine.rendering.*;
import Game.components.EnemyManager;
import Game.components.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class TestGame extends Game
{
	GameObject player 		= null;
	GameObject enemyManager = null;
	private static ArrayList<GameObject> planosScore;
	private static int mScore = 0;

	public void Init()
	{
		
		Material grassMaterial = new Material(new Texture("grass2.png"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
		
		Material treeMaterial = new Material(new Texture("wood2.png"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
		
		Material skyMaterial = new Material(new Texture("darksky.jpg"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
		
		GameObject skySphere = new GameObject();
		Mesh skyMesh = new Mesh("Earth.obj");
		MeshRenderer skyRenderer = new MeshRenderer(skyMesh, skyMaterial);
		skySphere.AddComponent(skyRenderer);
		skySphere.GetTransform().GetPos().Set(64, 1, 64);
		skySphere.GetTransform().SetScale(new Vector3f(-90.0f, -90.0f, -90.0f));
		skySphere.SetUpdate(false);
		AddObject(skySphere);
		
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
					x = ThreadLocalRandom.current().nextInt(-4, 12);
					z = ThreadLocalRandom.current().nextInt(-4, 12);
					
					GameObject treeObject = new GameObject();
					Mesh tree = new Mesh("tree2.obj" );
					MeshRenderer treeRederer = new MeshRenderer(tree, treeMaterial);
					treeObject.AddComponent(treeRederer);
					treeObject.GetTransform().GetPos().Set((i*16 + x)%72.0f, 0, (j*16 + z)%72.0f);
					treeObject.SetUpdate(false);
					AddObject(treeObject);

					System.out.println(treeObject.GetTransform().GetPos());
				}

				count = ThreadLocalRandom.current().nextInt(1, 6);
				for (int k = 0; k < count; k++)
				{
					x = ThreadLocalRandom.current().nextInt(-4, 12);
					z = ThreadLocalRandom.current().nextInt(-4, 12);
					
					GameObject grassObject = new GameObject();
					Mesh grass = new Mesh("grass"  + ThreadLocalRandom.current().nextInt(1, 3) + ".obj");
					MeshRenderer grassRenderer = new MeshRenderer(grass, grassMaterial);
					grassObject.AddComponent(grassRenderer);
					grassObject.GetTransform().GetPos().Set((i*16 + x)%72.0f, 0, (j*16 + z)%72.0f);
					grassObject.SetUpdate(false);
					AddObject(grassObject);
					System.out.println(grassObject.GetTransform().GetPos());
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

		player = (new GameObject().AddComponent(new FreeLook(0.5f)).AddComponent(new FreeMove(10.0f))
						.AddComponent(new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(70.0f),
								(float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f))));

		player.GetTransform().GetPos().Set(5, 3, 5);

		BoundingSphere playerBoudingSphere = new BoundingSphere(player.GetTransform().GetPos(), 1);
		Collider playerCollider = new Collider(playerBoudingSphere);
		player.AddComponent(playerCollider);
		
		Player playerComponent = new Player();
		player.AddComponent(playerComponent);
		

		enemyManager = new GameObject();
		EnemyManager enemyManagerComponent = new EnemyManager();
		enemyManager.AddComponent(enemyManagerComponent);
		
		//AddObject(monkey);
		AddObject(player);
		AddObject(enemyManager);

		planosScore = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			GameObject plano = new GameObject();
			plano.SetEnabled(false);
			plano.SetTag("plano");
			Mesh planoMesh = new Mesh("plane3.obj");
			Material planoMaterial = new Material(new Texture("numbers/0.png"), 0, 0,
					new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
			MeshRenderer planoRenderer = new MeshRenderer(planoMesh, planoMaterial);
			plano.AddComponent(planoRenderer);
			plano.GetTransform().SetRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(90.0f)));
			plano.SetEnabled(true);
			plano.GetTransform().SetScale(new Vector3f(0.2f, 0.2f, 0.2f));
			plano.GetTransform().GetPos().Set(new Vector3f(30-(i*3), 2, -8));
			planosScore.add(plano);
			Game.AddObject(plano);
		}
		for (int i = 3; i < 6; i++) {
			GameObject plano = new GameObject();
			plano.SetEnabled(false);
			plano.SetTag("plano");
			Mesh planoMesh = new Mesh("plane3.obj");
			Material planoMaterial = new Material(new Texture("numbers/0.png"), 0, 0,
					new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
			MeshRenderer planoRenderer = new MeshRenderer(planoMesh, planoMaterial);
			plano.AddComponent(planoRenderer);
			plano.GetTransform().SetRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-90.0f)));
			plano.GetTransform().Rotate(new Vector3f(0, 0, 1), (float) Math.toRadians(180.0f));
			plano.SetEnabled(true);
			plano.GetTransform().SetScale(new Vector3f(0.2f, 0.2f, 0.2f));
			plano.GetTransform().GetPos().Set(new Vector3f(30-((i-3)*3), 2, 72));
			planosScore.add(plano);
			Game.AddObject(plano);
		}
		for (int i = 6; i < 9; i++) {
			GameObject plano = new GameObject();
			plano.SetEnabled(false);
			plano.SetTag("plano");
			Mesh planoMesh = new Mesh("plane3.obj");
			Material planoMaterial = new Material(new Texture("numbers/0.png"), 0, 0,
					new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
			MeshRenderer planoRenderer = new MeshRenderer(planoMesh, planoMaterial);
			plano.AddComponent(planoRenderer);
			plano.GetTransform().SetRot(new Quaternion(new Vector3f(0, 0, 1), (float) Math.toRadians(90.0f)));
			plano.SetEnabled(true);
			plano.GetTransform().SetScale(new Vector3f(0.2f, 0.2f, 0.2f));
			plano.GetTransform().GetPos().Set(new Vector3f(-8, 2, 30-((i-6)*3)));
			planosScore.add(plano);
			Game.AddObject(plano);
		}
		for (int i = 9; i < 12; i++) {
			GameObject plano = new GameObject();
			plano.SetEnabled(false);
			plano.SetTag("plano");
			Mesh planoMesh = new Mesh("plane3.obj");
			Material planoMaterial = new Material(new Texture("numbers/0.png"), 0, 0,
					new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
			MeshRenderer planoRenderer = new MeshRenderer(planoMesh, planoMaterial);
			plano.AddComponent(planoRenderer);
			plano.GetTransform().SetRot(new Quaternion(new Vector3f(0, 0, 1), (float) Math.toRadians(90.0f)));
			plano.GetTransform().Rotate(new Vector3f(1, 0, 0), (float) Math.toRadians(90.0f));
			plano.SetEnabled(true);
			plano.GetTransform().SetScale(new Vector3f(0.2f, 0.2f, 0.2f));
			plano.GetTransform().GetPos().Set(new Vector3f(72, 2, 30-((i-9)*3)));
			planosScore.add(plano);
			Game.AddObject(plano);
		}
	}

	public static void addScore(int points) {
		mScore += points;
		// units
		int num = mScore % 10;
		Material planoMaterial = new Material(new Texture("numbers/"+String.valueOf(num)+".png"), 0, 0,
				new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
		planosScore.get(2).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
		planosScore.get(3).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
		planosScore.get(6).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
		planosScore.get(11).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
		for (int i=2; i<=3; i++)//i=2 dezena, 3 centena, 4 milhar...
		{
			int div = (int) Math.pow(10, i);
			int remainder = mScore % div;	// remove left numbers
			int div2 = (int) Math.pow(10, i-1);
			num = remainder/div2;			// remove right numbers
			planoMaterial = new Material(new Texture("numbers/"+String.valueOf(num)+".png"), 0, 0,
					new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
			planosScore.get(3-i).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
			planosScore.get(i+2).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
			planosScore.get(i+5).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
			planosScore.get(12-i).GetComponent(MeshRenderer.class).SetMateria(planoMaterial);
		}
	}

	public void resetScore()
	{
		mScore = 0;
		addScore(0);
	}
	
	public void Reset(){
		player.GetTransform().GetPos().Set(5, 3, 5);//Reseta pra posi��o inciial
		enemyManager.GetComponent(EnemyManager.class).HideAll(); // esconde todos os inimigos
		resetScore();
	}
}
