// © 2018 Lucas Jaggernauth
// Do not copy, cite, or distribute without permission of the author

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

class Number2 {
	BigDecimal r;
	BigDecimal i;
	Number2 (BigDecimal r, BigDecimal i) {
		this.r = r;
		this.i = i;
	}
}

// Includes Drawing
public class MandelbrotSetV2 extends Application {
	
	// VIEW SETTINGS
	static final int WIDTH = 800;
	static final int HEIGHT = 800;
	static final BigDecimal MAX = new BigDecimal("1000"); //WIDTH > HEIGHT ? WIDTH : HEIGHT;
	static BigInteger MAX_DEPTH = new BigInteger("12");
	
	static BigDecimal zoom = new BigDecimal("0.64");
	static BigDecimal offsetX = new BigDecimal("0");
	static BigDecimal offsetY = new BigDecimal("1.2");

	static BigDecimal colors = new BigDecimal("0.05");

	static boolean render = true;
	static boolean rendering = false;
	
	static GraphicsContext gc;
	
	@Override
    public void start(Stage primaryStage) {
		
	    Group root = new Group();
    	Scene s = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

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
    	
    	canvas.setOnMousePressed(e -> {
    		if (e.isPrimaryButtonDown()) {
	    		offsetX = offsetX.add( (new BigDecimal(e.getX()).subtract(new BigDecimal(WIDTH / 2))).divide(MAX.divide(zoom) ) );
	    		offsetY = offsetX.add( (new BigDecimal(e.getY()).subtract(new BigDecimal(HEIGHT / 2))).divide(MAX.divide(zoom) ) );
	    		render = true;
    		}
    	});
    	
    	canvas.setOnKeyPressed(e -> {
    		switch (e.getCode()) {
    			case SPACE:
    				MAX_DEPTH = MAX_DEPTH.multiply(new BigInteger("2"));
    				render = true;
    				break;
    			case Z:
    				zoom = zoom.divide(new BigDecimal("2"));
    				render = true;
    				break;
    			case X:
    				zoom = zoom.multiply(new BigDecimal("2"));
    				render = true;
    				break;
    		}
    	});
    	
    	AnimationTimer timer = new AnimationTimer() {
    		
    		BigDecimal x = new BigDecimal("0");
    		BigDecimal y = new BigDecimal("0");
    		
    	    @Override
    	    public void handle(long dt) 
    	    {	
    	    	if (render && !rendering) {
    	    		x = new BigDecimal("0");
    	    		y = new BigDecimal("0");
    	    		render = false;
    	    		rendering = true;
    	    		gc.clearRect(0, 0, WIDTH, HEIGHT);
    	    	}
    	    	for (int lol = 0; lol < WIDTH * 1; lol++) {
	    			Number2 c = new Number2((x.subtract(new BigDecimal(WIDTH / 2.0))).multiply(zoom).divide(MAX).add(offsetX), (y.subtract(new BigDecimal(HEIGHT / 2.0))).multiply(zoom).divide(MAX).add(offsetY));
	    			BigDecimal i = new BigDecimal("0"), j = new BigDecimal("0");
	    			BigInteger r = new BigInteger("0");
	    			while (i.multiply(i).add(j.multiply(j)).compareTo(new BigDecimal("4")) <= 0 && r.compareTo(MAX_DEPTH) == -1) {
	    				BigDecimal t = i.multiply(i).subtract(j.multiply(j)).add(c.r);
	    				j = new BigDecimal("2").multiply(i).multiply(j).add(c.i);
	    				i = t;
	    				r = r.add(new BigInteger("1"));
	    			}
	    			if (r.compareTo(MAX_DEPTH) == -1) {
	    				gc.setFill(Color.hsb(r.multiply(r).mod(new BigInteger("360")).intValueExact(),1,1));
    	    			gc.fillRect(x.intValueExact(), y.intValueExact(), 1, 1);
	    			}
//	    			System.out.println(x);
	    	    	x = x.add(new BigDecimal("1"));
	    	    	if (x.compareTo(new BigDecimal(WIDTH)) >= 0) {
	    	    		x = new BigDecimal("0");
	    	    		y = y.add(new BigDecimal("1"));
	    	    		if (y.compareTo(new BigDecimal(HEIGHT)) >= 0) {
	    	    			rendering = false;
	    	    			return;
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
