package edu.ycp.cs365.actor;

/**
 * An opaque identifier for an {@link Actor} within an {@link ActorGroup}.
 * Used for specifying sender and recipient when a {@link Message}
 * is sent.  Note that there are no public methods.
 * 
 * @author David Hovemeyer
 */
public class ActorId {
	private final int index;
	
	ActorId(int index) {
		this.index = index;
	}
	
	int getIndex() {
		return index;
	}
}
