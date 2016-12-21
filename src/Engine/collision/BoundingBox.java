package Engine.collision;

import Engine.core.*;

public class BoundingBox extends BoundingCollider
{
	public Vector3f min;
	public Vector3f max;
	
	public BoundingBox(Vector3f min, Vector3f max)
	{
		this.min = min;
		this.max = max;
	}
	
	public boolean Intersect(BoundingBox b)
	{
		return (min.GetX() <= b.max.GetX() && max.GetX() >= b.min.GetX()) &&
				(min.GetY() <= b.max.GetY() && max.GetY() >= b.min.GetY()) &&
				(min.GetZ() <= b.max.GetZ() && max.GetZ() >= b.min.GetZ());
	}
	
	public boolean Intersect(BoundingSphere sphere)
	{

		  // get box closest point to sphere center by clamping
		  float x = Math.max(min.GetX(), Math.min(sphere.center.GetX(), max.GetX()));
		  float y = Math.max(min.GetY(), Math.min(sphere.center.GetY(), max.GetY()));
		  float z = Math.max(min.GetZ(), Math.min(sphere.center.GetZ(), max.GetZ()));

		  // this is the same as isPointInsideSphere
		  float distance = (float) Math.sqrt((x - sphere.center.GetX()) * (x - sphere.center.GetX()) +
		                           (y - sphere.center.GetY()) * (y - sphere.center.GetY()) +
		                           (z - sphere.center.GetZ()) * (z - sphere.center.GetZ()));
		  
		  return distance < sphere.radius;

	}	
}
