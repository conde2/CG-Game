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

import java.util.ArrayList;

import Engine.components.*;
import Engine.core.*;
import Engine.rendering.*;

public class TestGame extends Game
{

	public void Init()
	{

		// Add our camera
		GameObject camera = new GameObject();
		camera.AddComponent(new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(90.0f),
				(float) Window.GetWidth() / (float) Window.GetHeight(), 0.01f, 1000.0f)));
		
		Player player = new Player(10,50);
		//Sprite playerSprite = new Sprite(new Texture("circulo.png"));
		//playerSprite.setScale(new Vector3f(0.5f, 0.5f, 1.0f));
		Obstaculo obstacle= new Obstaculo(70.0f,10,new Vector3f(400.0f,500.0f,0.0f),1);

		AddObject(camera);
		AddObject(player);
		AddObject(obstacle);
		
	}
	
	@Override
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
					
				
			
		
		
	}
}
