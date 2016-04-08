import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Actor{

	private ImageView myImageView;
	private double xPos,yPos,xVelo,yVelo;
	private String name;
	
	
	Actor(double x, double y, Image i, String myname){
		myImageView = new ImageView(i);
		myImageView.setFitHeight(50);
		myImageView.setFitWidth(50);
		setxPos(x);

		setyPos(y);

		setxVelo(0);
		setxVelo(0);
		
		name = myname;
		
	}
	
	public ImageView getImageView(){
		return myImageView;
	}
	
	public Bounds getBounds(){
		return getImageView().getLayoutBounds();
	}
	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		getImageView().setX(xPos);
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		getImageView().setY(yPos);
		this.yPos = yPos;
	}

	public double getxVelo() {
		return xVelo;
	}

	public void setxVelo(double xVelo) {
		this.xVelo = xVelo;
	}

	public double getyVelo() {
		return yVelo;
	}

	public void setyVelo(double yVelo) {
		this.yVelo = yVelo;
	}

	public String getName() {
		return name;
	}	

}
