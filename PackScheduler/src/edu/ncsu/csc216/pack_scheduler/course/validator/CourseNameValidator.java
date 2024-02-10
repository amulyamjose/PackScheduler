package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Checks if a course name is valid
 * 
 * @author William Walton
 * @author Jacob Phillips
 */
public class CourseNameValidator {
    /** Count of letters at the start of the name */
    private int letterCount;
    /** Count of digits in the middle of the name */
    private int digitCount;
    /** Where the FSM's current state is */
    private State currentState;
    /** the initial state of the FSM */
    private final State stateInitial = new InitialState();
    /** the letter state of the FSM */
    private final State stateLetter = new LetterState();
    /** the digit state of the FSM */
    private final State stateNumber = new NumberState();
    /** the suffix state of the FSM */
    private final State stateSuffix = new SuffixState();

    /**
     * Determines if a String is a valid Course name or not.
     * 
     * @param courseNameToTest String to test
     * @return true if valid, false if it does not end on a valid end state
     * @throws InvalidTransitionException
     *                                    if the path along the FSM for the name is
     *                                    not valid
     */
    public boolean isValid(String courseNameToTest) throws InvalidTransitionException {
        letterCount = 0;
        digitCount = 0;
        currentState = stateInitial;

        for (char c : courseNameToTest.toCharArray()) {

            if (Character.isLetter(c)) {
                currentState.onLetter();
            } else if (Character.isDigit(c)) {
                currentState.onDigit();
            } else {
                currentState.onOther();
            }

        }
        return currentState == stateNumber && digitCount == NumberState.REQUIRED_DIGITS || currentState == stateSuffix;
    }

    /**
     * Roadmap for how to build each part of the state
     */
    private abstract class State {

        /**
         * Determines what to do on a per-state basis if there is a letter [a-z] next
         * 
         * @throws InvalidTransitionException if the next character makes the name
         *                                    invalid
         */
        public abstract void onLetter() throws InvalidTransitionException;

        /**
         * Determines what to do on a per-state basis if there is a digit [0-9] next
         * 
         * @throws InvalidTransitionException if the next character makes the name
         *                                    invalid
         */
        public abstract void onDigit() throws InvalidTransitionException;

        /**
         * Determines what to do on a per-state basis if there is a non-letter or digit
         * character next. throws every time.
         * 
         * @throws InvalidTransitionException "Course name can only contain letters and
         *                                    digits."
         *                                    because only letters and numbers are
         *                                    allowed in course names, other characters
         *                                    are not allowed
         */
        public void onOther() throws InvalidTransitionException {

            throw new InvalidTransitionException("Course name can only contain letters and digits.");
        }

    }

    /**
     * Holds logic for the first part of determination
     */
    private class InitialState extends State {

        /**
         * constructor for InitialState
         * should not be able to be called outside
         */
        private InitialState() {
        }

        /**
         * On a letter, since we need 1-4 more for the first part of the name, increase
         * letter count and record our state as Lettering state.
         */
        @Override
        public void onLetter() {
            currentState = stateLetter;
            letterCount++;
        }

        /**
         * Digits are not allowed next, so throw an error to show that this is not a
         * valid name
         * 
         * @throws InvalidTransitionException "Course name must start with a letter."
         */
        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name must start with a letter.");
        }

    }

    /**
     * Holds logic for the letter part of determination
     */
    private class LetterState extends State {
        /**
         * Max # of letters that can be in a LetterState
         */
        private final static int MAX_LETTERS = 4;

        /**
         * constructor for LetterState
         * should not be able to be called outside
         */
        private LetterState() {
        }

        /**
         * What to do if there's a letter next and you're already in the lettering
         * phase.
         * If there's already 4 letters, then a 5th will be invalid & error will be
         * thrown.
         * Else, keep going with the letters until there's 4.
         * 
         * @throws InvalidTransitionException "Course name cannot start with more than 4
         *                                    letters."
         *                                    if letterCount >= MAX_LETTERS (4)
         */
        @Override
        public void onLetter() throws InvalidTransitionException {
            if (letterCount >= MAX_LETTERS) {
                throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
            }
            letterCount++;
        }

        /**
         * What to do if you're moving onto the Digit phase from the Lettering phase;
         * Sets the current state to the Digit state and increases the digit count.
         */
        @Override
        public void onDigit() {
            currentState = stateNumber;
            digitCount++;
        }

    }

    /**
     * Holds logic for what to do if you're in the Digit state of the FSM
     */
    private class NumberState extends State {
        /** # of digits that the name HAS to have */
        private final static int REQUIRED_DIGITS = 3;

        /**
         * constructor for NumberState
         * should not be able to be called outside
         */
        private NumberState() {
        }

        /**
         * If there's a letter next, assume it's the suffix. If there's not already 3
         * digits, then that's an invalid name, so throw. Else, move onto Suffix state.
         * 
         * @throws InvalidTransitionException "Course name must have 3 digits."
         *                                    if digit count != Required_DIGITS
         */
        @Override
        public void onLetter() throws InvalidTransitionException {
            if (digitCount != REQUIRED_DIGITS) {
                throw new InvalidTransitionException("Course name must have 3 digits.");

            }
            currentState = stateSuffix;
        }

        /**
         * If there's a digits next, assume you're continuing the Digit phase. If
         * there's already 3 digits, then a 4th is invalid, so throw an error.
         * Otherwise
         * increase the digit count and continue on.
         * 
         * @throws InvalidTransitionException "Course name can only have 3 digits."
         *                                    if digitCount >= REQUIRED_DIGITS
         */
        @Override
        public void onDigit() throws InvalidTransitionException {
            if (digitCount >= REQUIRED_DIGITS) {
                throw new InvalidTransitionException("Course name can only have 3 digits.");
            }
            digitCount++;
        }

    }

    /**
     * Holds logic for the Suffix state of the FSM.
     */
    private class SuffixState extends State {
        /**
         * constructor for SuffixState
         * should not be able to be called outside
         */
        private SuffixState() {
        }

        /**
         * There should ne no more letters in the name, so if there is, throw.
         * 
         * @throws InvalidTransitionException "Course name can only have a 1 letter
         *                                    suffix."
         */
        @Override
        public void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
        }

        /**
         * There should be no more digits in the name, so if there is, throw.
         * 
         * @throws InvalidTransitionException "Course name cannot contain digits after
         *                                    the suffix."
         */
        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");

        }

    }
}
