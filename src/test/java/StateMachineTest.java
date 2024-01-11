import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.sydlabz.java.statemachine.StateMachine;
import org.sydlabz.java.statemachine.StateMachineInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StateMachineTest {

    private final StateMachineInterface<Integer, Character> sm = new StateMachine<>();
    private final int startState;
    private final String input;
    private final boolean shouldPass;
    public StateMachineTest(Integer startState, String input, Boolean shouldPass) {
        this.startState = startState;
        this.input = input;
        this.shouldPass = shouldPass;
    }

    @Parameters(name = "{index}: input: {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{ //
                {1, "aaaab", true}, //
                {1, "ab", true}, //
                {1, "c", true}, //
                {1, "cbbbb", true}, //
                {4, "", true}, //
                {1, "", false}, //
                {1, "aba", false}, //
                {1, "acb", false}, //
                {1, "ccccb", false}, //
                {1, "ccccbbbb", false}, //
                {1, "abcd", false}, //
                {4, "a", false} //
        });
    }

    @Before
    public void init() {
        sm.addTransition(1, 'a', 2);
        sm.addTransition(2, 'a', 2);
        sm.addTransition(2, 'b', 3);
        sm.addTransition(1, 'c', 4);
        sm.addTransition(4, 'b', 4);

        sm.addAcceptingState(3);
        sm.addAcceptingState(4);
    }

    @Test
    public void test() {
        List<Character> listC = new ArrayList<>();
        for (char c : input.toCharArray()) {
            listC.add(c);
        }

        assertEquals(shouldPass, sm.accepts(startState, listC));
    }
}
