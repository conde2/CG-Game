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
		
		GameObject player = new GameObject();
		Sprite playerSprite = new Sprite(new Texture("circulo.png"));
		playerSprite.setScale(new Vector3f(0.5f, 0.5f, 1.0f));
		player.AddComponent(playerSprite);

		AddObject(camera);
		AddObject(player);
		
	}
}
