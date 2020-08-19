// © 2018 Lucas Jaggernauth
// Do not copy, cite, or distribute without permission of the author

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class Number {
	double r;
	double i;
	
	Number (double r, double i) {
		this.r = r;
		this.i = i;
	}
}

// Includes Drawing
public class MandelbrotSet extends Application {
	
	// VIEW SETTINGS
	static final int WIDTH = 400;
	static final int HEIGHT = 400;
	static final double MAX = 1000; //WIDTH > HEIGHT ? WIDTH : HEIGHT;
	static long MAX_DEPTH = 10;
	
	static double zoom = 5;
	static double offsetX = 0;
	static double offsetY = 0;

	static double colors = 0.05;

	static boolean render = true;
	static boolean rendering = false;
	
	static GraphicsContext gc;
	
	// Power for julia set
	static double n = 2;
	static double a = Math.PI;
	
	public static double juliaR(double x, double y, double c) {
		return Math.pow((x*x + y*y), (n / 2)) * Math.cos(n * Math.atan2(y, x)) + c + Math.log(Math.hypot(x, y));
	}
	
	public static double juliaI(double x, double y, double c) {
		return Math.pow((x*x + y*y), (n / 2)) * Math.sin(n * Math.atan2(y, x)) + c;
	}

	public static double mandelbrotR(double x, double y, double c) {
		return x*x - y*y + c;
	}
	
	public static double mandelbrotI(double x, double y, double c) {
		return 2*x*y + c;
	}
	
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
    			if (!rendering) {
		    		offsetX += (e.getX() - WIDTH / 2) / (MAX / zoom);
		    		offsetY += (e.getY() - HEIGHT / 2) / (MAX / zoom);
		    		render = true;
    			}
    		}
    	});
    	
    	canvas.setOnKeyPressed(e -> {
    		switch (e.getCode()) {
    			case SPACE:
    				MAX_DEPTH *= 2;
    				render = true;
    				break;
    			case Z:
    				zoom /= 2;
    				render = true;
    				break;
    			case X:
    				zoom *= 2;
    				render = true;
    				break;
    			case C:
    				n -= 0.05;
    				render = true;
    				break;
    			case V:
    				n += 0.05;
    				render = true;
    				break;
    				
    			default:
    				break;
    		}
    	});
    	
    	AnimationTimer timer = new AnimationTimer() {
    		
    		double x = 0;
    		double y = 0;
    		
    	    @Override
    	    public void handle(long dt) 
    	    {	
    	    	if (render) {
    	    		x = 0;
    	    		y = 0;
    	    		render = false;
    	    		rendering = true;
    	    		gc.clearRect(0, 0, WIDTH, HEIGHT);
    	    	}
    	    	for (int lol = 0; lol < WIDTH*50; lol++) {
    	    			Number c = new Number(offsetX + (x - WIDTH / 2.0) * zoom / MAX, offsetY + (y - HEIGHT / 2.0) * zoom / MAX);
    	    			double i = 0, j = 0;
    	    			int r = 0;
    	    			while (i*i+j*j <= 8 && r++ < MAX_DEPTH) {
    	    				double t = mandelbrotR(i, j, c.r);
    	    				j = mandelbrotI(i, j, c.i);
    	    				i = t;
    	    			}
    	    			if (r < MAX_DEPTH) {
    	    				gc.setFill(Color.hsb(colors*r*r%360,1,1));
        	    			gc.fillRect(x, y, 1, 1);
    	    			}
    	    	if (++x >= WIDTH) {
    	    		x = 0;
    	    		if (++y >= HEIGHT) {
    	    			rendering = false;
    	    			gc.setFill(Color.WHITE);
    	    			gc.fillText((1/zoom) + "x", 200, 30);
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
