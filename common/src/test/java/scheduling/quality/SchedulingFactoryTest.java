package scheduling.quality;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import scheduling.model.Employee;
import scheduling.model.Problem;
import scheduling.model.SchedulingFactory;
import scheduling.model.WeekdayShift;
import scheduling.model.WeekendShift;
import scheduling.model.WeeklyScheduling;
import scheduling.quality.SchedulingEvaluator;
import static org.junit.Assert.*;
import static java.lang.String.*;
public class SchedulingFactoryTest {
    
    @Test
    public void test() {
        Problem problem = Problem.getDefault();
        List<WeeklyScheduling> actual = new SchedulingFactory(problem, new SchedulingEvaluator(problem)).randomScheduling();
        assertEquals(4, actual.size());
        actual.stream().forEach(this::assertSchedulingIsProperlyFilled);
        System.out.println(actual.get(0));
    }
    
    private void assertSchedulingIsProperlyFilled(WeeklyScheduling weeklySched) {
        Set<Employee> usedEmployees = new HashSet<>();
        for (WeekdayShift shift: weeklySched.weekdaySched().keySet()) {
            for (Employee e: weeklySched.weekdaySched().get(shift)) {
                if (e == null) {
                    throw new AssertionError(format("null employee in shift [%s]", shift));
                }
                if (usedEmployees.contains(e)) {
                    throw new AssertionError(format("employee [%s] is used twice", e.toString()));
                }
            }
        }
        usedEmployees = new HashSet<>();
        for (WeekendShift shift: weeklySched.weekendSched().keySet()) {
            for (Employee e: weeklySched.weekendSched().get(shift)) {
                if (e == null) {
                    throw new AssertionError(format("null employee in shift [%s]", shift));
                }
                if (usedEmployees.contains(e)) {
                    throw new AssertionError(format("employee [%s] is used twice", e.toString()));
                }
            }
        }
    }

}
