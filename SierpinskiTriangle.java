// © 2018 Lucas Jaggernauth
// Do not copy, cite, or distribute without permission of the author

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.image.WritableImage;
import javafx.scene.canvas.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.embed.swing.SwingFXUtils;

// Includes Drawing
public class SierpinskiTriangle extends Application {
	
	// VIEW SETTINGS
	static final int WIDTH = 800;
	static final int HEIGHT = 800;
	
	static boolean[] slotBelow = new boolean[WIDTH];
	
	static GraphicsContext gc;
	
	@Override
    public void start(Stage primaryStage) {
		
	    Group root = new Group();
    	Scene s = new Scene(root, WIDTH, HEIGHT, Color.WHITE);

    	final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    	gc = canvas.getGraphicsContext2D();
    	gc.setFill(Color.BLACK);
    	 
    	root.getChildren().add(canvas);
    
    	primaryStage.initStyle(StageStyle.UNDECORATED);
    	primaryStage.setTitle("Triangle");
    	primaryStage.setScene(s);
    	primaryStage.setResizable(false);
    	primaryStage.show();
    	
    	canvas.setFocusTraversable(true);
	
    	primaryStage.setOnCloseRequest(e -> {
	    	
	    	System.exit(0);
	    	
    	});
    	
    	for (int i = 0; i < WIDTH; i++) {
    		slotBelow[i] = true;
    	}
    	
    	AnimationTimer timer = new AnimationTimer() {
    		
    		int x = 0;
    		int y = 0;
    		boolean prev = true;
    		boolean[] temp = new boolean[WIDTH];
    		
    	    @Override
    	    public void handle(long dt) 
    	    {	
    	    	for (int i = 0; i < WIDTH * HEIGHT / 60; i++) {
	    	    	if (prev = prev^slotBelow[x])
	    	    		gc.fillRect(x, HEIGHT-y, 1, 1);
	    	    	temp[x] = prev;
	    	    	if (++x >= WIDTH) {
	    	    		if (++y >= HEIGHT) {
	    	    			this.stop();
//	    	    			WritableImage wim = new WritableImage(WIDTH, HEIGHT);
//	    	    			canvas.snapshot(null, wim);
//	    	    			
//	    	    			File file = new File("render.png");
//
//	    	    			try {
//	    	    				ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
	    	    			return;
	    	    		}
	    	    		else {
		    	    		slotBelow = temp;
		    	    		temp = new boolean[WIDTH];
		    	    		x = 0;
		    	    		prev = false;
	    	    		}
	    	    	}
    	    	}
    	    }
    	    
    	};
    	
    	timer.start();
	    	
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
}
