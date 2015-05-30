package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents a simplification of a ray-tracer for rendering of 3D
 * scenes. It is not a full-blown ray tracer but a ray-caster instead. Object on
 * the scene are spheres. Rastering is performed using multiple threads.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class RayCaster {

	/**
	 * This method is called once the program is run. It shows the scene
	 * constainig multiple sphere objects rendered using ray-casting algorithm.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Return producer that renderes an image using ray-casting algorithm.
	 * 
	 * @return Producer that renderes an image.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = view.sub(eye).modifyNormalize(); // eye-view
				Point3D yAxis = viewUp.sub(
						zAxis.scalarMultiply(zAxis.scalarProduct(viewUp)))
						.modifyNormalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).modifyNormalize();

				Point3D screenCorner = view.sub(
						xAxis.scalarMultiply(horizontal / 2.0)).add(
						yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];

				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {

						// Pixel.
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply((x * horizontal)
										/ (width - 1.0))).sub(
								yAxis.scalarMultiply((y * vertical)
										/ (height - 1.0)));

						// Ray from eye-position to pixel.
						Ray ray = Ray.fromPoints(eye, screenPoint);

						// Determine closest intersection and color.
						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}

			/**
			 * Traces scene using ray-casting model. If the ray does not
			 * intersect any objects in a scene, black is set.
			 * 
			 * @param scene
			 *            The scene.
			 * @param ray
			 *            The eye-screen pixel ray.
			 * @param rgb
			 *            RGB array.
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb) {

				rgb[0] = rgb[1] = rgb[2] = 0;

				RayIntersection closestIntersection = determineClosestIntersection(
						scene, ray);

				if (closestIntersection != null) {
					determineColorFor(scene, ray, rgb, closestIntersection);
				}

			}

			/**
			 * Returns closest intersection of a ray with some object in the
			 * scene or null if ray doesn't intersect any objects.
			 * 
			 * @param scene
			 *            The scene.
			 * @param ray
			 *            The eye-screen pixel ray.
			 * @return Closest intersection of a ray with some object in the
			 *         scene or null if ray doesn't intersect any objects.
			 */
			private RayIntersection determineClosestIntersection(Scene scene,
					Ray ray) {

				RayIntersection closestIntersection = null;

				double minimalDistance = Double.MAX_VALUE;

				for (GraphicalObject object : scene.getObjects()) {
					RayIntersection intersection = object
							.findClosestRayIntersection(ray);
					if (intersection != null) {
						double distance = intersection.getDistance();
						if (distance < minimalDistance) {
							minimalDistance = distance;
							closestIntersection = intersection;
						}
					}
				}

				return closestIntersection;

			}

			/**
			 * Determines a color of given intersection. If intersection is
			 * hidden by some other object in the scene, the ambient color is
			 * set. Otherwise, diffuse component and reflective component of the
			 * color is added to the ambient component of a color.
			 * 
			 * @param scene
			 *            The scene.
			 * @param ray
			 *            The eye-screen pixel ray.
			 * @param rgb
			 *            RGB array.
			 * @param closestEyeObjectIntersection
			 *            Closest intersection of a ray with some object in the
			 *            scene.
			 */
			private void determineColorFor(Scene scene, Ray ray, short[] rgb,
					RayIntersection closestEyeObjectIntersection) {

				rgb[0] = rgb[1] = rgb[2] = 15;

				for (LightSource source : scene.getLights()) {
					Ray shadowRay = Ray.fromPoints(source.getPoint(),
							closestEyeObjectIntersection.getPoint());
					RayIntersection closestShadowRayObjectIntersection = determineClosestIntersection(
							scene, shadowRay);
					if (closestShadowRayObjectIntersection != null) {
						double sourceIntersectionDistance = source
								.getPoint()
								.sub(closestShadowRayObjectIntersection
										.getPoint()).norm();
						double eyeIntersectionDistance = source.getPoint()
								.sub(closestEyeObjectIntersection.getPoint())
								.norm();

						if (Math.abs(sourceIntersectionDistance
								- eyeIntersectionDistance) < 1e-6) {

							Point3D n = closestShadowRayObjectIntersection
									.getNormal().modifyNormalize();
							Point3D l = source
									.getPoint()
									.sub(closestShadowRayObjectIntersection
											.getPoint()).modifyNormalize();

							addDiffuseComponent(rgb,
									closestShadowRayObjectIntersection, source,
									n, l);
							addReflectiveComponent(shadowRay, rgb,
									closestShadowRayObjectIntersection, source,
									n, l);
						}
					}
				}
			}

			/**
			 * Adds diffuse component of a color to given color.
			 * 
			 * @param rgb
			 *            RGB array.
			 * @param intersection
			 *            Intersection of a ray with some object in the scene.
			 * @param source
			 *            The light source.
			 * @param n
			 *            Normal vector of the object starting at intersection
			 *            point.
			 * @param l
			 *            Light vector.
			 */
			private void addDiffuseComponent(short[] rgb,
					RayIntersection intersection, LightSource source,
					Point3D n, Point3D l) {

				double cosPhi = Math.max(0, l.scalarProduct(n));

				rgb[0] += source.getR() * intersection.getKdr() * cosPhi;
				rgb[1] += source.getG() * intersection.getKdg() * cosPhi;
				rgb[2] += source.getB() * intersection.getKdb() * cosPhi;

			}

			/**
			 * Adds reflective component of a color to the given color.
			 * 
			 * @param ray
			 *            The eye-screen pixel ray.
			 * @param rgb
			 *            RGB array.
			 * @param intersection
			 *            Intersection of a ray with some object in the scene.
			 * @param source
			 *            The light source.
			 * @param n
			 *            Normal vector of the object starting at intersection
			 *            point.
			 * @param l
			 *            Light vector.
			 */
			private void addReflectiveComponent(Ray ray, short[] rgb,
					RayIntersection intersection, LightSource source,
					Point3D n, Point3D l) {

				Point3D r = n.scalarMultiply(l.scalarProduct(n))
						.scalarMultiply(2).sub(l).modifyNormalize();
				Point3D v = ray.start.sub(intersection.getPoint());
				double cosAlpha = r.normalize().scalarProduct(v.normalize());

				if (cosAlpha >= 0) {
					cosAlpha = Math.pow(cosAlpha, intersection.getKrn());
					rgb[0] += source.getR() * intersection.getKrr() * cosAlpha;
					rgb[1] += source.getG() * intersection.getKrg() * cosAlpha;
					rgb[2] += source.getB() * intersection.getKrb() * cosAlpha;
				}

			}

		};
	}
}
