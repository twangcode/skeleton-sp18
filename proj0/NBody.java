public class NBody {
	static String backgroudImage = "images/starfield.jpg";

	/** Returns the radius of the universe from file fileName */
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int n = in.readInt();
		double radius = in.readDouble();
		return radius; 
	}
	/** Returns an array of object Planet from file fileName */
	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int n = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[n]; 
		for (int i=0; i<n; i=i+1) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();

			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);

		}
		return planets;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double radius = readRadius(filename);

		StdDraw.enableDoubleBuffering();

		double timeElapsed = 0.0;
		while (timeElapsed < T){
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];

			for(int i=0; i<planets.length; i=i+1){
				double totalForceX = 0.0;
				double totalForceY = 0.0;
				for (int j=0; j<planets.length; j=j+1){
					if(j!=i){
						totalForceX += planets[i].calcForceExertedByX(planets[j]);
						totalForceY += planets[i].calcForceExertedByY(planets[j]);
					}
				}
				xForces[i] = totalForceX;
				yForces[i] = totalForceY;
			}
			for(int i=0; i<planets.length; i=i+1){
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			StdDraw.setScale(-radius, radius);
			StdDraw.picture(0, 0, backgroudImage);
			for (int i=0; i<planets.length; i=i+1){
				planets[i].draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			timeElapsed += dt;
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
		                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
		
	}
}