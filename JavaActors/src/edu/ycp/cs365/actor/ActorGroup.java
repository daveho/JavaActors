// Java actors framework
// Copyright (C) 2013, David Hovemeyer <david.hovemeyer@gmail.com>
//
// Permission is hereby granted, free of charge, to any person obtaining a 
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included 
// in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
// THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
// OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
// ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.

package edu.ycp.cs365.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A group of {@link Actor}s, and a pool of worker threads that
 * allows them to react to messages.
 * 
 * @author David Hovemeyer
 */
public class ActorGroup {
	private Object lock = new Object();
	private List<Actor> actorList;
	private ConcurrentLinkedQueue<Message> messageQueue;
	private int numWorkers;
	private volatile boolean finished;

	// A worker thread.
	private class Worker implements Runnable {
		private int backoffTime;
		
		@Override
		public void run() {
			try {
				backoffTime = 5;
				
				while (!finished) {
					// See if there is a message waiting to be sent
					Message message = messageQueue.poll();
					
					if (message != null) {
						// Found a message!
						
						// reset backoff timer
						backoffTime = 5;
						
						// process message
						Actor recipient;
						synchronized (lock) {
							recipient = actorList.get(message.getRecipientId().getIndex());
						}
						recipient.react(message);
					} else {
						// No message: sleep for a bit
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
	
	/**
	 * Constructor.
	 * 
	 * @param numWorkers number of worker threads to create
	 */
	public ActorGroup(int numWorkers) {
		this.actorList = new ArrayList<Actor>();
		this.messageQueue = new ConcurrentLinkedQueue<Message>();
		this.numWorkers = numWorkers;
		this.finished = false;
	}
	
	/**
	 * Start a computation by adding a root {@link Actor} and specifying
	 * the contents of an initial message to be sent to that actor.
	 * Note that the message will not have a recipient.
	 * 
	 * @param rootActor            the root actor
	 * @param startMessageContent  the contents of the initial message sent to the root actor
	 */
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
	
	/**
	 * Shut down the worker threads: this can be done when the overall
	 * computation is complete.
	 */
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
