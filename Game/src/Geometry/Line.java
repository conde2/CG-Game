package Geometry;

import static org.lwjgl.opengl.GL11.*;

import Maths.Vector2f;

public class Line extends Geometry {
	
	private Vector2f endLine;
	
	public Line(Vector2f start, Vector2f end)
	{
		x = start.x;
		y = start.y;
		endLine = end;
	}

	public void draw()
	{
		super.draw();
	    glBegin(GL_LINE_STRIP);
	    glVertex2d(x, y);
	    glVertex2d(endLine.x, endLine.y);
	    glEnd();
		
	}
	
}
