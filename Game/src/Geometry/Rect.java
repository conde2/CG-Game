package Geometry;

import static org.lwjgl.opengl.GL11.*;

import Maths.Vector2f;

public class Rect extends Geometry {
	
	private Vector2f size;
	
	public Rect(Vector2f position, Vector2f size)
	{
		x = position.x;
		y = position.y;
		this.size = size;
	}

	public void draw()
	{
		super.draw();
		
		if (isFilled())
		{
			glBegin(GL_QUADS);
		    glVertex2f(x, y);
		    glVertex2f(x+size.x, y);
		    glVertex2f(x+size.x, y+size.y);
		    glVertex2f(x, y+size.y);			
		}
		else
		{
			glBegin(GL_LINE_STRIP);	
		    glVertex2f(x, y);
		    glVertex2f(x+size.x, y);
		    glVertex2f(x+size.x, y+size.y);
		    glVertex2f(x, y+size.y);
		    glVertex2f(x, y);
		}

		glEnd(); 
	}
	
}