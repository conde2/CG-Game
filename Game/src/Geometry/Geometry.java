package Geometry;

import Maths.Vector2f;
import Maths.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public abstract class Geometry {

	
	public float PI = 3.1415926f;
	public float x = 0.0f;
	public float y = 0.0f;
	private Vector3f color = new Vector3f();
	private boolean fill = false;
	
	public boolean isFilled()
	{
		return fill;
	}
	
	public void setFill(boolean fill)
	{
		this.fill = fill;
	}
	
	public void setColor(Vector3f color)
	{
		this.color = color;
		
	}
	
	public void setColor(float r, float g, float b)
	{
		color.x = r;
		color.y = g;
		color.z = b;
	}
	
	public void draw()
	{
		glColor3f(color.x, color.y, color.z);
	}
	
}
