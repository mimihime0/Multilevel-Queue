import java.util.Objects;

public class PCB implements Comparable<PCB> {
    private int processID; 
    private int priority; 
    private int arrivalTime;
    private int cpuBurst; 
    private int remainingBurstTime; 

    private int startTime = -1; 
    private int terminationTime = -1; 
    private int turnaroundTime = 0;
    private int waitingTime = 0; 
    private int responseTime = 0; 

    public PCB(int processID, int priority, int arrivalTime, int cpuBurst) {
        if (priority < 1 || priority > 2) {
            throw new IllegalArgumentException("Priority must be 1 or 2.");
        }
        if (arrivalTime < 0 || cpuBurst <= 0) {
            throw new IllegalArgumentException("Arrival time must be non-negative and Burst time must be positive.");
        }
        this.processID = processID;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.remainingBurstTime = cpuBurst;
    }

    public void calculateFinalMetrics() {
        if (this.terminationTime != -1 && this.arrivalTime != -1) {
            this.turnaroundTime = this.terminationTime - this.arrivalTime;
        }
        if (this.turnaroundTime >= 0) {
           
            this.waitingTime = this.turnaroundTime - this.cpuBurst;
        }
        
         if (this.startTime != -1 && this.arrivalTime != -1) {
             this.responseTime = this.startTime - this.arrivalTime;
         }
    }


    public void recordStartTime(int time) {
        if (this.startTime == -1) {
            this.startTime = time;
        }
    }

   
    public int getProcessID() { return processID; }
    public int getPriority() { return priority; }
    public int getArrivalTime() { return arrivalTime; }
    public int getCpuBurst() { return cpuBurst; }
    public int getRemainingBurstTime() { return remainingBurstTime; }
    public int getStartTime() { return startTime; }
    public int getTerminationTime() { return terminationTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public int getWaitingTime() { return waitingTime; }
    public int getResponseTime() { return responseTime; }


    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = Math.max(0, remainingBurstTime); 
    }
    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }


    @Override
    public String toString() {
        return "PCB{" +
                "ID=" + processID +
                ", priority=" + priority +
                ", arrival=" + arrivalTime +
                ", burst=" + cpuBurst +
                ", remaining=" + remainingBurstTime +
                ", start=" + (startTime == -1 ? "N/A" : startTime) +
                ", term=" + (terminationTime == -1 ? "N/A" : terminationTime) +
                ", TAT=" + turnaroundTime +
                ", WT=" + waitingTime +
                ", RT=" + responseTime +
                '}';
    }


    @Override
    public int compareTo(PCB other) {
        int arrivalCompare = Integer.compare(this.arrivalTime, other.arrivalTime);
        if (arrivalCompare != 0) {
            return arrivalCompare;
        }

        return Integer.compare(this.processID, other.processID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PCB pcb = (PCB) o;
        return processID == pcb.processID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(processID);
    }
}
