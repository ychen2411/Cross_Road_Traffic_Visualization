/**
 * The TwoWayRoad class generates an TwoWayRoad object
 * that have 3 lanes and 2 directions in the road
 *
 * @author Yanhui Chen
 *      e-mail: yanhui.chen@stonybrook.edu
 * Data member: final int FORWARD_WAY
 *              final int BACKWARD_WAY
 *              final int NUM_WAYS
 *              final int LEFT_LANE
 *              final int MIDDLE_LANE
 *              final int RIGHT_LANE
 *              final int NUM_LANES
 *              string name
 *              int greenTime
 *              int leftSingalGreenTime
 *              VehicleQueue[][] lanes
 *              LightValue lightValue
 *
 */
import java.util.ArrayList;
public class TwoWayRoad {

    public final int FORWARD_WAY = 0;
    public final int BACKWARD_WAY = 1;
    public final int NUM_WAYS = 2;
    public final int LEFT_LANE = 0;
    public final int MIDDLE_LANE = 1;
    public final int RIGHT_LANE = 2;
    public final int NUM_LANES = 3;
    private String name;
    private int greenTime;
    private int leftSignalGreenTime;
    private VehicleQueue[][] lanes;
    private LightValue lightValue;

    /**
     * it returns the string of the
     * direction a road is facing
     *
     * @param x
     *      the direction of the road
     *
     * @return
     *      a string representation of the
     *      direction the road is facing
     *
     * @precondition
     *      TwoWayRoad object is initialized
     */
    public String getDirection(int x) {
        String str = "";
        if (x == 0)
            return "FORWARD";
        else if (x == 1)
            return "BACKWARD";
        return str;
    }

    /**
     * returns VehicleQueue object or lane at the specific
     * place indicated in parameter
     *
     * @param x
     *      the direction of the lane
     *
     * @param y
     *      the lane, MID, RIGHT, OR LEFT
     *
     * @return
     *      queue at indicated lane
     *
     * @precondition
     *      the TwoWayRoad object is initialized
     */
    public VehicleQueue getDirectionAndLaneForSimulate(int x, int y) {
        return lanes[x][y];
    }

    /**
     * returns the string representation of the lane
     *
     * @param x
     *      index of the lane
     *
     * @return
     *      string representation of the lane
     *
     * @precondition
     *      TwoWayRoad object is initialized
     */
    public String getLane(int x) {
        String str = "";
        if (x == 0)
            return "LEFT lane";
        if (x == 1)
            return "MIDDLE lane";
        if (x == 2)
            return "RIGHT lane";
        return str;
    }

    /**
     * return name of the road
     *
     * @return
     *      name of the road
     *
     * @precondition
     *      TwoWayRoad object is initialized
     *
     */
    public String getName() {
        return name;
    }

    /**
     * returns the green time of the road
     *
     * @return
     *      maximum green time of the road
     *
     * @precondition
     *     TwoWayRoad object is initialized
     *
     */
    public int getGreenTime() {
        return greenTime;
    }

    /**
     * returns the lightValue of the road, RED,
     * GREEN, or LEFT_SIGNAL
     *
     * @return
     *      the lightValue
     */
    public LightValue getLightValue() {
        return lightValue;
    }

    /**
     * sets the lightValue for the road
     *
     * @param light
     *      lightValue to be set to the
     *      road
     * @precondition
     *      TwoWayRoad object is initialized
     *
     * @postcondition
     *      lightValue of the object is set
     *      to the indicated lightValue
     *
     */
    public void setLightValue(LightValue light) {
        lightValue = light;
    }

