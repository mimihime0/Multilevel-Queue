import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Demo {

    static ArrayList<PCB> Q1 = new ArrayList<>(100); // Assuming maximum size for Q1
    static ArrayList<PCB> Q2 = new ArrayList<>(100); // Assuming maximum size for Q2
    static ArrayList<PCB> schedulingOrder = new ArrayList<>(100); // Assuming maximum size for scheduling order

    static int q1Size = 0;
    static int q2Size = 0;
    static int schedulingOrderSize = 0;
    static int currentTime = 0;
    static int ttProcesses;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Scheduler scheduler = new Scheduler();

        int choice;
        do {

            System.out.println("\nMenu:");
            System.out.println("1. Enter processes' information");
            System.out.println("2. Report processes' detailed information and different scheduling criteria");
            System.out.println("3. Exit the program");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the total number of processes: ");
                    int totalProcesses = scanner.nextInt();
                    ttProcesses = totalProcesses;

                    for (int i = 1; i <= totalProcesses; i++) {
                        System.out.println("Process " + i);
                        System.out.print("Enter priority (1 or 2): ");
                        int priority = scanner.nextInt();
                        System.out.print("Enter arrival time: ");
                        int arrivalTime = scanner.nextInt();
                        System.out.print("Enter burst time: ");
                        int burstTime = scanner.nextInt();

                        PCB process = new PCB(i, priority, arrivalTime, burstTime);

                        addProcess(process);
                    }
                    break;
                case 2:
                    scheduleProcesses();
                    try {
                    displayReport();
                    } catch (IOException e) {
                        System.err.println("An error occurred while writing to the file: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Goodbye.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }

    static public void addProcess(PCB process) {
        if (process.getPriority_Of_A_Process() == 1) {
            Q1.add(process);
            q1Size++;
        } else {
            Q2.add(process);
            q2Size++;
        }
    }

    static public void scheduleProcesses() {
        int time = 0;

        while (q1Size > 0 || q2Size > 0) {

            while (!Q1.isEmpty() || !Q2.isEmpty()) {
                // Check if there's a process arriving at this time in Q1
                for (int i = 0; i < Q1.size(); i++) {
                    if (Q1.get(i).getArrival_Time() <= time) {
                        PCB process = Q1.get(i);
                        executeRRProcess(process, 3); // For Q1, time quantum is 3 ms
                        Q1.remove(i);
                        q1Size--;

                        i--; // Process removed, so decrement i
                        time = schedulingOrder.get(schedulingOrder.size() - 1).getTermination_Time();
                    }
                }

                // Execute Shortest-Job-First (SJF) for Q2
                if (Q2.size() > 0) {
                    int shortestBurstIndex = -1;
                    int shortestBurstTime = Integer.MAX_VALUE;

                    // select shortest burst procces
                    for (int i = 0; i < Q2.size(); i++) {
                        if (Q2.get(i).getArrival_Time() <= time && Q2.get(i).getCPU_burstremain() < shortestBurstTime) {
                            shortestBurstIndex = i;
                            shortestBurstTime = Q2.get(i).getCPU_burstremain();
                        }
                    }

                    if (shortestBurstIndex != -1) {
                        PCB shortestProcess = Q2.get(shortestBurstIndex);
                        executeSJFProcess(Q2.get(shortestBurstIndex), time + 1); // For Q2, use the shortest CPU burst
                                                                                 // time

                        shortestProcess.setCPU_burstremain(shortestProcess.getCPU_burstremain() - 1);
                        Q2.remove(shortestBurstIndex);
                        q2Size--;

                        if (!(shortestProcess.getCPU_burstremain() <= 0)) {
                            addProcess(shortestProcess);
                        }

                    }
                }
                time++;
            }
        }// end big while

    }

    // Execute process for Round-Robin (RR) with specified time quantum
    static private void executeRRProcess(PCB process, int timeQuantum) {
        if (process.getCPU_burst() == process.getCPU_burstremain())
            process.setStart_Time(Math.max(process.getArrival_Time(), getLastTerminationTime()));
        else
            process.setStart_Time(process.getStart_Time());

        int remainingBurstTime = process.getCPU_burstremain();

        if (remainingBurstTime <= timeQuantum) {
            process.setTermination_Time( getLastTerminationTime() + remainingBurstTime);
            schedulingOrder.add(process);
            schedulingOrderSize++;
        } else {
            process.setTermination_Time(getLastTerminationTime() + timeQuantum);
            remainingBurstTime -= timeQuantum;
            process.setCPU_burstremain(remainingBurstTime);
            schedulingOrder.add(process);
            schedulingOrderSize++;

            // Re-add the process to the queue with remaining burst time
            addProcess(process);
        }

        // Call calculateTimes() to update process times
        process.calculateTimes();

    }

    // Execute process for Shortest-Job-First (SJF)
    static private void executeSJFProcess(PCB process, int until) {
        if (process.getCPU_burst() == process.getCPU_burstremain()) {
            process.setStart_Time(getLastTerminationTime());
        } else
            process.setStart_Time(process.getStart_Time());

        process.setTermination_Time(until);
        schedulingOrder.add(process);
        schedulingOrderSize++;
        // Call calculateTimes() to update process times
        process.calculateTimes();

    }

    static public int getLastTerminationTime() {
        if (schedulingOrderSize == 0)
            return 0;
        else
            return schedulingOrder.get(schedulingOrder.size() - 1).getTermination_Time();
    }

    static public void displayReport() throws IOException {

        try {

            FileWriter writer = new FileWriter("Report.txt");
            int prevID = -1;

            System.out.print("Scheduling Order: ");
            writer.write("Scheduling Order: ");
            for (int i = 0; i < schedulingOrderSize; i++) {
                if(i > 0) {
                    if (prevID != schedulingOrder.get(i).getProcess_ID()) {
                        System.out.print("P" + schedulingOrder.get(i).getProcess_ID() + " | ");
                        writer.write("P" + schedulingOrder.get(i).getProcess_ID() + " | ");
                        prevID = schedulingOrder.get(i).getProcess_ID();
                    }
                } else {
                    System.out.print("P" + schedulingOrder.get(i).getProcess_ID() + " | ");
                    writer.write("P" + schedulingOrder.get(i).getProcess_ID() + " | ");
                }
            }

            System.out.println();
            writer.write("\n");

            double totalTurnaroundTime = 0;
            double totalWaitingTime = 0;
            double totalResponseTime = 0;

            Collections.sort(schedulingOrder, Comparator.comparingInt(PCB::getProcess_ID));
            Collections.reverse(schedulingOrder);

            System.out.println("Process ID | Priority | Arrival Time | Burst Time | Start Time | Termination Time | Turnaround Time | Waiting Time | Response Time");
            writer.write("Process ID | Priority | Arrival Time | Burst Time | Start Time | Termination Time | Turnaround Time | Waiting Time | Response Time\n");

            Set<Integer> processedIDs = new HashSet<>();

            for (int i = schedulingOrderSize - 1; i >= 0; i--) {
                PCB process = schedulingOrder.get(i);
                int processID = process.getProcess_ID();

                if (!processedIDs.contains(processID)) {
                    System.out.printf("%-10s | %-8d | %-12d | %-10d | %-10d | %-17d | %-15d | %-12d | %-13d\n",
                    "P" + processID, process.getPriority_Of_A_Process(), process.getArrival_Time(), process.getCPU_burst(),
                    process.getStart_Time(), process.getTermination_Time(), process.getTurnaround_Time(),
                    process.getWaiting_Time(), process.getResponse_Time());

                    writer.write(String.format("%-10s | %-8d | %-12d | %-10d | %-10d | %-17d | %-15d | %-12d | %-13d\n",
                    "P" + processID, process.getPriority_Of_A_Process(), process.getArrival_Time(), process.getCPU_burst(),
                    process.getStart_Time(), process.getTermination_Time(), process.getTurnaround_Time(),
                    process.getWaiting_Time(), process.getResponse_Time()));

                    totalTurnaroundTime += process.getTurnaround_Time();
                    totalWaitingTime += process.getWaiting_Time();
                    totalResponseTime += process.getResponse_Time();

                    processedIDs.add(processID);
                }
            }

            double avgTurnaroundTime = totalTurnaroundTime / schedulingOrderSize;
            double avgWaitingTime = totalWaitingTime / schedulingOrderSize;
            double avgResponseTime = totalResponseTime / schedulingOrderSize;

            System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
            writer.write("\nAverage Turnaround Time: " + avgTurnaroundTime);
            System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
            writer.write("\nAverage Waiting Time: " + avgWaitingTime);
            System.out.println("\nAverage Response Time: " + avgResponseTime);
            writer.write("\nAverage Response Time: " + avgResponseTime);
            writer.close();

        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());

        }

    }

}
