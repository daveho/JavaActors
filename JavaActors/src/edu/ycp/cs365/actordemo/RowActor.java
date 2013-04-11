package edu.ycp.cs365.actordemo;

import edu.ycp.cs365.actor.Actor;
import edu.ycp.cs365.actor.Message;

/**
 * Actor class that receives a {@link RowSpec} and computes iteration
 * counts for each point in that row.
 * 
 * @author David Hovemeyer
 */
public class RowActor extends Actor {
	public static final int MAX_ITERS = 800;
	
	@Override
	public void react(Message message) {
		Object content = message.getContent();
		
		if (content instanceof RowSpec) {
			// Compute the iteration counts for this row
			RowSpec rowSpec = (RowSpec) content;
			Row row = new Row(rowSpec.getY(), rowSpec.getNumSamples());
			compute(row, rowSpec);
			
			// Send the computed row back to the sender
			send(message.getSenderId(), row);
		} else {
			unexpectedMessageContent(content);
		}
	}

	private void compute(Row row, RowSpec rowSpec) {
		for (int i = 0; i < rowSpec.getNumSamples(); i++) {
			Complex c = new Complex(rowSpec.getXmin() + i*rowSpec.getDx(), rowSpec.getY());
			Complex z = new Complex(0.0, 0.0);
			int count = 0;
			while (z.magnitude() < 2.0 && count < 800) {
				z = z.multiply(z).add(c);
				count++;
			}
			row.setIterCount(i, count);
		}
	}
}
