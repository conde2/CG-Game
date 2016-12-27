package Engine.components;

import Engine.core.*;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import static org.lwjgl.opengl.GL11.*;


public class Circulo extends GameComponent{
	
	boolean fill=false;
	float radius=0;
	float centerX;
	float centerY;
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
  		
  		
  		if (IsFilled())
  		{
  			glBegin(GL_LINE_LOOP);
  			/*glBegin(GL_TRIANGLE_FAN);
  			glVertex2f(centerX,centerY);//center
  			*/
  		}
  		else
  		{
  			glBegin(GL_POINTS);
  		}
  		
  		/*for(int i = 0; i < 45; i++)
  		{ 
  			//glVertex2d(xx + GetTransform().GetPos().GetX(), yy + GetTransform().GetPos().GetY());//output vertex 
  			
  			float eixoX =(float)Math.sin(Math.toRadians(i))*radius;
  			float eixoY =(float)Math.cos(Math.toRadians(i))*radius;
  			
  			circlepoints(eixoX,eixoY);		    
  		}*/
  		float d = 1 -radius;
  		float x = 0;
  		float y = radius;
  		circlepoints(x,y);
  		while(x<y){
	  		if (d<=0){
	  			d = d + 2*x + 3;
	  			x++;
	  		}
	  		else{
	  			d = d + 2*x -2*y + 5;
	  			x++;
	  			y--;
	  		}
	  		circlepoints(x,y);
  		}
  		glEnd();
	}
	
	void circlepoints(float eixoX,float eixoY){
		glVertex2f(centerX + eixoX, centerY + eixoY);//para cada ponto do circulo
		glVertex2f(centerX - eixoX, centerY + eixoY);//coloca no mesmo ponto em cada octante do circulo
		glVertex2f(centerX + eixoX, centerY - eixoY);
		glVertex2f(centerX - eixoX, centerY - eixoY);
		glVertex2f(centerX + eixoY, centerY + eixoX);
		glVertex2f(centerX - eixoY, centerY + eixoX);
		glVertex2f(centerX + eixoY, centerY - eixoX);
		glVertex2f(centerX - eixoY, centerY - eixoX);		
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
	
	public void Update(float delta){
		centerX=GetTransform().GetPos().GetX();
		centerY=GetTransform().GetPos().GetY();	
	}
	
}
