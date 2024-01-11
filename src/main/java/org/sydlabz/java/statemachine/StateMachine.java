package org.sydlabz.java.statemachine;

import java.util.*;

public class StateMachine<S, I> implements StateMachineInterface<S, I> {
    private final Map<S, Map<I, S>> stateTransitionMap = new LinkedHashMap<>();
    private final List<S> acceptingStates = new LinkedList<>();

    @Override
    public void addTransition(S startState, I input, S endState) {
        if (acceptingStates.contains(startState) && endState != startState) {
            return;
        }

        Map<I, S> transitionMap;

        if (stateTransitionMap.containsKey(startState)) {
            transitionMap = stateTransitionMap.get(startState);

            if (!transitionMap.containsKey(input)) {
                transitionMap.put(input, endState);
            }
        } else {
            transitionMap = new LinkedHashMap<>();
            transitionMap.put(input, endState);
            stateTransitionMap.put(startState, transitionMap);
        }

        if (!stateTransitionMap.containsKey(endState)) {
            stateTransitionMap.put(endState, new LinkedHashMap<>());
        }
    }

    @Override
    public void addAcceptingState(S state) {
        acceptingStates.add(state);
    }

    @Override
    public boolean accepts(S startState, List<I> input) {
        S nextState = startState;

        for (int index = 0; index < input.size(); index++) {
            I data = input.get(index);

            Map<I, S> transitionMap = stateTransitionMap.get(nextState);

            if (Objects.isNull(transitionMap) || transitionMap.isEmpty()) {
                if (index + 1 > input.size()) {
                    return acceptingStates.contains(nextState);
                } else {
                    return false;
                }
            }

            nextState = transitionMap.get(data);
        }

        return acceptingStates.contains(nextState);
    }
}
