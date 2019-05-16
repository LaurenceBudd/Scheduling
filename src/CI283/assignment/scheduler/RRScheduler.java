package CI283.assignment.scheduler;
//Laurence Budd	16849174
import java.util.ArrayList;
import java.util.List;

public class RRScheduler extends Scheduler {

	private ArrayList<Process> rRobin;

	/**
	 * Create a new RRScheduler with the given quantum. The constructor needs to
	 * call the constructor of the superclass, then initialise the list of
	 * processes.
	 * 
	 * @param quantum
	 */
	public RRScheduler(long quantum) {
		super(quantum);
		rRobin = new ArrayList<>();
	}

	@Override
	public void enqueue(Process p) {
		rRobin.add(p);
	}

	@Override
	public List<Process> startScheduling() {

		ArrayList<Process> results = new ArrayList<>();

		while (!rRobin.isEmpty()) {
			for (int i = 0; i < rRobin.size(); i++) {
				Process p = rRobin.get(i);
				if (p.getState().equals(Thread.State.NEW)) {
					p.start();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					rRobin.remove(p);
					rRobin.add(0, p);
				}

				if (p.getState().equals(Thread.State.TERMINATED)) {
					rRobin.remove(p);
					results.add(p);

				} else {
					p.interrupt();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					rRobin.remove(p);
					rRobin.add(0, p);
				}
			}
		}
		return results;
	}
}
