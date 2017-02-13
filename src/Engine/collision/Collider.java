package Engine.collision;

import Engine.components.GameComponent;
import Engine.rendering.RenderingEngine;
import Engine.rendering.Shader;
import static org.lwjgl.opengl.GL11.*;


public class Collider extends GameComponent
{
	boolean m_debug = false;

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
		
		if (m_boundingCollider == null)
			return;
			
		int stacks = 15;
		int slices = 15;
		float PI = (float)Math.PI;
		float rho, drho, theta, dtheta;
		float x = ((BoundingSphere)m_boundingCollider).center.GetX();
		float y = ((BoundingSphere)m_boundingCollider).center.GetY();
		float z = ((BoundingSphere)m_boundingCollider).center.GetZ();
		float radius = ((BoundingSphere)m_boundingCollider).radius;
		int i, j;
		float nsign = 1.0f;
		boolean normals = false;
		drho = PI / stacks;
		dtheta = 2.0f * PI / slices;


		// draw stack lines
		// stack line at i==stacks-1 was missing here
		for (i = 1; i < stacks; i++)
		{
			
			rho = i * drho;

			glBegin(GL_LINE_LOOP);
			for (j = 0; j < slices; j++) {
				theta = j * dtheta;
				x = (float) (Math.cos(theta) * Math.sin(rho));
				y = (float) (Math.sin(theta) * Math.sin(rho));
				z = (float) Math.cos(rho);
				if (normals)
					glNormal3f(x * nsign, y * nsign, z * nsign);
				glVertex3f(x * radius, y * radius, z * radius);
			}
			glEnd();
		}
		// draw slice lines
		for (j = 0; j < slices; j++) {
			theta = j * dtheta;
			glColor3f(1.0f, 0, 0);
			glLineWidth(3.0f);
			glBegin(GL_LINE_STRIP);
			for (i = 0; i <= stacks; i++) {
				rho = i * drho;
				x = (float) (Math.cos(theta) * Math.sin(rho));
				y = (float) (Math.sin(theta) * Math.sin(rho));
				z = (float) Math.cos(rho);
				if (normals)
					glNormal3f(x * nsign, y * nsign, z * nsign);
				

				glVertex3f(x * radius, y * radius, z * radius);
			}
			glEnd();
		}
	}
	
}
