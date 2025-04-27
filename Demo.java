import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Demo {

    private List<PCB> incomingProcesses;
    private Queue<PCB> readyQ1; 
    private List<PCB> readyQ2; 
    private List<PCB> completedProcesses;
    private List<String> schedulingLog; 
    private int currentTime;
    private final int TIME_QUANTUM = 3; 


    public Demo() {
        incomingProcesses = new ArrayList<>();
        readyQ1 = new LinkedList<>(); 
        readyQ2 = new ArrayList<>();
        completedProcesses = new ArrayList<>();
        schedulingLog = new ArrayList<>();
        currentTime = 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Demo scheduler = new Demo(); 

        int choice;
        do {
            System.out.println("\n--- Multi-Level Queue Scheduler Menu ---");
            System.out.println("1. Enter processes' information");
            System.out.println("2. Run Simulation and Generate Report");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        scheduler.enterProcessInfo(scanner);
                        break;
                    case 2:
                        if (scheduler.incomingProcesses.isEmpty()) {
                            System.out.println("Please enter process information first (Option 1).");
                        } else {
                            scheduler.runSimulation();
                            try {
                                scheduler.displayReport();
                            } catch (IOException e) {
                                System.err.println("Error writing report file: " + e.getMessage());
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Exiting program. Goodbye.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                choice = 0; 
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
                choice = 0;
            }

        } while (choice != 3);

        scanner.close();
    }

    public void enterProcessInfo(Scanner scanner) {
        System.out.print("Enter the total number of processes: ");
        int totalProcesses;
        try {
            totalProcesses = scanner.nextInt();
            scanner.nextLine(); 
            if (totalProcesses <= 0) {
                System.out.println("Number of processes must be positive.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); 
            return;
        }

        incomingProcesses.clear();
        readyQ1.clear();
        readyQ2.clear();
        completedProcesses.clear();
        schedulingLog.clear();
        currentTime = 0;

        System.out.println("Enter details for each process:");
        for (int i = 1; i <= totalProcesses; i++) {
            System.out.println("--- Process " + i + " ---");
            int priority, arrivalTime, burstTime;
            try {
                System.out.print("  Priority (1=Q1/RR, 2=Q2/SJF): ");
                priority = scanner.nextInt();
                System.out.print("  Arrival Time: ");
                arrivalTime = scanner.nextInt();
                System.out.print("  CPU Burst Time: ");
                burstTime = scanner.nextInt();
                scanner.nextLine(); 

                PCB process = new PCB(i, priority, arrivalTime, burstTime);
                incomingProcesses.add(process);

            } catch (InputMismatchException e) {
                System.out.println("Invalid input for process " + i + ". Please enter numbers. Skipping process.");
                scanner.nextLine(); 
                i--; 
            } catch (IllegalArgumentException e) {
                System.out.println("Input error for process " + i + ": " + e.getMessage() + " Skipping process.");
                scanner.nextLine(); 
                i--;
            }
        }

        Collections.sort(incomingProcesses);
        System.out.println("Process information entered successfully.");
    }

    public void runSimulation() {

        readyQ1.clear();
        readyQ2.clear();
        completedProcesses.clear();
        schedulingLog.clear();
        currentTime = 0;

        for (PCB p : incomingProcesses) {
             p.setRemainingBurstTime(p.getCpuBurst());

             p.setTerminationTime(-1);

        }

        int incomingIndex = 0;
        int totalProcesses = incomingProcesses.size();

        System.out.println("\n--- Running Simulation ---");

        while (completedProcesses.size() < totalProcesses) {


            while (incomingIndex < incomingProcesses.size() &&
                   incomingProcesses.get(incomingIndex).getArrivalTime() <= currentTime) {
                PCB arrivingProcess = incomingProcesses.get(incomingIndex);
                if (arrivingProcess.getPriority() == 1) {
                    readyQ1.offer(arrivingProcess); 
                    System.out.println("Time " + currentTime + ": Process P" + arrivingProcess.getProcessID() + " arrived for Q1");
                } else {
                    readyQ2.add(arrivingProcess); 
                     System.out.println("Time " + currentTime + ": Process P" + arrivingProcess.getProcessID() + " arrived for Q2");
                }
                incomingIndex++;
            }


            if (!readyQ1.isEmpty()) {
                PCB currentProcess = readyQ1.poll(); 
                currentProcess.recordStartTime(currentTime);
                schedulingLog.add("P" + currentProcess.getProcessID());
                 System.out.print("Time " + currentTime + ": Running P" + currentProcess.getProcessID() + " (Q1-RR) for ");

                int timeToRun = Math.min(TIME_QUANTUM, currentProcess.getRemainingBurstTime());
                currentTime += timeToRun;
                currentProcess.setRemainingBurstTime(currentProcess.getRemainingBurstTime() - timeToRun);
                 System.out.println(timeToRun + "ms. Remaining: " + currentProcess.getRemainingBurstTime());


                if (currentProcess.getRemainingBurstTime() <= 0) {
                    currentProcess.setTerminationTime(currentTime);
                    currentProcess.calculateFinalMetrics();
                    completedProcesses.add(currentProcess);
                    System.out.println("Time " + currentTime + ": Process P" + currentProcess.getProcessID() + " completed.");
                } else {

                     while (incomingIndex < incomingProcesses.size() &&
                            incomingProcesses.get(incomingIndex).getArrivalTime() <= currentTime) {
                         PCB arrivingProcess = incomingProcesses.get(incomingIndex);
                         if (arrivingProcess.getPriority() == 1) {
                             readyQ1.offer(arrivingProcess);
                             System.out.println("Time " + currentTime + ": Process P" + arrivingProcess.getProcessID() + " arrived for Q1 during P" + currentProcess.getProcessID() + " slice");
                         } else {
                             readyQ2.add(arrivingProcess);
                             System.out.println("Time " + currentTime + ": Process P" + arrivingProcess.getProcessID() + " arrived for Q2 during P" + currentProcess.getProcessID() + " slice");
                         }
                         incomingIndex++;
                     }
                    readyQ1.offer(currentProcess); 
                }
            }

            else if (!readyQ2.isEmpty()) {

                PCB shortestJob = null;
                int shortestBurst = Integer.MAX_VALUE;
                int shortestJobIndex = -1;

                for (int i = 0; i < readyQ2.size(); i++) {
                    PCB candidate = readyQ2.get(i);

                        if (candidate.getCpuBurst() < shortestBurst) {
                            shortestBurst = candidate.getCpuBurst();
                            shortestJob = candidate;
                            shortestJobIndex = i;
                        }

                        else if (candidate.getCpuBurst() == shortestBurst) {
                             if (shortestJob == null || candidate.compareTo(shortestJob) < 0) {
                                 shortestJob = candidate;
                                 shortestJobIndex = i;
                             }
                        }
                    }
                }


                if (shortestJob != null) {
                    readyQ2.remove(shortestJobIndex); 
                    shortestJob.recordStartTime(currentTime); 
                    schedulingLog.add("P" + shortestJob.getProcessID());
                    System.out.println("Time " + currentTime + ": Running P" + shortestJob.getProcessID() + " (Q2-SJF) for " + shortestJob.getRemainingBurstTime() + "ms.");

                    currentTime += shortestJob.getRemainingBurstTime(); 
                    shortestJob.setRemainingBurstTime(0);
                    shortestJob.setTerminationTime(currentTime);
                    shortestJob.calculateFinalMetrics();
                    completedProcesses.add(shortestJob);
                     System.out.println("Time " + currentTime + ": Process P" + shortestJob.getProcessID() + " completed.");

                } else {
                
                     if (incomingIndex < incomingProcesses.size()) {
                         int nextArrivalTime = incomingProcesses.get(incomingIndex).getArrivalTime();
                         if (nextArrivalTime > currentTime) {
                              System.out.println("Time " + currentTime + ": CPU Idle until time " + nextArrivalTime);
                              schedulingLog.add("IDLE");
                              currentTime = nextArrivalTime;
                         } else {

                         }
                     } else {

                          if (completedProcesses.size() < totalProcesses) {
                               System.err.println("Error: Deadlock or logic error - processes remain but cannot run.");
                               break;
                          }
                     }
                }
            }

            else if (incomingIndex < incomingProcesses.size()) {
                 int nextArrivalTime = incomingProcesses.get(incomingIndex).getArrivalTime();
                 if (nextArrivalTime > currentTime) {
                      System.out.println("Time " + currentTime + ": CPU Idle until time " + nextArrivalTime);
                      schedulingLog.add("IDLE");
                      currentTime = nextArrivalTime;
                 }

            }
         

        }

        System.out.println("--- Simulation Complete ---");
    }


    public void displayReport() throws IOException {
        if (completedProcesses.isEmpty()) {
            System.out.println("No processes were simulated or completed. Cannot generate report.");
            return;
        }


        Collections.sort(completedProcesses, Comparator.comparingInt(PCB::getProcessID));

        try (FileWriter writer = new FileWriter("Report.txt")) { 

            System.out.println("\n--- Simulation Report ---");

            System.out.print("Scheduling Order: ");
            writer.write("Scheduling Order: ");
            StringJoiner sjLog = new StringJoiner(" | ");
            for (String logEntry : schedulingLog) {
                sjLog.add(logEntry);
            }
            String logString = sjLog.toString();
            System.out.println(logString);
            writer.write(logString + "\n");

            System.out.println("\nProcess Details:");
            String header = String.format("%-10s | %-8s | %-12s | %-10s | %-10s | %-17s | %-15s | %-12s | %-13s",
                    "ProcessID", "Priority", "Arrival Time", "Burst Time", "Start Time", "Termination Time", "Turnaround Time", "Waiting Time", "Response Time");
            System.out.println(header);
            System.out.println(String.join("", Collections.nCopies(header.length(), "-"))); 
            writer.write(header + "\n");
            writer.write(String.join("", Collections.nCopies(header.length(), "-")) + "\n");

            double totalTurnaroundTime = 0;
            double totalWaitingTime = 0;
            double totalResponseTime = 0;

            for (PCB process : completedProcesses) {
                String line = String.format("%-10s | %-8d | %-12d | %-10d | %-10d | %-17d | %-15d | %-12d | %-13d",
                        "P" + process.getProcessID(), process.getPriority(), process.getArrivalTime(), process.getCpuBurst(),
                        process.getStartTime(), process.getTerminationTime(), process.getTurnaroundTime(),
                        process.getWaitingTime(), process.getResponseTime());
                System.out.println(line);
                writer.write(line + "\n");

                totalTurnaroundTime += process.getTurnaroundTime();
                totalWaitingTime += process.getWaitingTime();
                totalResponseTime += process.getResponseTime();
            }


            int numberOfProcesses = completedProcesses.size();
            double avgTurnaroundTime = (numberOfProcesses > 0) ? totalTurnaroundTime / numberOfProcesses : 0;
            double avgWaitingTime = (numberOfProcesses > 0) ? totalWaitingTime / numberOfProcesses : 0;
            double avgResponseTime = (numberOfProcesses > 0) ? totalResponseTime / numberOfProcesses : 0;

            System.out.println("\n--- Averages ---");
            writer.write("\n--- Averages ---\n");

            String avgTAT = String.format("Average Turnaround Time: %.2f", avgTurnaroundTime);
            System.out.println(avgTAT);
            writer.write(avgTAT + "\n");

            String avgWT = String.format("Average Waiting Time: %.2f", avgWaitingTime);
            System.out.println(avgWT);
            writer.write(avgWT + "\n");

            String avgRT = String.format("Average Response Time: %.2f", avgResponseTime);
            System.out.println(avgRT);
            writer.write(avgRT + "\n");

            System.out.println("\nReport successfully written to Report.txt");

        }
    }
}
