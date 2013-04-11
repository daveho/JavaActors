package edu.ycp.cs365.actor;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Actor {
	private ActorGroup group;
	private int index;
	private ConcurrentLinkedQueue<Message> mailbox;
	
	public Actor() {
		this.mailbox = new ConcurrentLinkedQueue<Message>();
	}
	
	public abstract void react(Message message);
	
	public void send(ActorId recipient, Object contents) {
		group.send(new ActorId(this.index), recipient, contents);
	}
	
	public ActorId spawn(Actor actor) {
		return group.addActor(actor);
	}

	protected void unexpectedMessageContent(Object content) {
		throw new IllegalArgumentException("Unexpected message type: " + content.getClass().getSimpleName());
	}
	
	void setGroup(ActorGroup group) {
		this.group = group;
	}
	
	void setIndex(int index) {
		this.index = index;
	}
	
	Message dequeueNextMessage() {
		return mailbox.remove();
	}
}
