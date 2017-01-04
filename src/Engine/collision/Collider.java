package Engine.collision;

import Engine.components.GameComponent;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import static org.lwjgl.opengl.GL11.*;


public class Collider extends GameComponent
{
	boolean m_debug = true;

	private BoundingCollider m_boundingCollider = null;
	public Collider(BoundingCollider boundingCollider)
	{
		m_boundingCollider = boundingCollider;
	}
	
	public BoundingCollider GetBoundingCollider()
	{
		return m_boundingCollider;
	}

	public boolean Intersect(Collider collider)
	{
		if (collider.GetBoundingCollider() instanceof BoundingBox)
			return m_boundingCollider.Intersect((BoundingBox)collider.GetBoundingCollider());
		else if (collider.GetBoundingCollider() instanceof BoundingSphere)
			return m_boundingCollider.Intersect((BoundingSphere)collider.GetBoundingCollider());
		else 
			return false;
	}

	@Override
	public void Update(float delta)
	{
		m_boundingCollider.Update(GetParent().GetTransform().GetPos());
	}

	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		if(!m_debug)
			return;
		
		glColor3f(0.0f, 0.0f, 0.0f);
        double theta = (2.0f * Math.PI) / 360.0f; 
  		double c = Math.cos(theta);//precalculate the sine and cosine
  		double s = Math.sin(theta);
  		double t;

  		double xx = ((BoundingSphere)m_boundingCollider).radius;//we start at angle = 0 
  		double yy = 0; 

  		glBegin(GL_LINE_LOOP);
   		for(int i = 0; i < 360; i++) 
  		{ 
  			glVertex2d(xx + GetTransform().GetPos().GetX(), yy + GetTransform().GetPos().GetY());//output vertex 
  	        
  			//glVertex2d(xx + ((BoundingSphere)m_boundingCollider).center.GetX(), yy + ((BoundingSphere)m_boundingCollider).center.GetY());//output vertex 
  	        

  			t = xx;
  			xx = c * xx - s * yy;
  			yy = s * t + c * yy;
  		} 
  		glEnd();
  		
  		glBegin(GL_LINE_LOOP);
   		for(int i = 0; i < 360; i++) 
  		{ 
  			//glVertex2d(xx + GetTransform().GetPos().GetX(), yy + GetTransform().GetPos().GetY());//output vertex 
  	        
  			glVertex2d(xx + ((BoundingSphere)m_boundingCollider).center.GetX(), yy + ((BoundingSphere)m_boundingCollider).center.GetY());//output vertex 
  	        

  			t = xx;
  			xx = c * xx - s * yy;
  			yy = s * t + c * yy;
  		} 
  		glEnd();
	}
	
}
