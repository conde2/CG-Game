package Engine.components;

import Engine.core.*;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import static org.lwjgl.opengl.GL11.*;


public class Circulo extends GameComponent{
	
	boolean fill=false;
	float radius=0;
	public Circulo(float radius){
		this.radius=radius;
	}
	public Circulo(float radius,boolean filled){
		this(radius);
		fill=filled;
	}
	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine) {
		glColor3f(GetParent().GetColor().GetX(),GetParent().GetColor().GetY(),GetParent().GetColor().GetZ());
        double theta = (2.0f * Math.PI) / 360.0f; 
  		double c = Math.cos(theta);//precalculate the sine and cosine
  		double s = Math.sin(theta);
  		double t;

  		double xx = radius;//we start at angle = 0 
  		double yy = 0; 
  		
  		float centerX=GetTransform().GetPos().GetX();
		float centerY=GetTransform().GetPos().GetY();
  	    
  		if (IsFilled())
  		{
  			glBegin(GL_LINE_LOOP);
  			glVertex2f(centerX,centerY);//center
  		}
  		else
  		{
  			glBegin(GL_POINTS);
  		}
  		
  		for(int i = 0; i < 45; i++)
  		{ 
  			//glVertex2d(xx + GetTransform().GetPos().GetX(), yy + GetTransform().GetPos().GetY());//output vertex 
  	        

  			/*t = xx;
  			xx = c * xx - s * yy;
  			yy = s * t + c * yy;
  			*/
  			float eixoX =(float)Math.sin(Math.toRadians(i))*radius;
  			float eixoY =(float)Math.cos(Math.toRadians(i))*radius;
  			
  			glVertex2f(centerX + eixoX, centerY + eixoY);
  			glVertex2f(centerX - eixoX, centerY + eixoY);
  			glVertex2f(centerX + eixoX, centerY - eixoY);
  			glVertex2f(centerX - eixoX, centerY - eixoY);
  			glVertex2f(centerX + eixoY, centerY + eixoX);
  			glVertex2f(centerX - eixoY, centerY + eixoX);
  			glVertex2f(centerX + eixoY, centerY - eixoX);
  			glVertex2f(centerX - eixoY, centerY - eixoX);
  			    
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
