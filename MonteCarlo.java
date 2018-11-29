import java.util.Random;
import java.util.ArrayList;

class Circle{
    double centerX;
    double centerY;
    double radius;
    public Circle(double x, double y, double r){
        this.centerX = x;
        this.centerY = y;
        this.radius = r;
    }
}

class Field{
	public static double MIN_WIDTH = 0;
	public static double MIN_HEIGHT = 0;
	public static double MAX_WIDTH = 0;
	public static double MAX_HEIGHT = 0;
	public static double AREA = 0;
	public static int positiveSamples = 0;
}

public class MonteCarlo{
	private static ArrayList<Circle> circs = new ArrayList<Circle>();
    static Random rand = new Random();
    public static void main(String[] args){
        circs.add(new Circle(-481, 252, 10));
        circs.add(new Circle(-481, 252, 10));
        circs.add(new Circle(-481, 248, 10));
        
        setMinimumWidth();
    	setMinimumHeight();
    	setMaximumWidth();
    	setMaximumHeight();
    	System.out.println(Field.MIN_WIDTH + " " + Field.MAX_WIDTH + " " + Field.MIN_HEIGHT + " " + Field.MAX_HEIGHT);
    	
    	System.out.println(estimateArea(circs, 1000000));
    }
    private static void setMinimumWidth() {
    	for(int i = 0; i < circs.size(); i++) {
    		if (i == 0) {
    			Field.MIN_WIDTH = circs.get(i).centerX - circs.get(i).radius;
    		}
    		else if (circs.get(i).centerX - circs.get(i).radius < Field.MIN_WIDTH) {
    			Field.MIN_WIDTH = circs.get(i).centerX - circs.get(i).radius;
    		}
    	}
    }
    private static void setMinimumHeight() {
    	for(int i = 0; i < circs.size(); i++) {
    		if (i == 0) {
    			Field.MIN_HEIGHT = circs.get(i).centerY - circs.get(i).radius;
    		}
    		else if (circs.get(i).centerY - circs.get(i).radius < Field.MIN_HEIGHT) {
    			Field.MIN_HEIGHT = circs.get(i).centerY - circs.get(i).radius;
    		}
    	}
    }
    
    private static void setMaximumWidth() {
    	for(int i = 0; i < circs.size(); i++) {
    		if (i == 0) {
    			Field.MAX_WIDTH = circs.get(i).centerX + circs.get(i).radius;
    		}
    		else if (circs.get(i).centerX + circs.get(i).radius > Field.MAX_WIDTH) {
    			Field.MAX_WIDTH = circs.get(i).centerX + circs.get(i).radius;
    		}
    	}
    }
    private static void setMaximumHeight() {
    	for(int i = 0; i < circs.size(); i++) {
    		if (i == 0) {
    			Field.MAX_HEIGHT = circs.get(i).centerY + circs.get(i).radius;
    		}
    		else if (circs.get(i).centerY + circs.get(i).radius > Field.MAX_HEIGHT) {
    			Field.MAX_HEIGHT = circs.get(i).centerY + circs.get(i).radius;
    		}
    	}
    }
    private static double sample(double min, double max){
        //This method returns a random number between min and max
        return min + (max - min) * rand.nextDouble();
    }
    private static boolean isIn(double x, double y, Circle c){
        //This method returns true if the point (x, y) is within the circle c, and false otherwise
        double dist = Math.sqrt(Math.pow(x - c.centerX, 2) + Math.pow(y - c.centerY, 2));
        return dist <= c.radius;
    }
    public static double estimateArea(ArrayList<Circle> circles, int numSamples){
    //YOUR CODE HERE
    	for(int i = 0; i < numSamples; i++) {
    		double sampleX = MonteCarlo.sample(Field.MIN_WIDTH, Field.MAX_WIDTH);
    		double sampleY = MonteCarlo.sample(Field.MIN_HEIGHT, Field.MAX_HEIGHT);
    		for(Circle circle : circles) {
    			if(MonteCarlo.isIn(sampleX, sampleY, circle)) {
    				Field.positiveSamples++;
    				break;
    			}
    		}
    	}
    	Field.AREA = (Field.MAX_WIDTH - Field.MIN_WIDTH) * (Field.MAX_HEIGHT - Field.MIN_HEIGHT);
    	double blobArea = ( (double) Field.positiveSamples)/((double)numSamples)*Field.AREA;
    	// to prevent counting twice when painComponent does so
    	Field.positiveSamples = 0;
    	return blobArea;
    }
}
