
/**
 * Physics Engine Class
 * Handles calculating/assigning new positions based on positional attributes and movement vectors
 * Receives Actor object via movement specific methods (ie moveRight, jump)
 * These methods then apply a set force that affects and updates the Actor's 
 * position and velocity variables
 * 
 * @author justinbergkamp
 */


public class Engine {
	
	
	//These Variable values are arbitrary, chosen by trial/error
	private int timeStep            =  1;   //Arbitrary timeStep, will be set to the time provided by step()
	private double friction         = -.05;  //Horizontal acceleration dampening (friction) coefficient
	private double gravity          = .1 ;  //Falling acceleration coefficient
	private double maxHorizVelocity = 50;   //maximum horizontal velocity
	private double maxVertVelocity  = -50;  //maximum vertical velocity 
	private double horizontalForce  = 5;    //Force applied to Actors on horizontal movements
	private double jumpForce        = -5;    //Vertical Force applied to Actors on jump movements
	
	public Engine(){
		
	}
	
	
	/**
	 * This method updates a given velocity by applying a given force
	 * @param velo
	 * @param force
	 * @return updated velo
	 */
	private double applyForce(double velo, double force){  //Applies a force(acceleration because mass =1) to a velocity
		double nextVelo = velo + force*timeStep;
		return nextVelo;
	}
	
	/**
	 * This method updates a given position by adding a velocity value scaled by a timeStep
	 * @param pos
	 * @param velo
	 * @return updated position
	 */
	private double changePos(double pos, double velo){
		double nextPos = pos + velo*timeStep;
		return nextPos;
	}
	
	private void update(Actor a,double forceX, double forceY ){
		double xVelo     = a.getxVelo();
		double yVelo     = a.getyVelo();
		double xPos      =  a.getxPos();      
		double yPos      =  a.getyPos();

		double nextHorzVelo;
		double nextVertVelo;
		double nextXPos;
		double nextYPos;
				
		nextHorzVelo = xVelo;      
		
		nextVertVelo = applyForce(yVelo, forceY);            // Apply  y force from movement action to y velocity
		nextVertVelo = applyForce(nextVertVelo, gravity);    //Apply gravitational force
		nextYPos     = changePos(yPos, nextVertVelo); 
		
		nextVertVelo = maxLimit(nextVertVelo, maxVertVelocity);
		
		if(nextYPos > 300){                    //Collision detection for the actor and the ground
			nextYPos = 300;			
			nextVertVelo = 0;
		}
		
		if(nextVertVelo == 0){  //If the Actor is on the ground
			nextHorzVelo = applyForce(xVelo, forceX); // Apply  y force from movement action to y velocity
			nextHorzVelo = applyForce(nextHorzVelo, (friction*(nextHorzVelo))); //Apply frictional force
		}
		nextXPos  = changePos(xPos,nextHorzVelo);
		
		nextHorzVelo = maxLimit(nextHorzVelo, maxHorizVelocity);
		
		setValues(a,  nextHorzVelo,  nextVertVelo,  nextXPos,  nextYPos );
	
	}
	
	/**
	 * Sets position and velocity variables for an Actor
	 * 
	 * @param a
	 * @param nextHorzVelo
	 * @param nextVertVelo
	 * @param nextXPos
	 * @param nextYPos
	 */
	private void setValues(Actor a, double nextHorzVelo, double nextVertVelo, double nextXPos, double nextYPos){
		a.setxVelo(nextHorzVelo);
		a.setyVelo(nextVertVelo);
		a.setxPos(nextXPos);
		a.setyPos(nextYPos);	
	}
	
	private double maxLimit(double vector, double limit){
		double v = Math.abs(vector);
		if( v > Math.abs(limit)){
			return limit;
		}
		return vector;
	}
	
	//These methods correspond to Actions that call them
	//They differ in the force applied to the Actor
	
	public void moveRight(Actor a1) {
		update(a1,horizontalForce, 0);
	}

	public void moveLeft(Actor a1) {
		update(a1,-horizontalForce, 0);
	}
	
	public void jump(Actor a1){
		update(a1,0,jumpForce);
	}

	public void tick(Actor a1) {
		update(a1,0.0,0.0);
	}


	public void reverseXVelo(Actor a1) {
		a1.setxVelo(-a1.getxVelo()*2);
	}


	public void reverseYVelo(Actor a1) {
		a1.setyVelo(-a1.getyVelo()*2);
	}
	
	
}
