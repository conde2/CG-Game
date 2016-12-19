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

package Engine.components;

import Engine.core.Vector3f;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import Engine.rendering.Texture;
import static org.lwjgl.opengl.GL11.*;

public class Sprite extends GameComponent
{
	private Texture     m_texture;
	private Vector3f 	m_scale = new Vector3f(1.0f,1.0f,1.0f);
	
	public Sprite(Texture texture)
	{
		this.m_texture = texture;
	}
	
	public void setScale(Vector3f scale)
	{
		 m_scale = scale;
	}
	
	public Vector3f getScale()
	{
		return m_scale;
	}

	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		m_texture.Bind();
		glBegin(GL_QUADS);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
			float x = GetTransform().GetPos().GetX();
			float y = GetTransform().GetPos().GetY();
			float width = m_texture.getWidth() * m_scale.GetX();
			float height =  m_texture.getWidth() * m_scale.GetY(); 
			
        	glTexCoord2f(0, 0); // top left
        	glVertex2f(x, y);

        	glTexCoord2f(0, 1); // bottom left 
        	glVertex2f(x, y + height);

        	glTexCoord2f(1, 1); // bottom right
        	glVertex2f(x + width, y + height);

        	glTexCoord2f(1, 0); // top right
        	glVertex2f(x + width, y);
        glEnd();
		
	}
}
