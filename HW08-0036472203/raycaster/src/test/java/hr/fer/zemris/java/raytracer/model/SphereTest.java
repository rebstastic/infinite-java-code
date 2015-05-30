package hr.fer.zemris.java.raytracer.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class SphereTest {

	private static Sphere sphere;

	@BeforeClass
	public static void before() {
		sphere = new Sphere(new Point3D(), 5, 10, 10, 10, 15, 15, 15, 20);
	}

	@Test
	public void findClosestRayIntersection_TwoIntersections() {
		Ray ray = Ray.fromPoints(new Point3D(), new Point3D(2, 2, 2));
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		assertTrue(intersection.getKdr() == 10);
		assertTrue(intersection.getKdg() == 10);
		assertTrue(intersection.getKdb() == 10);
		assertTrue(intersection.getKrr() == 15);
		assertTrue(intersection.getKrg() == 15);
		assertTrue(intersection.getKrb() == 15);
		assertEquals(intersection.getPoint().x, -2.886751345948129, 1e-6);
		assertEquals(intersection.getPoint().y, -2.886751345948129, 1e-6);
		assertEquals(intersection.getPoint().z, -2.886751345948129, 1e-6);
	}

	@Test
	public void findClosestRayIntersection_OneIntersection() {
		Ray ray = Ray.fromPoints(new Point3D(5, 1, 1), new Point3D(5, 0, 0));
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		assertTrue(intersection.getKdr() == 10);
		assertTrue(intersection.getKdg() == 10);
		assertTrue(intersection.getKdb() == 10);
		assertTrue(intersection.getKrr() == 15);
		assertTrue(intersection.getKrg() == 15);
		assertTrue(intersection.getKrb() == 15);
		assertEquals(intersection.getPoint().x, 5.0, 1e-6);
		assertEquals(intersection.getPoint().y, 0, 1e-6);
		assertEquals(intersection.getPoint().z, 0, 1e-6);
	}

	@Test
	public void findClosestRayIntersection_NoIntersections() {
		Ray ray = Ray.fromPoints(new Point3D(6, 1, 1), new Point3D(6, 0, 0));
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		assertNull(intersection);
	}

}
