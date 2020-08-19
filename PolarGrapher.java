// © 2018 Lucas Jaggernauth
// Do not copy, cite, or distribute without permission of the author

import java.text.DecimalFormat;

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
import javafx.scene.canvas.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Includes Drawing
public class PolarGrapher extends Application {
	
	// VIEW SETTINGS
	static final int WIDTH = 900;
	static final int HEIGHT = 900;
	
	static final double p = 0.01;
	static final double mt = 3600;
	
	static double offsetX;
	static double offsetY;
	
	static double mx = 0;
	static double my = 0;
	static boolean dragging = false;
	
	static double inspectX = 0;
	static double inspectY = 0;
	static boolean inspecting = false;
	
	static double zoom = 1;
	
	static GraphicsContext gc;
	
	public static double graph(double t) {
		return 40 * (Math.sin(Math.PI * t / 4) + 2);
	}
	
	public static void drawGraph() {
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.setStroke(Color.rgb(255, 255, 255, 0.5));
		gc.strokeLine(0, -offsetY / zoom + HEIGHT / 2, WIDTH, -offsetY / zoom + HEIGHT / 2);
		gc.strokeLine(-offsetX + WIDTH / 2, 0, -offsetX + WIDTH / 2, HEIGHT);
		gc.setStroke(Color.WHITE);
		double _r = graph(0);
		double _x = _r / zoom - offsetX + WIDTH / 2;
		double _y = -offsetY / zoom + WIDTH / 2;
		
		for (double t = 1; t <= mt + p; t+=p) {
			double r = graph(t);
			double x = Math.cos(Math.toRadians(t)) * r / zoom - offsetX + WIDTH / 2;
			double y = (-Math.sin(Math.toRadians(t)) * r - offsetY) / zoom + WIDTH / 2;
//			gc.fillRect(x, (y + offsetY) / -zoom + HEIGHT / 2, 1, 1);
			gc.strokeLine(_x, _y, x, y);
			_x = x;
			_y = y;
		}
		
		if (inspecting) {
			gc.setStroke(Color.rgb(0, 255, 0, 0.75));
			gc.strokeLine(0, -inspectY, WIDTH, -inspectY);
			gc.strokeLine(inspectX, 0, inspectX, HEIGHT);
			
			gc.setTextAlign(TextAlignment.RIGHT);
    		gc.setTextBaseline(VPos.BOTTOM);
    		gc.setFont(Font.font("Helvetica", FontWeight.LIGHT, 20));
    		gc.setFill(Color.rgb(0, 255, 0, 0.75));
//    		gc.fillText(Math.round((inspectX-WIDTH/2 + offsetX) * zoom * 100) / 100.00 + ", " + Math.round((-offsetY + zoom * (HEIGHT/2 + inspectY)) * 100) / 100.0, inspectX - 3, -inspectY);
    		gc.fillText((inspectX-WIDTH/2 + offsetX) * zoom + ", " + (-offsetY + zoom * (HEIGHT/2 + inspectY)), inspectX - 3, -inspectY);
		}
	}
	
	@Override
    public void start(Stage primaryStage) {
		
	    Group root = new Group();
    	Scene s = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

    	final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    	gc = canvas.getGraphicsContext2D();
    	gc.setStroke(Color.WHITE);
    	gc.setFill(Color.WHITE);
    	 
    	root.getChildren().add(canvas);
    
    	primaryStage.initStyle(StageStyle.UNDECORATED);
    	primaryStage.setTitle("Graph");
    	primaryStage.setScene(s);
    	primaryStage.setResizable(false);
    	primaryStage.show();
    	
    	canvas.setFocusTraversable(true);
	
    	primaryStage.setOnCloseRequest(e -> {
	    	
	    	System.exit(0);
	    	
    	});
    	
    	canvas.setOnMousePressed(e -> {
    		if (e.isPrimaryButtonDown()) {
	    		mx = e.getX();
	    		my = e.getY();
	    		dragging = true;
    		}
    		else if (e.isSecondaryButtonDown()) {
    			inspectX = e.getX();
    			inspectY = -e.getY();
    			inspecting = true;
    		}
    	});
    	
    	canvas.setOnMouseReleased(e -> {
    		dragging = false;
    		inspecting = false;
    	});
    	
    	canvas.setOnKeyPressed(e -> {
    		switch(e.getCode()) {
    			case SPACE:
    				offsetX = 0;
    				offsetY = 0;
    				zoom = 1;
    				break;
    				
    			default:
    				break;
    		}
    	});;
    	
    	canvas.setOnMouseDragged(e -> {
    		if (e.isPrimaryButtonDown()) {
	    		if (dragging) {
					offsetX -= (e.getX() - mx);
					offsetY -= (e.getY() - my) * zoom;
					mx = e.getX();
		    		my = e.getY();
		    		inspecting = false;
	    		}
    		}
    		else if (e.isSecondaryButtonDown()) {
    			inspectX = e.getX();
    			inspectY = -e.getY();
    		}
    	});
    	
    	canvas.setOnScroll(e -> {
    		double pz = zoom;
    		zoom /= (1 + 0.01 * (e.getDeltaY() < 0 ? -1 : 1) * Math.sqrt(Math.abs(e.getDeltaY())));
    	});
    	
    	AnimationTimer timer = new AnimationTimer() {
    		
    	    @Override
    	    public void handle(long dt) 
    	    {	
    	    	drawGraph();
    	    }
    	    
    	};
    	
    	timer.start();
	    	
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
}
