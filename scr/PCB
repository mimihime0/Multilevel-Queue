public class PCB {
private int Process_ID;//P#, where # represents the process number
private int Priority_Of_A_Process;// priorities range from 1 to 2
private int Arrival_Time;// The time at which the process enters into the ready queue.
private int CPU_burst;//The amount of time a process needs to execute.
private int Start_Time;//The start time of process execution
private int Termination_Time;//completion time
private int Turnaround_Time;//The amount of time to execute a process.
private int Waiting_Time;//The amount of time a process has been waiting in the ready queue.
private int Response_Time;//The amount of time it takes from when a request was submitted until the first respnse is produced.
private int CPU_burstremain;

public int getCPU_burstremain() {
    return CPU_burstremain;
}

public void setCPU_burstremain(int cPU_burstremain) {
    CPU_burstremain = cPU_burstremain;
}

public PCB(int ProcessID, int priority, int arrivaltime, int CPUburst){
    Process_ID=ProcessID;
    Priority_Of_A_Process=priority;
    Arrival_Time=arrivaltime;
    CPU_burst=CPUburst;
    CPU_burstremain=CPUburst;
    // the process has not startes
    this.Start_Time=-1;
    this.Termination_Time=-1;
}

public void calculateTimes() {
    //process has been started
    if (this.Start_Time != -1 && this.Termination_Time != -1) { 
        this.Turnaround_Time = this.Termination_Time - this.Arrival_Time;
        this.Waiting_Time = this.Turnaround_Time - this.CPU_burst;
        this.Response_Time = this.Start_Time - this.Arrival_Time;
    }
}

public int getProcess_ID() {
    return Process_ID;
}

public void setProcess_ID(int process_ID) {
    Process_ID = process_ID;
}

public int getPriority_Of_A_Process() {
    return Priority_Of_A_Process;
}

public void setPriority_Of_A_Process(int priority_Of_A_Process) {
    Priority_Of_A_Process = priority_Of_A_Process;
}

public int getArrival_Time() {
    return Arrival_Time;
}

public void setArrival_Time(int arrival_Time) {
    Arrival_Time = arrival_Time;
}

public int getCPU_burst() {
    return CPU_burst;
}

public void setCPU_burst(int cPU_burst) {
    CPU_burst = cPU_burst;
}

public int getStart_Time() {
    return Start_Time;
}

public void setStart_Time(int start_Time) {
    Start_Time = start_Time;
}

public int getTermination_Time() {
    return Termination_Time;
}

public void setTermination_Time(int termination_Time) {
    Termination_Time = termination_Time;
}

public int getTurnaround_Time() {
    return Turnaround_Time;
}

public void setTurnaround_Time(int turnaround_Time) {
    Turnaround_Time = turnaround_Time;
}

public int getWaiting_Time() {
    return Waiting_Time;
}

public void setWaiting_Time(int waiting_Time) {
    Waiting_Time = waiting_Time;
}

public int getResponse_Time() {
    return Response_Time;
}

public void setResponse_Time(int response_Time) {
    Response_Time = response_Time;
}

}
