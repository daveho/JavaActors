package edu.ycp.cs365.actordemo;

import edu.ycp.cs365.actor.ActorGroup;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		MandelbrotSpec mspec = new MandelbrotSpec(-2.0, -2.0, 2.0, 2.0, 20, 20);
		
		System.out.println("Starting computation...");
		ActorGroup group = new ActorGroup(2);
		MandelbrotActor rootActor = new MandelbrotActor();
		group.start(rootActor, mspec);
		
		System.out.println("Computation finished");
		
		rootActor.waitUntilFinished();
		
		group.shutdown();
		
		// Get the result and sort the rows
		Grid result = rootActor.getResult();
		result.sort();
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
		
		System.out.println("Exiting");
	}
}
