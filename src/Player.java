import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Player {

	private int screenSize = 1200;
    private Scene myScene;
    private List<Actor> actors;
    private Engine myEngine;
    private Actor a1;
    private Actor a2;
    private CollisionDetection myCollisionDetector;
    
    
    public void setUp(Stage stage){
        Group root = new Group();
        myScene = new Scene (root,screenSize,screenSize, Color.BLACK);
        stage.setScene(myScene);
        myEngine = new Engine();
        myCollisionDetector = new CollisionDetection(myEngine);
        actors = new ArrayList<Actor>();
        
        Image image = new Image("circ.png");
        a1 = new Actor(0,300, image,"green");
        
        Image image2 = new Image("red_circ.png");
        a2 = new Actor(300,300, image2,"red");
        
        actors.add(a1);
        actors.add(a2);
        for(Actor a: actors){
        	root.getChildren().add(a.getImageView());
        }
        
        
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        
        stage.show();
    }

	private void handleKeyInput(KeyCode key) {
		switch (key) {
        	case RIGHT:
        		myEngine.moveRight(a1);
        		break;
        	case LEFT:
        		myEngine.moveLeft(a1);
        		break;
        		
        	case SPACE:
        		myEngine.jump(a1);
        		break;
        	default:
        		break;
		}
		step(1);
	}

	

	public Object step(double secondDelay) {
		for(Actor a: actors){
			myEngine.tick(a);
		}
		myCollisionDetector.detection(actors);
		return null;
	}
	
}