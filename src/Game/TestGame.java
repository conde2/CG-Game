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
import Engine.components.Camera;
import Engine.components.FreeMove;
import Engine.components.GameComponent;
import Engine.components.Sprite;
import Engine.core.Game;
import Engine.core.GameObject;
import Engine.core.Matrix4f;
import Engine.core.Vector3f;
import Engine.rendering.Texture;
import Engine.rendering.Window;
import Game.components.ObstacleManager;
import Game.components.Player;
import text.Text;

import java.util.Random;

//import java.util.ArrayList;

public class TestGame extends Game
{
	private GameObject obstacleManager = new GameObject();
	private GameObject player = new GameObject();
	private float speed=2.0f;
	private Random randomNum = new Random();

	public void Init() {
		// For text
		GameObject text = new GameObject();
		text.AddComponent(new Text("Lets go", 10, 50, 10, 1));

		// Add our camera
		GameObject camera = new GameObject();
		camera.AddComponent(new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(90.0f),
				(float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f)));

		Player playerComponent = new Player();
		player.AddComponent(playerComponent);
		
		BoundingSphere boundingSphere = new BoundingSphere(player.GetTransform().GetPos(), 13);
		Collider collider = new Collider(boundingSphere);
		player.AddComponent(collider);

		player.SetColor(new Vector3f(1.0f, 1.0f, 1.0f));
		player.GetTransform().SetPos(new Vector3f((float) Window.GetWidth() /2,(float) Window.GetHeight()/2,0.0f));
		
		player.AddComponent(new FreeMove(78.0f+speed));
		
		Sprite playerSprite = new Sprite(new Texture("spaceship.png"), 0.2f);
		playerSprite.setScale(new Vector3f(0.15f,0.15f,1.0f));
		player.AddComponent(playerSprite);
		
		GameObject background = new GameObject();
		Sprite backgroundSprite = new Sprite(new Texture("background.jpg"), -0.5f);
		background.AddComponent(backgroundSprite);

		
		ObstacleManager obstacleManagerComponent = new ObstacleManager(speed,6+randomNum.nextInt(4));
		obstacleManager.AddComponent(obstacleManagerComponent);

		// Desenhar primeiro os objetos com menor Z !!
		AddObject(camera);
		AddObject(background);
		AddObject(obstacleManager);
		AddObject(player);
		AddObject(text);
	}
	@Override
	public void Update(float delta) {
		super.Update(delta);

		if(obstacleManager.GetTransform().GetPos().GetY()<-100){
			obstacleManager = new GameObject();
			ObstacleManager obstacleManagerComponent = new ObstacleManager(speed++,6+randomNum.nextInt(7));
			obstacleManager.AddComponent(obstacleManagerComponent);
			AddObject(obstacleManager);
			
			for (GameComponent componente: player.GetAllComponents()){
				if(componente instanceof FreeMove){
					((FreeMove) componente).setSpeed(78.0f + speed);
				}
			}
		}
	}
}
