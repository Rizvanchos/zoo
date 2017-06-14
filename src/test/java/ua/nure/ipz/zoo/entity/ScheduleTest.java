package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class ScheduleTest {

    private static final String VISIT_TIME = "Visit time";
    private static final String NO_SCHEDULE = "No schedule";
    private Schedule schedule = new Schedule();

    @Before
    public void setUp() {
        schedule.getVisitTimes().clear();
    }

    @Test
    public void shouldContainVisitTime_whenAddVisitTime() {
        schedule.addTime(VISIT_TIME);
        assertTrue(schedule.getVisitTimes().contains(VISIT_TIME));
    }

    @Test
    public void shouldReturnMessageWithNoSchedule_whenGenerateEmptySchedule() {
        assertEquals(NO_SCHEDULE, schedule.generateSchedule());
    }

    @Test
    public void shouldGenerateSchedule_whenPassVisitTime() {
        schedule.addTime(VISIT_TIME);

        String expectedVisitTime = VISIT_TIME + "\n";
        assertEquals(expectedVisitTime, schedule.generateSchedule());
    }

    @Test
    public void shouldSetNewScheduleVisitTimes_whenSetNewVisitTimes(){
        List<String> visitTimes = Collections.singletonList(VISIT_TIME);
        schedule.setVisitTimes(visitTimes);

        assertEquals(visitTimes, schedule.getVisitTimes());
    }
}
