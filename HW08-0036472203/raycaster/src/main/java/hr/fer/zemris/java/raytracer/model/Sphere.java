package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a sphere object. It provides a method that finds an
 * intersection of the line with this sphere.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of a sphere.
	 */
	private Point3D center;

	/**
	 * Radius of a sphere.
	 */
	private double radius;

	/**
	 * Diffuse component coefficient for color red.
	 */
	private double kdr;

	/**
	 * Diffuse component coefficient for color green.
	 */
	private double kdg;

	/**
	 * Diffuse component coefficient for color blue.
	 */
	private double kdb;

	/**
	 * Reflective component coefficient for color red.
	 */
	private double krr;

	/**
	 * Reflective component coefficient for color green.
	 */
	private double krg;

	/**
	 * Reflective component coefficient for color blue.
	 */
	private double krb;

	/**
	 * Exponent to power the angle when calculating reflective color component.
	 */
	private double krn;

	/**
	 * Constructor.
	 * 
	 * @param center
	 *            Center of a sphere.
	 * @param radius
	 *            Radius of a sphere.
	 * @param kdr
	 *            Diffuse component coefficient for color red.
	 * @param kdg
	 *            Diffuse component coefficient for color green.
	 * @param kdb
	 *            Diffuse component coefficient for color blue.
	 * @param krr
	 *            Reflective component coefficient for color red.
	 * @param krg
	 *            Reflective component coefficient for color green.
	 * @param krb
	 *            Reflective component coefficient for color blue.
	 * @param krn
	 *            Exponent to power the angle when calculating reflective color
	 *            component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D v = ray.start.sub(center);

		double a = ray.direction.scalarProduct(ray.direction);
		double b = ray.direction.scalarMultiply(2).scalarProduct(v);
		double c = v.scalarProduct(v) - Math.pow(radius, 2);

		double D = Math.pow(b, 2) - 4 * a * c;

		if (D < 0) {
			return null;
		}

		D = Math.sqrt(D);

		double t1 = (-b + D) / (2 * a);
		double t2 = (-b - D) / (2 * a);

		if (t1 < 0 && t2 < 0) {
			return null;
		}

		Point3D t1Intersection = ray.start
				.add(ray.direction.scalarMultiply(t1));
		Point3D t2Intersection = ray.start
				.add(ray.direction.scalarMultiply(t2));

		double distance = 0;
		Point3D closestIntersection = (ray.start.sub(t1Intersection).norm() < ray.start
				.sub(t2Intersection).norm()) ? t1Intersection : t2Intersection;
		distance = ray.start.sub(closestIntersection).norm();

		return new RayIntersection(closestIntersection, distance,
				(closestIntersection.sub(center).norm() > radius)) {

			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
