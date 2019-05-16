package CI283.assignment.test;

/**
 * Tests for the CI283 Scheduling assignment. When your code passes these tests you can be
 * fairly confident it is along the right lines, but passing the tests does not imply that
 * your code is perfect.
 *
 * @author Jim Burton <j.burton@brighton.ac.uk>
 */

import CI283.assignment.scheduler.*;
import CI283.assignment.scheduler.Process;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestSchedulers {
    @Before
    public void setUp() throws Exception {
    }
//
    @Test
    public void testRRScheduler() {
        Scheduler s = new RRScheduler(100);
        s.enqueue(new Process("P1", 5000));
        s.enqueue(new Process("P2", 3000));
        s.enqueue(new Process("P3", 1000));
        s.enqueue(new Process("P4", 4000));

        Stream<String> names = s.startScheduling().stream().map(Process::getName);
        assertEquals("[P3, P2, P4, P1]", Arrays.toString(names.toArray()));

    }

    @Test
    public void testPScheduler() {
        Scheduler s = new PScheduler(100);
        s.enqueue(new Process("P1", 2000, Process.PRIORITY.MED));
        s.enqueue(new Process("P2", 3000, Process.PRIORITY.LOW));
        s.enqueue(new Process("P3", 4000, Process.PRIORITY.MED));
        s.enqueue(new Process("P4", 4000, Process.PRIORITY.HIGH));
        s.enqueue(new Process("P5", 4000, Process.PRIORITY.LOW));
        s.enqueue(new Process("P6", 4000, Process.PRIORITY.HIGH));

        Stream<String> names = s.startScheduling().stream().map(Process::getName);
        assertEquals("[P6, P4, P3, P1, P2, P5]", Arrays.toString(names.toArray()));
    }

    @Test
    public void doMLFQScheduler() {
        Scheduler s = new MLFQScheduler(100);
        s.enqueue(new Process("P1", 2000));
        s.enqueue(new Process("P2", 3000));
        s.enqueue(new Process("P3", 4000));
        s.enqueue(new Process("P4", 4000));
        s.enqueue(new Process("P5", 4000));
        s.enqueue(new Process("P6", 4000));

        Stream<String> names = s.startScheduling().stream().map(Process::getName);
        assertEquals("[P1, P2, P4, P5, P6, P3]", Arrays.toString(names.toArray()));
    }
}
