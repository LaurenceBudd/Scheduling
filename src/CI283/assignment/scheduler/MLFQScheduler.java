package CI283.assignment.scheduler;
//Laurence Budd	16849174
/**
 * The Multi-Level Feedback Queue Scheduler. This scheduler manages two lists of processes, which are YOUNG
 * and OLD. To schedule the next process it does the following:
 * + If the the list of YOUNG processes is not empty, take the next process, allow it to run then
 *   put it at the end of the list of OLD processes (unless the state of
 *   process is TERMINATED).
 * + If the list of YOUNG processes is empty, take the next process from the list of OLD processes, allow it
 *   to run then put it at the end of the list of YOUNG processes (unless the state of
 *   process is TERMINATED). 
 *   
 *   Similar to priority queue. 2 lists, while loop and 2 if statements. when one list empty run while loop. if young list empty,
 *   everything you did in pqueue except you swap over young and old lists. so where it says remove you add it and where it says add you remove.
 *   remove from the young list and add it to the old list. Then do the same if it's terminated, remove from young list. Same for if young list is
 *   empty. Break down task in bullet points. ONe big while loop, while young list not empty or old not empty. do the following. bla bla bla else, 
 *   young list is empty. 
 */
import java.util.ArrayList;
import java.util.List;

public class MLFQScheduler extends Scheduler {

	private ArrayList<Process> young;
	private ArrayList<Process> old;

	/**
	 * Constructs a multi-level feedback queue scheduler. The constructor needs to
	 * call the constructor of the superclass then initialise the two lists for
	 * young and old processes.
	 * 
	 * @param quantum
	 */
	public MLFQScheduler(long quantum) {

		super(quantum);
		young = new ArrayList<>();
		old = new ArrayList<>();

	}

	@Override
	public void enqueue(Process p) {
		young.add(p);
	}

	/**
	 * Schedule the processes. This method needs to: + create an empty list which
	 * will hold the completed processes. This will be the return value of the
	 * method. + while one of the queues is not empty: - if the list of YOUNG
	 * processes is not empty, take the next process and get its State. - if the
	 * state is NEW, start the process then sleep for QUANTUM milliseconds then put
	 * the process at the back of the list of OLD processes. - if the state is
	 * TERMINATED, add it to the results list. - if the state is anything else then
	 * interrupt the process to wake it up then sleep for QUANTUM milliseconds, then
	 * put the process at the back of the queue.
	 *
	 * - if the list of YOUNG processes is empty, do the same except take the
	 * process from the list of OLD processes and, after it does its 'work' put it
	 * at the end of the list of YOUNG processes. + when both lists are empty,
	 * return the list of completed processes.
	 * 
	 * @return
	 */
	@Override
	public List<Process> startScheduling() {
		ArrayList<Process> results = new ArrayList<>();

		while (!young.isEmpty() || !old.isEmpty()) {

			Process p = young.get(young.size() - 1);
			Process p1 = old.get(old.size() - 1);

			if (!young.isEmpty()) {
				if (p.getState().equals(Thread.State.NEW)) {
					p.start();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					young.remove(p);
					old.add(p);
				} else if (p.getState().equals(Thread.State.TERMINATED)) {
					young.remove(p);
					results.add(p);
				} else {
					p.interrupt();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					young.remove(p);
					old.add(p);
				}
			}else if (young.isEmpty()) {
				if (p1.getState().equals(Thread.State.NEW)) {
					p1.start();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					old.remove(p1);
					young.add(p1);
				} else if (p1.getState().equals(Thread.State.TERMINATED)) {
					old.remove(p1);
					results.add(p1);
				} else {
					p1.interrupt();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					old.remove(p1);
					young.add(p1);
				}
			}
			
		}
		return results;
	}
}