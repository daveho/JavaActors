package edu.ycp.cs365.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActorGroup {
	private Object lock = new Object();
	private List<Actor> actorList;
	//private ConcurrentLinkedQueue<Actor> readyActorQueue;
	private ConcurrentLinkedQueue<Message> messageQueue;
	
	private class Worker implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					Message message = messageQueue.poll();
					if (message != null) {
						// process message
						Actor recipient;
						synchronized (lock) {
							recipient = actorList.get(message.getRecipientId().getIndex());
						}
						recipient.react(message);
					} else {
						// back off
						backOff();
					}
				}
			} catch (InterruptedException e) {
				// Just exit
			}
		}

		private void backOff() throws InterruptedException {
			Thread.sleep(200); // TODO: exponential backoff
		}
	}
	
	public ActorGroup() {
		this.actorList = new ArrayList<Actor>();
		this.messageQueue = new ConcurrentLinkedQueue<Message>();
	}

	void send(ActorId senderId, ActorId recipientId, Object content) {
		Message message = new Message(content, senderId, recipientId);
		messageQueue.add(message);
	}
	
	void addActor(Actor actor) {
		synchronized (lock) {
			int index = actorList.size();
			actorList.add(actor);
			actor.setIndex(index);
			actor.setGroup(this);
		}
	}
}
