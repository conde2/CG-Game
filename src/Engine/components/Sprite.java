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
	private float       m_zBuffer = 0.0f;
	private boolean     m_center = true;
	private Vector3f	m_color = new Vector3f(1.0f,1.0f,1.0f);

	public Sprite(Texture texture, float zBuffer)
	{
		m_texture = texture;
		m_zBuffer = zBuffer;
	}
	
	public Sprite(Texture texture)
	{
		m_texture = texture;
	}

	public void setTexture(Texture m_texture) {
		this.m_texture = m_texture;
	}

	public void setScale(Vector3f scale)
	{
		 m_scale = scale;
	}
	
	public Vector3f getScale()
	{
		return m_scale;
	}

	public int getHeight()
	{
		return m_texture.getHeight();
	}
	
	public int getWidth()
	{
		return m_texture.getWidth();
	}
	
	public void SetColor(Vector3f color)
	{
		m_color = color;
	}

	public Vector3f GetColor()
	{
		return m_color;
	}	
	
	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		m_texture.Bind();
		glColor4f(m_color.GetX(), m_color.GetY(), m_color.GetZ(), 1.0f);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST); 
		glDepthFunc(GL_LEQUAL);
		glClearDepth(1.0f);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0);
		
		glBegin(GL_QUADS);

			float x = GetTransform().GetPos().GetX();
			float y = GetTransform().GetPos().GetY();
			float width = m_texture.getWidth() * m_scale.GetX();
			float height =  m_texture.getWidth() * m_scale.GetY(); 
			
			if (m_center)
			{
	        	glTexCoord2f(0, 0); // top left
	        	glVertex3f(x - width/2, y - height /2, m_zBuffer);

	        	glTexCoord2f(0, 1); // bottom left 
	        	glVertex3f(x- width/2, y + height - height /2, m_zBuffer);

	        	glTexCoord2f(1, 1); // bottom right
	        	glVertex3f(x + width - width/2, y + height - height /2, m_zBuffer);

	        	glTexCoord2f(1, 0); // top right
	        	glVertex3f(x + width - width/2, y - height /2, m_zBuffer);
			}
			else
			{
				glTexCoord2f(0, 0); // top left
				glVertex3f(x, y, m_zBuffer);

				glTexCoord2f(0, 1); // bottom left 
				glVertex3f(x, y + height, m_zBuffer);

				glTexCoord2f(1, 1); // bottom right
				glVertex3f(x + width, y + height, m_zBuffer);

				glTexCoord2f(1, 0); // top right
				glVertex3f(x + width, y, m_zBuffer);
			}
        glEnd();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
		
	}
}