    /**
     * return an instance of the TwoWayRoad object
     * with the specified name and maximum green time
     *
     * @param initName
     *      name of the road
     *
     * @param initGreenTime
     *      maximum green time of the road
     *
     * @precondition
     *      initGreenTime is greater than 0
     *
     * @postcondition
     *      this road is initialized with all empty lanes,
     *      inidcated name, and maximum green time.
     */
    public TwoWayRoad(String initName, int initGreenTime) {
        if (initName == null || initGreenTime < 1)
            throw new IllegalArgumentException();
        lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                lanes[x][y] = new VehicleQueue();
            }
        }
        greenTime = initGreenTime;
        name = initName;
        leftSignalGreenTime = (int) (1.0 / NUM_LANES * initGreenTime);
        lightValue = LightValue.RED;
    }

    /**
     * Executes the passage of time in the simulation
     * by one turn or time step interval
     *
     * @param timerVal
     *      the current time value
     *
     * @return
     *      an array of vehicle that has been dequeued during
     *      this time step
     *
     * @precondition
     *      TwoWayRoad object is instantiated
     *
     * @throws IllegalArgumentException
     *      thrown when timerVal is less than or equal to 0
     *
     */
    public Vehicle[] proceed(int timerVal) {
        if (timerVal <= 0)
            throw new IllegalArgumentException();
        ArrayList<Vehicle> temp = new ArrayList<>();
        if (timerVal <= leftSignalGreenTime) {
            lightValue = LightValue.LEFT_SIGNAL;
            System.out.println("LEFT_SIGNAL LIGHT for " + this.name);
            if (!this.isLaneEmpty(FORWARD_WAY,LEFT_LANE)) {
                temp.add(lanes[FORWARD_WAY][LEFT_LANE].dequeue());
            }
            if (!this.isLaneEmpty(BACKWARD_WAY,LEFT_LANE)) {
                temp.add(lanes[BACKWARD_WAY][LEFT_LANE].dequeue());
            }
        } else if (timerVal > leftSignalGreenTime){
            lightValue = LightValue.GREEN;
            System.out.println("GREEN LIGHT for " + this.name);
            if (!this.isLaneEmpty(FORWARD_WAY,RIGHT_LANE)) {
                temp.add(lanes[FORWARD_WAY][RIGHT_LANE].dequeue());
            }
            if (!this.isLaneEmpty(BACKWARD_WAY,RIGHT_LANE)) {
                temp.add(lanes[BACKWARD_WAY][RIGHT_LANE].dequeue());
            }
            if (!this.isLaneEmpty(FORWARD_WAY,MIDDLE_LANE)) {
                temp.add(lanes[FORWARD_WAY][MIDDLE_LANE].dequeue());
            }
            if (!this.isLaneEmpty(BACKWARD_WAY,MIDDLE_LANE)) {
                temp.add(lanes[BACKWARD_WAY][MIDDLE_LANE].dequeue());
            }
        }
//        if (timerVal == 1 || isRoadEmpty()){
//            lightValue = LightValue.RED;
//        }

        Vehicle[] temp2 = new Vehicle[temp.size()];
        for (int i = 0 ; i < temp.size(); i++) {
            temp2[i] = temp.get(i);
        }

        return temp2;
    }

    /**
     * enqueues a vehicle into the specified lane
     *
     * @param wayIndex
     *      direction of the car going in
     * @param laneIndex
     *      lane the car arrives in
     * @param vehicle
     *      vehicle to enqueue; must not be null
     *
     * @precondition
     *      TwoWayRoad object is initialized
     *
     * @postcondition
     *      vehicle added to the specified lane
     *
     * @throws IllegalArgumentException
     *      thrown when If wayIndex > 1
     *      || wayIndex < 0 || laneIndex < 0
     *      || laneIndex > 2 or vehicle==null
     */
    public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle) {
        if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 ||
                laneIndex > 2 || vehicle == null)
            throw new IllegalArgumentException();
        lanes[wayIndex][laneIndex].enqueue(vehicle);
    }

    /**
     * return true is the specified lane is empty, otherwise
     * false
     *
     * @param wayIndex
     *      direction of the lane
     *
     * @param laneIndex
     *      index of the lane
     *
     * @return
     *      true if lane is empty, else false
     *
     * @precondition
     *      TwoWayRoad object is initialized
     *
     * @postcondition
     *      TwoWayRoad should remain unchanged
     *
     * @throws IllegalArgumentException
     *      thrown when wayIndex > 1 || wayIndex < 0
     *      || laneIndex < 0 || laneIndex > 2.
     */
    public boolean isLaneEmpty(int wayIndex, int laneIndex) {
        if ( wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex >2) {
            throw new IllegalArgumentException();
        }
        if (lanes[wayIndex][laneIndex].isEmpty())
            return true;
        else
            return false;
    }

    /**
     * return true when the road is empty,
     * otherwise return false
     *
     * @return
     *      true if lane is empty, else false
     *
     * @precondition
     *      TwoWayRoad object is initialized
     *
     */
    public boolean isRoadEmpty() {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                if (!isLaneEmpty(x,y))
                    return false;
            }
        }
        return true;
    }

    /**
     * return a toString representation of this class
     *
     * @return
     *      the road with the specified details and
     *      vehicles in the lanes if there is vehicle,
     *      otherwise empty empty road
     *
     * @precondition
     *      TwoWayRoad object is initialized
     *
     */
    public String toString() {
        String name = this.getName() + "\n";
        String fAndB = "                       FORWARD" +
                "              " +
                "BACKWARD\n";
        String str = "";
        if (getLightValue() == LightValue.RED) {
            for (int i = 0, j = NUM_LANES-1; j >= 0 && i < this.NUM_LANES; i++, j--) {
                str += String.format("%30s-%8s%7s-%2s\n", this.lanes[0][i].toString(true),
                        convertIndexToLaneRedForward(i), convertIndexToLaneRedBackward(j),
                        this.lanes[1][j].toString(false));
            }
        } else if (getLightValue() == LightValue.GREEN) {
            for (int i = 0, j = NUM_LANES-1; j >= 0 && i < this.NUM_LANES; i++, j--) {
                str += String.format("%30s-%8s%7s-%2s\n", this.lanes[0][i].toString(true),
                        convertIndexToLaneGreenForward(i), convertIndexToLaneGreenBackward(j),
                        this.lanes[1][j].toString(false));
            }
        } else if (getLightValue() == LightValue.LEFT_SIGNAL) {
            for (int i = 0, j = NUM_LANES-1; j >= 0 && i < this.NUM_LANES; i++, j--) {
                str += String.format("%30s-%8s%7s-%2s\n", this.lanes[0][i].toString(true),
                        convertIndexToLaneLeftForward(i), convertIndexToLaneLeftBackward(j),
                        this.lanes[1][j].toString(false));
            }
        }
        return (name + fAndB + str + "\n");
    }

    /**
     * return string representation of the
     * forward road in red light
     *
     * @param x
     *      the lane
     *
     * @return
     *      string representation of the
     *      forward road in red light
     */
    public String convertIndexToLaneRedForward(int x) {
        if ( x == LEFT_LANE) {
            return "[L] x";
        }
        if (x == MIDDLE_LANE) {
            return "[M] x";
        }
        if (x == RIGHT_LANE) {
            return "[R] x";
        }
        return "";
    }

    /**
     * returns the string representation of the
     * backward lane is red light
     *
     * @param x
     *      the lane
     *
     * @return
     *      string representation of the
     *      backward lane in red light
     */
    public String convertIndexToLaneRedBackward(int x) {
        if ( x == RIGHT_LANE) {
            return "x [R]";
        }
        if (x == MIDDLE_LANE) {
            return "x [M]";
        }
        if (x == LEFT_LANE) {
            return "x [L]";
        }
        return "";
    }

    /**
     * returns the string representation of
     * the forward lane in green light
     *
     * @param x
     *      the lane
     *
     * @return
     *      the string of the forward lane in green light
     */
    public String convertIndexToLaneGreenForward(int x) {
        if ( x == LEFT_LANE) {
            return "[L] x";
        }
        if (x == MIDDLE_LANE) {
            return "[M]  ";
        }
        if (x == RIGHT_LANE) {
            return "[R]  ";
        }
        return "";
    }

    /**
     * returns the string representation of backward
     * lane in green light
     *
     * @param x
     *      the lane
     *
     * @return
     *      backward lane in green light
     */
    public String convertIndexToLaneGreenBackward(int x) {
        if ( x == RIGHT_LANE) {
            return "  [R]";
        }
        if (x == MIDDLE_LANE) {
            return "  [M]";
        }
        if (x == LEFT_LANE) {
            return "x [L]";
        }
        return "";
    }

    /**
     * returns the string representation of forward
     * lane in left_signal light
     *
     * @param x
     *      the lane
     *
     * @return
     *      forward lane in left_signal light
     */
    public String convertIndexToLaneLeftForward(int x) {
        if ( x == LEFT_LANE) {
            return "[L]  ";
        }
        if (x == MIDDLE_LANE) {
            return "[M] x";
        }
        if (x == RIGHT_LANE) {
            return "[R] x";
        }
        return "";
    }

    /**
     * return a string representation of the backward
     * lane of the left_signal light
     *
     * @param x
     *      the lane
     *
     * @return
     *      the backward lane of left_signal light
     *
     */
    public String convertIndexToLaneLeftBackward(int x) {
        if ( x == RIGHT_LANE) {
            return "x [R]";
        }
        if (x == MIDDLE_LANE) {
            return "x [M]";
        }
        if (x == LEFT_LANE) {
            return "  [L]";
        }
        return "";
    }

    /**
     * return total number of vehicles
     * in the TwoWayRoad object
     *
     * @return
     *      total number of
     *      cars in the road
     */
    public int numCars() {
        int num = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                num += lanes[i][j].size();
            }
        }
        return num;
    }

}
