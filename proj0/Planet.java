/** The Planet class */
public class Planet {
	double xxPos;
	double yyPos;
	double xxVel;
	double yyVel;
	double mass;
	String imgFileName;
	static final double GRAVITYCONST = 6.67e-11;
	/** Constructor */
	public Planet(double xP, double yP, double xV,
				  double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	/** Constructor */
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	/** Returns the distance to planet p */
	public double calcDistance(Planet p){
		double xxDistance = Math.abs(p.xxPos - this.xxPos);
		double yyDistance = Math.abs(p.yyPos - this.yyPos);
		return Math.sqrt(xxDistance*xxDistance + yyDistance*yyDistance);
	}
	/** Returns the force exerted by planet p */
	public double calcForceExertedBy(Planet p) {
		double distance = calcDistance(p);
		return GRAVITYCONST * this.mass * p.mass / (distance * distance);
	}
	/** Returns the x component force exerted by planet p */
	public double calcForceExertedByX(Planet p) {
		double xxDistance = p.xxPos - this.xxPos;
		return calcForceExertedBy(p) * xxDistance / calcDistance(p);
	}
	/** Returns the y component force exerted by planet p */
	public double calcForceExertedByY(Planet p) {
		double yyDistance = p.yyPos - this.yyPos;
		return calcForceExertedBy(p) * yyDistance / calcDistance(p);
	}
	/** Updates the position and velocity of the planet */
	public void update(double dt, double fX, double fY) {
		double xxAcceleration = fX / this.mass;
		double yyAcceleration = fY / this.mass;
	// Update velocity:
		this.xxVel += dt * xxAcceleration;
		this.yyVel += dt * yyAcceleration;
	// Update position 
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}
	/** Draw planet */
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
	}
}