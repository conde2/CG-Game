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

import Engine.collision.BoundingSphere;
import Engine.collision.Collider;

//import java.util.ArrayList;

import Engine.components.*;
import Engine.core.*;
import Engine.rendering.*;
import Game.components.ObstacleManager;
import Game.components.Player;
import Game.components.ObstacleManager.Cores;

public class TestGame extends Game
{
	GameObject obstacleManager = new GameObject();
	float speed=4.0f;
	Random randomNum = new Random();
	public void Init()
	{

		// Add our camera
		GameObject camera = new GameObject();
		camera.AddComponent(new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(90.0f),
				(float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f)));

		GameObject player = new GameObject();
		Player playerComponent = new Player();
		player.AddComponent(playerComponent);
		
		BoundingSphere boundingSphere = new BoundingSphere(player.GetTransform().GetPos(), 10);
		Collider collider = new Collider(boundingSphere);
		player.AddComponent(collider);

		Circulo circuloComponent = new Circulo(10.0f,true);
		player.AddComponent(circuloComponent);
		player.SetColor(new Vector3f(1.0f, 1.0f, 1.0f));
		player.GetTransform().SetPos(new Vector3f((float) Window.GetWidth() /2,(float) Window.GetHeight()/2,0.0f));
		
		player.AddComponent(new FreeMove(80.0f));
		
		
		ObstacleManager obstacleManagerComponent = new ObstacleManager(speed,5+randomNum.nextInt(5));
		obstacleManager.AddComponent(obstacleManagerComponent);

		AddObject(camera);
		AddObject(player);
		AddObject(obstacleManager);
	}
	@Override
	public void Update(float delta) {
		super.Update(delta);
		if(obstacleManager.GetTransform().GetPos().GetY()<-100){
			obstacleManager = new GameObject();
			ObstacleManager obstacleManagerComponent = new ObstacleManager(speed++,5+randomNum.nextInt(8));
			obstacleManager.AddComponent(obstacleManagerComponent);
			AddObject(obstacleManager);
		}
	}
	
	
	/*@Override
	public void Update(float delta) {
		super.Update(delta);
		
		GameObject root = this.GetRootObject();
		ArrayList<GameObject> objetos=root.GetAllAttached();
		for(GameObject objeto : objetos)
			if (objeto instanceof Player)
				for(GameObject outro : objetos)
					if(outro!=objeto)
						if(outro instanceof Obstaculo)
							for(Vector3f ponto:((Player) objeto).Circumference())
								if(((Obstaculo) outro).Collision(ponto)){
									//COLLISION CODE
									System.out.println("COLIDIU");
									break;
								}
					
				
			
	
		
	}*/
}
