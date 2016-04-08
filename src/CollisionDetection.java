
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;

/**
 * Collision Detection class handles checking for collisions among a list of Actors
 * It also handles resolving said collision should one be found
 * 
 * @author justinbergkamp
 *
 */
public class CollisionDetection {

	Engine myPhysicsEngine;
	private String LEFT   =  "left";
	private String RIGHT  =  "right";
	private String TOP    =  "top";
	private String BOTTOM =  "bottom";
	
	public CollisionDetection( Engine physicsEngine){
		myPhysicsEngine = physicsEngine;
	}
	
	public List<Actor> detection(List<Actor> actors){
		for (Actor a1 : actors){
			for(Actor a2 : actors){
				if(a1 != a2){
					if(isCollision(a1,a2)){
						resolveCollision(a1,a2);
					}
				}
			}
		}
		return actors;
	}
	
	
	private boolean isCollision(Actor a1, Actor a2){
		return a1.getBounds().intersects(a2.getBounds());
	}
	
	private Map<String, Double> getCorners(Actor a){ 
		Map<String,Double> actorMap = new HashMap<String,Double>();
		actorMap.put(LEFT  , a.getBounds().getMinX());
		actorMap.put(RIGHT , a.getBounds().getMaxX());
		actorMap.put(TOP   , a.getBounds().getMinY());
		actorMap.put(BOTTOM, a.getBounds().getMaxY());
		return actorMap;
	}

	private String getCollisionType(Map<String,Double> a1Map, Map<String,Double> a2Map){
		String ct = null;
		if( edgeCollision(a1Map,a2Map,LEFT, RIGHT, LEFT) ||
			edgeCollision(a1Map,a2Map,LEFT, RIGHT, RIGHT) ){
			ct = "SideCollision";
		}
		else if(edgeCollision(a1Map,a2Map,BOTTOM, TOP, BOTTOM)){
			ct = "TopCollision";
		}
		else if(edgeCollision(a1Map,a2Map,BOTTOM, TOP, TOP)){
			ct = "BottomCollision";
		}
		return ct;
	}
	private boolean edgeCollision(Map<String,Double> a1Map, Map<String,Double> a2Map,String edge1, String edge2, String a2Edge ) {
		return ( (a1Map.get(edge1)   <= a2Map.get(a2Edge))   &&  (a2Map.get(a2Edge)   <= a1Map.get(edge2)) );
	}

	private void resolveCollision(Actor a1, Actor a2){
		String collisionType = getCollisionType(getCorners(a1),getCorners(a2));
		String triggerString = a1.getName() + collisionType + a2.getName();
		System.out.print(triggerString+"\n");
		if(collisionType == "SideCollision"){
			myPhysicsEngine.reverseXVelo(a1);
		}else if(collisionType == "TopCollision" || collisionType == "BottomCollision"){
			myPhysicsEngine.reverseYVelo(a1);
		}

	}	
}