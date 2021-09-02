/**
 * The Vehicle class creates an Vehicle object
 * that can be put to drive through or stop at
 * the lanes
 *
 * @author Yanhui Chen
 *      e-mail: yanhui.chen@stonybrook.edu
 *
 * Data member: static int serialCounter
 *              int serialID
 *              int timeArrived
 *
 */
public class Vehicle {

    private static int serialCounter = 0;
    private int serialID;
    private int timeArrived;

    /**
     * returns an instance of Vehicle object
     * with specified arrived time
     *
     * @param initTimeArrived
     *      time the vehicle arrive at the intersection
     *
     * @preconditions
     *      initTimeArrived is greater than 1
     *
     * @throws IllegalArgumentException
     *      thrown is initTimeArrived is not within
     *      proper range
     *
     */
    public Vehicle(int initTimeArrived){
        if (initTimeArrived < 1) {
            throw new IllegalArgumentException();
        } else {
            serialCounter++;
            serialID = serialCounter;
            timeArrived = initTimeArrived;
        }
    }

    /**
     * it returns the serialID for the
     * individual vehicle
     *
     * @precondition
     *      Vehicle object is initialized
     *
     * @return
     *      the serialID of generated vehicle
     */
    public int getSerialID() {
        return serialID;
    }

    /**
     * returns the timeArrived of
     * the specific vehicle
     *
     * @precondtion
     *      Vehicle object is initialized
     * @return
     *      the time the vehicle arrived
     */
    public int getTimeArrived() {
        return timeArrived;
    }
}
