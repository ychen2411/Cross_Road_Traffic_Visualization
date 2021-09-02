/**
 * This VehicleQueue class generated an queue object
 * of vehicle that enqueue from tail and dequeue
 * from head
 *
 * @author Yanhui Chen
 *      e-mail: yanhui.chen@stonybrook.edu
 *
 */
import java.util.ArrayDeque;
public class VehicleQueue extends ArrayDeque<Vehicle>{
    /**
     * returns an instance of VehicleQueue object
     */
    public VehicleQueue(){

    }

    /**
     * adds an vehicle to the tail of the queue
     *
     * @param v
     *      the vehicle to be added
     *
     * @precondition
     *      VehicleQueue object is initialized
     *
     * @postcondition
     *      vehicle v is added to the tail of the queue
     */
    public void enqueue(Vehicle v) {
        this.add(v);
    }

    /**
     * removes an vehicle from the head of the queue
     *
     * @return
     *      the vehicle being removed
     *
     * @precondition
     *      VehicleQueue object is initialized
     *
     * @postcondition
     *      vehicle at the head of the queue is
     *      removed
     *
     */
    public Vehicle dequeue() {
        return this.poll();
    }

    /**
     * generates a toString representation of
     * the vehicles in the queue
     *
     * @param isForward
     *      if the lane is a FORWARD lane
     *
     * @return
     *      a toString representation of the vehicles
     *      in the queue
     *
     * @precondition
     *      VehicleQueue object is generated
     *
     * @postcondition
     *      the toString representation is generated
     */
    public String toString(boolean isForward) {
        if (isForward == true) { //Forward
            String carIDs = "";
            for (Vehicle v : this) {
                carIDs = "[" + v.getSerialID() + "]" + carIDs;
            }
            return carIDs;
        }
        else { //Backward
            String carIDs = "";
            for (Vehicle v : this) {
                carIDs = carIDs + "[" + v.getSerialID() + "]";
            }
            return carIDs;
        }
    }
}
