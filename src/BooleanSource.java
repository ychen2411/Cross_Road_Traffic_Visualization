/**
 * This BooleanSource class gives the probability
 * if vehicle will arrive the lane or not.
 *
 * @author Yanhui Chen
 *      e-mail: yanhui.chen@stonybrook.edu
 *
 * Data member: double probability
 *
 */
public class BooleanSource {

    private double probability;

    /**
     * returns an instance of BooleanSource
     * object, and initializes the probability
     * indicated in parameter.
     *
     * @param initProbability
     *      probability used to construct this BooleanSource
     *      object
     *
     * @Preconditions
     *      initProbability is greater than zero and
     *      less than or equal to one
     *
     * @throws: IllegalArgumentException
     *      thrown when initProbability is not
     *      within proper range.
     */
    public BooleanSource(double initProbability) {
        if (initProbability > 1 || initProbability <= 0) {
            throw new IllegalArgumentException();
        } else
            probability = initProbability;
    }

    /**
     * returns true if Math.random generate a
     * number within the percentage, false otherwise
     *
     * @preconditions
     *      probability is between (0,1]
     *
     * @return
     *      boolean value indicating whether the car
     *      has generated or not
     *
     */
    public boolean occurs(){
        return (Math.random() < probability);
    }

    /**
     * returns the probability value
     *
     * @return
     *      the probability field
     */
    public double getProbability() {
        return probability;
    }

    /**
     * sets the probability to number
     * specified in parameter
     *
     * @param newProb
     *     the probability to be set
     *
     * @precondition
     *      BooleanSource is initiated
     *
     * @postcondition
     *      probability is set to the
     *      specified number in parameter
     */
    public void setProbability(double newProb) {
        probability = newProb;
    }

}
