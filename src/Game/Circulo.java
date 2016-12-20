package Game;

import Engine.components.GameComponent;
import Engine.core.*;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import static org.lwjgl.opengl.GL11.*;


public class Circulo extends GameComponent{
	
	boolean fill=false;
	float radius=0;
	public Circulo(float r){
		this.radius=r;
	}
	
	public void Render(Shader shader, RenderingEngine renderingEngine) {
		glColor3f(1.0f, 0.0f, 0.0f);
        double theta = (2.0f * Math.PI) / 360.0f; 
  		double c = Math.cos(theta);//precalculate the sine and cosine
  		double s = Math.sin(theta);
  		double t;

  		double xx = radius;//we start at angle = 0 
  		double yy = 0; 
  	    
  		if (IsFilled())
  		{
  			glBegin(GL_TRIANGLE_FAN);
  		}
  		else
  		{
  			glBegin(GL_LINE_LOOP);
  		}

  		for(int i = 0; i < 360; i++) 
  		{ 
  			glVertex2d(xx + GetTransform().GetPos().GetX(), yy + GetTransform().GetPos().GetY());//output vertex 
  	        

  			t = xx;
  			xx = c * xx - s * yy;
  			yy = s * t + c * yy;
  		} 
  		glEnd();
	}


	public boolean Inside(Vector3f point) {
		if (Math.pow(point.GetX()-GetTransform().GetPos().GetX(),2) + 
				Math.pow(point.GetY()-GetTransform().GetPos().GetY(),2) 
				< Math.pow(radius,2))
			return true;
		return false;
	}
	
	public void SetFill(){
		fill= true;
	}
	public void SetEmpty(){
		fill= false;	
	}
	public boolean IsFilled(){
		return fill;	
	}
	
	/*public void Update(float delta){
		GetTransform().SetPos(GetTransform().GetPos().Add(new Vector3f(0.0f,-20*delta,0.0f))); //adiciona delta em Y atual
	}*/
	
}
