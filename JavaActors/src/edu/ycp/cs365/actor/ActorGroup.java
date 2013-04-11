package edu.ycp.cs365.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActorGroup {
	private Object lock = new Object();
	private List<Actor> actorList;
	private ConcurrentLinkedQueue<Message> messageQueue;
	private int numWorkers;
	private volatile boolean finished;
	
	private class Worker implements Runnable {
		private int backoffTime;
		
		@Override
		public void run() {
			try {
				backoffTime = 5;
				
				while (!finished) {
					Message message = messageQueue.poll();
					if (message != null) {
						// reset backoff timer
						backoffTime = 5;
						
						// process message
						Actor recipient;
						synchronized (lock) {
							recipient = actorList.get(message.getRecipientId().getIndex());
						}
						recipient.react(message);
					} else {
						// sleep for a bit
						idle();
					}
				}
			} catch (InterruptedException e) {
				// Just exit
			}
			//System.out.println("Worker exiting");
		}

		private void idle() throws InterruptedException {
			// Sleep using an exponential backoff, starting at
			// 5 ms and increasing to 640 ms
			Thread.sleep(backoffTime);
			if (backoffTime < 640) {
				backoffTime *= 2;
			}
		}
	}
	
	public ActorGroup(int numWorkers) {
		this.actorList = new ArrayList<Actor>();
		this.messageQueue = new ConcurrentLinkedQueue<Message>();
		this.numWorkers = numWorkers;
		this.finished = false;
	}
	
	public void start(Actor rootActor, Object startMessageContent) {
		addActor(rootActor);
		
		// Start worker threads
		for (int i = 0; i < numWorkers; i++) {
			new Thread(new Worker()).start();
		}
		
		// The start message is the only message that doesn't
		// specify a sender
		send(null, new ActorId(0), startMessageContent);
	}
	
	public void shutdown() {
		this.finished = true;
	}

	void send(ActorId senderId, ActorId recipientId, Object content) {
		Message message = new Message(content, senderId, recipientId);
		messageQueue.add(message);
	}
	
	ActorId addActor(Actor actor) {
		synchronized (lock) {
			int index = actorList.size();
			actorList.add(actor);
			actor.setIndex(index);
			actor.setGroup(this);
			return new ActorId(index);
		}
	}
}
