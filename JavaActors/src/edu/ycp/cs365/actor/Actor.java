package edu.ycp.cs365.actor;

/**
 * Actor superclass: all actor classes must extend this class
 * and override the {@link Actor#react(Message)} method.
 * 
 * @author David Hovemeyer
 */
public abstract class Actor {
	private ActorGroup group;
	private int index;
	
	/**
	 * Constructor.
	 */
	public Actor() {
	}
	
	/**
	 * React to a {@link Message}.
	 * 
	 * @param message the message to which the actor should react
	 */
	public abstract void react(Message message);
	
	/**
	 * Send a message.
	 * 
	 * @param recipient the {@link ActorId} of the recipient {@link Actor}
	 * @param contents  the contents of the message
	 */
	public void send(ActorId recipient, Object contents) {
		group.send(new ActorId(this.index), recipient, contents);
	}
	
	/**
	 * Spawn a new {@link Actor}.
	 * 
	 * @param actor the new actor
	 * @return the {@link ActorId} of the new actor
	 */
	public ActorId spawn(Actor actor) {
		return group.addActor(actor);
	}

	/**
	 * Actors can call this method if they receive a message
	 * with unexpected content.
	 * 
	 * @param content the unexpected content
	 */
	protected void unexpectedMessageContent(Object content) {
		throw new IllegalArgumentException("Unexpected message type: " + content.getClass().getSimpleName());
	}
	
	void setGroup(ActorGroup group) {
		this.group = group;
	}
	
	void setIndex(int index) {
		this.index = index;
	}
}
