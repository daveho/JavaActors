package edu.ycp.cs365.actordemo;

import edu.ycp.cs365.actor.Actor;
import edu.ycp.cs365.actor.ActorId;
import edu.ycp.cs365.actor.Message;

public class MandelbrotActor extends Actor {
	private Grid result;
	private int numRowsExpected;
	private boolean done;
	private Object lock;
	
	public MandelbrotActor() {
		result = new Grid();
		done = false;
		lock = new Object();
	}
	
	@Override
	public void react(Message message) {
		Object content = message.getContent();
		
		if (content instanceof MandelbrotSpec) {
			System.out.println("Received MandelbrotSpec");
			MandelbrotSpec mspec = (MandelbrotSpec) content;
			this.numRowsExpected = mspec.getNrows();
			
			// Create RowActors
			double dy = (mspec.getYmax() - mspec.getYmin()) / mspec.getNrows();
			double dx = (mspec.getXmax() - mspec.getXmin()) / mspec.getNcols();
			
			for (int j = 0; j < mspec.getNrows(); j++) {
				// Create a specification for the row of iteration counts to be computed
				RowSpec rowSpec = new RowSpec(mspec.getXmin(), mspec.getYmin() + j*dy, dx, mspec.getNcols());
				
				// Spawn an actor to compute this row
				ActorId id = spawn(new RowActor());
				
				// Send the row specification to the new RowActor
				send(id, rowSpec);
			}
		} else if (content instanceof Row) {
			System.out.println("Received Row " + ((Row)content).getY());
			
			// Received a computed row
			result.addRow((Row) content);
			
			// Have all rows been computed?
			if (result.getRowList().size() == numRowsExpected) {
				finished();
			}
		} else {
			unexpectedMessageContent(content);
		}
	}

	private void finished() {
		synchronized (lock) {
			done = true;
			lock.notifyAll();
		}
	}
	
	public void waitUntilFinished() throws InterruptedException {
		synchronized (lock) {
			while (!done) {
				lock.wait();
			}
		}
	}
	
	public Grid getResult() {
		return result;
	}
}
