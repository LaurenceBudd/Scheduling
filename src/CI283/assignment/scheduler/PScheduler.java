package CI283.assignment.scheduler;
//Laurence Budd	16849174
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PScheduler extends Scheduler {
	private PriorityQueue<Process> queue;

	public PScheduler(long quantum) {
		super(quantum);

		Comparator<Process> c = (p1, p2) -> {
			if (p1.getPriority() <= p2.getPriority()) {
				return -1;
			} else {
				return 1;
			}
		};
		queue = new PriorityQueue<>(c);
	}

	@Override
	public void enqueue(Process p) {
		queue.add(p);
	}

	@Override
	public List<Process> startScheduling() {
		ArrayList<Process> orderedResults = new ArrayList<>();

		while (!queue.isEmpty()) {
			for (int i = 0; i < queue.size(); i++) {
				Process p = queue.poll();
				if (p.getState().equals(Thread.State.NEW)) {
					p.start();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					queue.remove(p);
					queue.add(p);
				}
				if (p.getState().equals(Thread.State.TERMINATED)) {
					queue.remove(p);
					orderedResults.add(p);
				} else {
					p.interrupt();
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					queue.remove(p);
					queue.add(p);
				}
			}
		}
		return orderedResults;
	}
}
