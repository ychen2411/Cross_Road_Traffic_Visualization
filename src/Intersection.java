/**
 * This Intersection class creates an Intersection object
 * with multiple TwoWayRoad objects, lightIndex for the road
 * with the light, countdownTimer showing the green time
 * and shows the intersection at each time period.
 *
 * @author Yanhui Chen
 *      e-mail: yanhui.chen@stonybrook.edu
 *
 * Data members:
 *          TwoWayRoads[] roads
 *          int lightIndex
 *          int countdownTimer
 *
 */
import java.util.ArrayList;
public class Intersection {
    private TwoWayRoad[] roads;
    private int lightIndex = 0;
    private int countdownTimer;

    /**
     * returns the number of roads in
     * the intersection object
     *
     * @return
     *      num of roads
     *
     * @precondition
     *      Intersection object is initialized
     */
    public int getNumRoads() {
        return roads.length;
    }

    /**
     * return the lightIndex
     *
     * @return
     *      index of the road where
     *      the light is active
     *
     * @precondtion
     *      Intersection object is initialized
     *
     */
    public int getLightIndex() {
        return lightIndex;
    }

    /**
     * returns an array of TwoWayRoad
     *
     * @return
     *      the roads
     *
     * @precondition
     *      Intersection object is initialized
     */
    public TwoWayRoad[] getRoads(){
        return roads;
    }

    /**
     * returns the countdownTimer
     *
     * @return
     *      maximum green time aka
     *      countdownTimer
     *
     * @precondition
     *      Intersection object is initialized
     */
    public int getCountdownTimer(){
        return countdownTimer;
    }

    /**
     * return the lightValue
     *
     * @return
     *      lightValue of the light
     *      active road.
     *
     * @precondition
     *      Intersection object is initialized
     */
    public LightValue getCurrentLightValue() {
        return roads[lightIndex].getLightValue();
    }

    /**
     * return a instance with indicated
     * road array
     *
     * @param initRoads
     *      array of road
     *
     * @precondition
     *      initRoad is not null, length of
     *      initRoads is less than or equal to
     *      MAX_ROADS, all indices of initRoads
     *      are not null
     *
     * @postcondition
     *      Intersection object initialized with
     *      specified roads array
     *
     * @throws IllegalArgumentException
     *      thrown when precondition is violated
     *
     */
    public Intersection(TwoWayRoad[] initRoads) {
        if (initRoads == null || initRoads.length > 4)
            throw new IllegalArgumentException();
        for (int i = 0; i < initRoads.length; i++) {
            if (initRoads[i] == null)
                throw new IllegalArgumentException();
        }
        roads = new TwoWayRoad[initRoads.length];

        for (int i = 0; i < initRoads.length; i++) {
            roads[i] = initRoads[i];
        }
        roads[lightIndex].setLightValue(LightValue.GREEN);
        System.out.println("INTESECTION: " + roads[lightIndex].getLightValue().toString());
        countdownTimer = roads[lightIndex].getGreenTime();

    }

    /**
     * performs a single iteration through
     * the intersection
     *
     * @return
     *      array of Vehicle that's dequeued in
     *      the iteration
     *
     * @postcondition
     *      the intersection has dequeued all lanes
     *      with a green light (if non-empty) and returns
     *      an array containing the Vehicles
     *
     */
    public Vehicle[] timeStep() {
        Vehicle[] temp;
        temp = roads[lightIndex].proceed(countdownTimer);
        if(roads[lightIndex].isRoadEmpty()){
            countdownTimer = 0;
        }
        countdownTimer--;
        if (countdownTimer < 1 && lightIndex < roads.length-1) {
            this.display();
            roads[lightIndex].setLightValue(LightValue.RED);
            this.lightIndex++;
            //if(lightIndex == roads.length) lightIndex = 0;
            countdownTimer = roads[lightIndex].getGreenTime();
            roads[lightIndex].setLightValue(LightValue.GREEN);
            return temp;
        }
        else if (countdownTimer < 1 && lightIndex == roads.length-1) {
            this.display();
            roads[lightIndex].setLightValue(LightValue.RED);
            lightIndex = 0;
            countdownTimer = roads[lightIndex].getGreenTime();
            roads[lightIndex].setLightValue(LightValue.GREEN);
            return temp;
        }
        this.display();
        return temp;
    }

    /**
     * Enqueues a vehicle onto the lane in the
     * intersection
     *
     * @param roadIndex
     *      index of roads
     * @param wayIndex
     *      index of direction
     * @param laneIndex
     *      index of lane
     * @param vehicle
     *      vehicle to be enqueued
     *
     * @precondition
     *      0 ≤ roadIndex < roads.length.
     *      0 ≤ wayIndex < TwoWayRoad.NUM_WAYS.
     *      0 ≤ laneIndex < TwoWayRoad.NUM_LANES.
     *      vehicle != null.
     *
     * @throws IllegalArgumentException
     *      thrown when precondition is violated
     */
    public void enqueueVehicle(int roadIndex, int wayIndex,int laneIndex,
                               Vehicle vehicle) {
        if (vehicle == null || roadIndex < 0 || roadIndex >= roads.length ||
                wayIndex < 0 || wayIndex >= roads[roadIndex].NUM_WAYS ||
                laneIndex < 0 || laneIndex >= roads[roadIndex].NUM_LANES)
            throw new IllegalArgumentException();

        roads[roadIndex].enqueueVehicle(wayIndex,laneIndex,vehicle);
    }

    /**
     * returns a string representation of
     * the intersection during different
     * time steps.
     *
     * @precondtion
     *      Intersection object is
     *      initialized
     */
    public void display() {

        for (int i = 0; i <roads.length; i++) {
            System.out.print(roads[i].toString());
        }

    }

    /**
     * return true if the intersection
     * if empty, otherwise false
     *
     * @return
     *      true when intersection is empty,
     *      otherwise false.
     *
     * @precondition
     *      Intersection object is initialized
     *
     */
    public boolean isIntersectionEmpty() {
        for(int i = 0; i < roads.length; i++) {
            if(!roads[i].isRoadEmpty())
                return false;
        }
        return true;
    }

    /**
     * return total cars in the intersection
     *
     * @return
     *      total number of cars in the intersection
     *
     * @precondition
     *      Intersection object is initialized
     */
    public int totalCarsWaiting(){
        int total = 0;
        for(int i = 0; i < roads.length; i++){
            total += roads[i].numCars();
        }
        return total;
    }
}
