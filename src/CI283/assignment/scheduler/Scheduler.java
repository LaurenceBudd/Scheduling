package CI283.assignment.scheduler;
/**
 * The abstract superclass for all Schedulers. A Scheduler accepts a series of Processes
 * and allows them to run to completion according to its specific strategy (e.g.
 * Round Robin, High/Low Priority or Multi-level Feedack Queue).
 *
 * @author Jim Burton <j.burton@brighton.ac.uk>
 */

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Scheduler {
    /** The time quantum for which each process will run before being
     * put back to sleep.
     */
    protected static long QUANTUM;
    /** A logger for printing debug information and so on.
     *
     */
    protected static Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * Creates a Scheduler with the given time quantum.
     * @param quantum
     */

    public Scheduler(long quantum) {
        QUANTUM = quantum;
        //Create a logger that will publish to the console
        LOGGER.setLevel(Level.FINER);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);//or Level.FINER to publish all messages
        LOGGER.addHandler(handler);
    }

    /**
     * Add a process to be scheduled.
     * @param p
     */
    public abstract void enqueue(Process p);

    /**
     * Start scheduling the processes.
     * @return
     */
    public abstract List<Process> startScheduling();
}
