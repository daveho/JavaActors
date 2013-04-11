package edu.ycp.cs365.actordemo;

import edu.ycp.cs365.actor.ActorGroup;

/**
 * A really simple demo of the Java actors framework:
 * computes iteration counts for complex numbers in a region of
 * the complex plane to determine which are in the Mandelbrot set.
 * 
 * @author David Hovemeyer
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		// The MandelbrotSpec object is the content of the initial
		// message to the root actor, which kicks off the computation
		MandelbrotSpec mspec = new MandelbrotSpec(-2.0, -2.0, 2.0, 2.0, 20, 20);
		
		
		// Create an Actor group with two worker threads
		ActorGroup group = new ActorGroup(2);
		
		// Create the root actor, which receives the MandelbrotSpec
		// and spawns RowActors to compute each row
		MandelbrotActor rootActor = new MandelbrotActor();
		
		// Start the computation
		System.out.println("Starting computation...");
		group.start(rootActor, mspec);

		// Wait for the computation to finish
		rootActor.waitUntilFinished();
		System.out.println("Computation finished");
		
		// Shutdown the worker threads
		group.shutdown();
		
		// Get the result and print the rows
		Grid result = rootActor.getResult();
		for (Row r : result.getRowList()) {
			int[] iterCounts = r.getIterCounts();
			for (int i = 0; i < iterCounts.length; i++) {
				if (i > 0) {
					System.out.print(",");
				}
				System.out.print(iterCounts[i]);
			}
			System.out.println();
		}
		
		System.out.println("All done!");
	}
}
