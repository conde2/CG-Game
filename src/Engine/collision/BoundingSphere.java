package Engine.collision;

import Engine.core.*;

public class BoundingSphere extends BoundingCollider
{
	public Vector3f center;
	public float radius;
	
	public BoundingSphere(Vector3f center, float radius)
	{
		this.center = center;
		this.radius = radius;
	}
	
	@Override
	public void Update(Vector3f center)
	{
		this.center = center;
	}
		
	public boolean Intersect(BoundingBox box)
	{

		  // get box closest point to sphere center by clamping
		  float x = Math.max(box.min.GetX(), Math.min(center.GetX(), box.max.GetX()));
		  float y = Math.max(box.min.GetY(), Math.min(center.GetY(), box.max.GetY()));
		  float z = Math.max(box.min.GetZ(), Math.min(center.GetZ(), box.max.GetZ()));

		  // this is the same as isPointInsideSphere
		  float distance = (float) Math.sqrt((x - center.GetX()) * (x - center.GetX()) +
		                           (y - center.GetY()) * (y - center.GetY()) +
		                           (z - center.GetZ()) * (z - center.GetZ()));
		  
		  return distance < radius;

	}
	
	public boolean Intersect(BoundingSphere other) 
	{
		  // we are using multiplications because it's faster than calling Math.pow
		  double distance = Math.sqrt((center.GetX() - other.center.GetX()) * (center.GetX() - other.center.GetX()) +
		                           (center.GetY() - other.center.GetY()) * (center.GetY() - other.center.GetY()) +
		                           (center.GetZ() - other.center.GetZ()) * (center.GetZ() - other.center.GetZ()));
		  return distance < (radius + other.radius); 
	}
	
	public boolean Inside(Vector3f point)
	{
		  // we are using multiplications because is faster than calling Math.pow
		  double distance = Math.sqrt((point.GetX() - center.GetX()) * (point.GetX() - center.GetX()) +
		                           (point.GetY() - center.GetY()) * (point.GetY() - center.GetY()) +
		                           (point.GetZ() - center.GetZ()) * (point.GetZ() - center.GetZ()));
		  return distance < radius;
	}

	
}
