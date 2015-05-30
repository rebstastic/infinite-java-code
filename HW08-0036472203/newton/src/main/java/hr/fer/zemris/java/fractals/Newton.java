package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Newton-Rapshon iteration-based drawing of Mandelbrot fractals. User must
 * provide wanted complex roots through standard input stream. Program accepts
 * complex roots such as: "3.51", "-3.17", "-2.71i", "i", "1", "-2.71-3.15i".
 * When done, user must type "done".
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Newton {

	/**
	 * Maximum number of Newton-Rapshon iterations.
	 */
	private static final int NUMBER_OF_ITERATIONS = 16 * 16 * 16;

	/**
	 * Convergence treshold for Newton-Rapshon iteration.
	 */
	private static final double CONVERGENCE_TRESHOLD = 0.001;

	/**
	 * Root treshold for Newton-Rapshon iteration.
	 */
	private static final double ROOT_TRESHOLD = 0.002;

	/**
	 * Polynomial used in for Newton-Rapshon iteration.
	 */
	private static ComplexRootedPolynomial polynomial;

	/**
	 * This method is called once the program is run. It takes polynomial roots
	 * from the user and shows drawn Mandelbrot fractals calculated with
	 * Newton-Rapshon iteration.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {

		System.out
				.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out
				.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner scanner = new Scanner(System.in);

		List<Complex> roots = new ArrayList<>();

		while (true) {
			int root = roots.size() + 1;
			System.out.println("Root " + root + "> ");
			String line = scanner.nextLine().trim();
			if (line.equalsIgnoreCase("done")) {
				System.out
						.println("Image of fractal will appear shortly. Thank you.");
				break;
			}
			try {
				Complex number = Complex.parse(line);
				roots.add(number);
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		scanner.close();

		polynomial = new ComplexRootedPolynomial(
				roots.toArray(new Complex[roots.size()]));
		FractalViewer.show(getParallelFractalProducer());

	}

	/**
	 * Returns parallel fractal producer. Fractal producer creates n threads,
	 * where n is number of available processors, and evenly divides the job of
	 * Mandelbrot fractals computing to those threads. Newton-Rapshon iteration
	 * is used.
	 * 
	 * @return Parallel fractal producer.
	 */
	private static IFractalProducer getParallelFractalProducer() {
		return new IFractalProducer() {

			@Override
			public void produce(double reMin, double reMax, double imMin,
					double imMax, int width, int height, long requestNo,
					IFractalResultObserver observer) {

				/**
				 * This class represents a job performed by threads
				 * independently.
				 * 
				 * @author Petra Rebernjak - 0036472203
				 *
				 */
				class Job implements Runnable {

					/**
					 * Starting horizontal line on the scene.
					 */
					private int start;

					/**
					 * Ending horisontal line on the scene.
					 */
					private int end;

					/**
					 * Index data.
					 */
					private short[] data;

					/**
					 * Constructor.
					 * 
					 * @param start
					 *            Starting horizontal line on the scene.
					 * @param end
					 *            Ending horisontal line on the scene.
					 * @param data
					 *            Index data.
					 */
					public Job(int start, int end, short[] data) {
						this.start = start;
						this.end = end;
						this.data = data;
					}

					@Override
					public void run() {
						int offset = start * width;
						for (int y = start; y <= end; y++) {
							for (int x = 0; x < width; x++) {
								double cre = x / (width - 1.0)
										* (reMax - reMin) + reMin;
								double cim = ((height - 1) - y)
										/ (height - 1.0) * (imMax - imMin)
										+ imMin;
								Complex c = new Complex(cre, cim);
								Complex zN = c;
								double module = 0;
								int iters = 0;
								do {
									Complex zN1 = zN.sub(polynomial.apply(zN)
											.divide(polynomial
													.toComplexPolynomial()
													.derive().apply(zN)));
									module = zN1.sub(zN).module();
									iters++;
									zN = zN1;
								} while (iters < NUMBER_OF_ITERATIONS
										&& module > CONVERGENCE_TRESHOLD);
								short index = (short) polynomial
										.indexOfClosestRootFor(zN,
												ROOT_TRESHOLD);
								data[offset++] = (short) (index + 1);
							}
						}
					}

				}

				int numberOfThreads = Runtime.getRuntime()
						.availableProcessors();

				ExecutorService pool = Executors
						.newFixedThreadPool(numberOfThreads);
				List<Future<?>> results = new ArrayList<>();

				short[] data = new short[width * height];

				int numberOfJobs = 8 * numberOfThreads;
				Job[] jobs = new Job[numberOfJobs];
				int strips = height / numberOfJobs;
				for (int i = 0; i < numberOfJobs; i++) {
					int start = i * strips;
					int end = (i + 1) * strips - 1;
					if (i == numberOfJobs - 1) {
						end = height - 1;
					}
					jobs[i] = new Job(start, end, data);
					results.add(pool.submit(jobs[i]));
				}

				for (Future<?> job : results) {
					while (true) {
						try {
							job.get();
						} catch (InterruptedException | ExecutionException e) {
						}
						break;
					}
				}

				pool.shutdown();
				observer.acceptResult(data, (short) (polynomial
						.toComplexPolynomial().order() + 1), requestNo);
			}

		};
	}

}
