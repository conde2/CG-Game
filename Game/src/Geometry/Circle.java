package Geometry;

import static org.lwjgl.opengl.GL11.*;

import Maths.Vector2f;

public class Circle extends Geometry {
	
	private float radius;
	private int segments = 50;
	
	public Circle(float cx, float cy, float radius)
	{
		x = cx;
		y = cy;
		this.radius = radius;
	}

	public void draw()
	{
		super.draw();

		double theta = (2.0f * PI) / (float)segments; 
		double c = Math.cos(theta);//precalculate the sine and cosine
		double s = Math.sin(theta);
		double t;

		double xx = radius;//we start at angle = 0 
		double yy = 0; 
	    
		if (isFilled())
		{
			glBegin(GL_TRIANGLE_FAN);
		}
		else
		{
			glBegin(GL_LINE_LOOP);
		}

		for(int i = 0; i < segments; i++) 
		{ 
			glVertex2d(xx + x, yy + y);//output vertex 
	        
			//apply the rotation matrix
			t = xx;
			xx = c * xx - s * yy;
			yy = s * t + c * yy;
		} 
		glEnd(); 
		
	}
	
}