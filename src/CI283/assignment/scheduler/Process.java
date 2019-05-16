package CI283.assignment.scheduler;

/**
 * A class representing a process for the CI283 Scheduling assignment. Process is a subclass of Thread.
 * When the thread runs, the 'work' that it does is to sleep repeatedly.
 *
 * @author Jim Burton <j.burton@brighton.ac.uk>
 */
public class Process extends Thread {
    /** The amount of 'work' this process has to do, in milliseconds. */
    private long work;
    /** The time at which this process was started, in milliseconds. */
    private long timeStarted;
    /** Contains the name and other details of this process. */
    private String status;

    /** An enum containing three priority values, LOW, MEDIUM and HIGH.
     *
     */
    public enum PRIORITY {
        HIGH(1), MED(5), LOW(9);

        private int val;

        PRIORITY(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    /**
     * Constructs a new Process with the given name and amount of work to do.
     * @param name
     * @param work
     */
    public Process(String name, long work) {
        this(name, work, PRIORITY.MED);
    }

    /**
     * Constructs a new Process with the given name, amount of work to do and priority.
     * @param name
     * @param work
     * @param p
     */
    public Process(String name, long work, PRIORITY p) {
        this.setName(name);
        this.work = work;
        timeStarted = System.currentTimeMillis();
        setPriority(p.getVal());
        status = String.format("[%s %d] INCOMPLETE %d", name, getPriority(), work);
    }

    /**
     * The run method sleeps repeatedly until the 'work' is done.
     */
    public void run() {
        while(workDone() < work) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) { }
        }
        setStatus();
    }

    /**
     * Sets a string describing this process and how long it took to complete.
     */
    private void setStatus() {
        status = String.format("[%s %d] COMPLETE: %d/%d",
                getName(), getPriority(), work, workDone());
    }

    /**
     * Getter for status.
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * The length of time, in milliseconds, that this process has been working.
     * @return
     */
    private long workDone() {
        return System.currentTimeMillis() - timeStarted;
    }

    /**
     * Returns the contents of `status'.
     * @return
     */
    public String toString() {
        return status;
    }

}
