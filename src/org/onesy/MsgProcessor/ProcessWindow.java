package org.onesy.MsgProcessor;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ProcessWindow {
	
	public static ConcurrentLinkedQueue<InProcessFrame> InProcessFrameWindowCache = new ConcurrentLinkedQueue<InProcessFrame>();
	
	
}
