package Engine.collision;

import Engine.core.Vector3f;

public abstract class BoundingCollider
{
	public abstract boolean Intersect(BoundingBox b);
	public abstract boolean Intersect(BoundingSphere sphere);
	public void Update(Vector3f center) {};

}
