/**
 * This IntersectionSimulator class tests the Intersection class,
 * TwoWayRoad class, VehicleQueue class, Vehicle class,
 * BooleanSource class, and LightValue enum.
 *
 * @author Yanhui Chen
 *      e-mail: yanhui.chen@stonybrook.edu
 *
 */
import java.util.Scanner;
public class IntersectionSimulator {
    public static void main(String[] args) {
        System.out.print("Welcome to IntersectionSimulator 2021\n\n");
        Scanner in = new Scanner(System.in);
        System.out.print("Input the simulation time: ");
        int simTime = in.nextInt();
        System.out.print("Input the arrival probability: ");
        double prob = in.nextDouble();
        System.out.print("Input the number of Streets: ");
        int numStreets = in.nextInt();

        in.nextLine();
        String[] name = new String[numStreets];
        for (int i = 0; i < numStreets; i++) {
            System.out.print("\nInput Street " + (i+1) + " name:");
            name[i] = in.nextLine();
            for (int x = 0; x < name.length; x++) {
                if (name[i].equalsIgnoreCase(name[x]) && i != x) {
                    System.out.print("\nDuplicate Detected.\n");
                    i--;
                    break;
                } else
                    continue;
            }
        }
        int[] maxGreenTimes = new int[numStreets];
        for (int i = 0; i < numStreets; i++) {
            System.out.print("Input max green time for " + name[i] +": ");
            maxGreenTimes[i] = in.nextInt();
        }


        System.out.print("\nStart Simulation...\n\n");

        simulate(simTime, prob, name, maxGreenTimes);
    }

    /**
     * This method tests the classes made by showing the
     * intersection and statistics
     *
     * @param simulationTime
     *      number of terms that car can be added
     *
     * @param arrivalProbability
     *      probability car might be arrived
     *
     * @param roadNames
     *      array of the roads' names
     *
     * @param maxGreenTimes
     *      array of the roads' maximum
     *      green times
     */
    public static void simulate(int simulationTime, double arrivalProbability,
                                String[] roadNames, int[] maxGreenTimes) {

        BooleanSource isCarArrive = new BooleanSource(arrivalProbability);
        TwoWayRoad[] roads = new TwoWayRoad[roadNames.length];
        for (int i = 0; i < roads.length; i++) {
            roads[i] = new TwoWayRoad(roadNames[i],maxGreenTimes[i]);
        }
        Intersection intersection = new Intersection(roads);
        int time = 1;
        int totalWaitTime = 0;
        int totalCarsPassed = 0;
        int totalCars = 0;
        double averageWaitTime = 0;
        int longestWaitTime = 0;
        do {
            System.out.print("####################################################" +
                    "############################\n");
            System.out.print("Time Step: " + time + "\n\n");

            int numCarEnqueued = 0;
            System.out.print("ARRIVING CARS: \n");
            if (simulationTime > 0) {
                for (int x = 0; x < roads.length; x++) {
                    for (int y = 0; y < 2; y++) {
                        for (int z = 0; z < 3; z++) {
                            if (isCarArrive.occurs()) {
                                Vehicle addedCar = new Vehicle(time);
                                intersection.enqueueVehicle(x, y, z, addedCar);
                                numCarEnqueued++;
                                totalCars++;
                                System.out.print("Car[" + addedCar.getSerialID() + "] " +
                                        "entered " + intersection.getRoads()[x].getName()
                                        + ", going " + intersection.getRoads()[x].getDirection(y) +
                                        " in " + intersection.getRoads()[x].getLane(z) + "\n");
                                if (numCarEnqueued == 6)
                                    break;
                            }
                        }
                        if (numCarEnqueued == 6)
                            break;
                    }
                }
            }
            System.out.print("Timer: " + (intersection.getCountdownTimer()) + "\n\n");
            simulationTime--;
            Vehicle[] passedCars = intersection.timeStep();
            System.out.print("\nPASSING CARS:\n");

            int waitTime = 0;


            for (int i = 0; i < passedCars.length; i++) {
                if (passedCars[i] == null)
                    break;
                else {
                    waitTime = (time - passedCars[i].getTimeArrived());
                    if (waitTime > longestWaitTime)
                        longestWaitTime = waitTime;
                    totalWaitTime = totalWaitTime + waitTime;
                    totalCarsPassed++;
                    System.out.print("Car[" + passedCars[i].getSerialID() + "] " +
                            "passes through. Wait time of " +
                            waitTime + ".\n");
                }
            }
            int totalCarsWaiting = intersection.totalCarsWaiting();

            System.out.print("\nSTATISTICS: ");
            System.out.print("\nCars currently waiting: " + totalCarsWaiting + " cars");
            System.out.print("\nTotal cars passed: " + totalCarsPassed + " cars");
            System.out.print("\nTotal wait time: " + totalWaitTime + " turns");
            averageWaitTime =  ((double) totalWaitTime / (double) totalCarsPassed);
            System.out.print("\nAverage wait time: " + averageWaitTime + " turns.\n\n");
            time++;

        } while (!intersection.isIntersectionEmpty());

        System.out.print("################################################################################\n" +
                "################################################################################\n" +
                "################################################################################\n\n");
        System.out.print("SIMULATION SUMMARY:\n\n");
        System.out.print("Total Time: " + (time-1) + " steps\n");
        System.out.print("Total Vehicles: " + totalCars + " cars\n");
        System.out.print("Longest wait time: " + longestWaitTime + " turns\n");
        System.out.print("Total wait time: " + totalWaitTime+ " turns\n");
        System.out.print("Average wait time: " + averageWaitTime+ " turns\n");

    }
}
