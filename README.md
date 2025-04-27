# Multilevel Queue Scheduler

## Description

This project implements a Multilevel Queue (MLQ) scheduling algorithm in Java. It simulates the execution of processes based on a two-level queue structure (Q1 and Q2) with different priorities and scheduling policies. The goal is to manage processes efficiently according to their assigned priority and CPU burst requirements.

The system uses two queues:
* **Q1 (High Priority):** Uses the Round Robin (RR) scheduling algorithm with a fixed time quantum (TQ = 3ms).
* **Q2 (Low Priority):** Uses the non-preemptive Shortest Job First (SJF) scheduling algorithm.

Q1 has higher priority than Q2, meaning processes in Q1 will always run before processes in Q2 if any are ready. The program takes process information (priority, arrival time, burst time) as input, runs the simulation, and generates a detailed report (`Report.txt`) containing the scheduling order and performance metrics for each process.

## Features

* Implements a two-level MLQ scheduler (Q1: RR, Q2: non-preemptive SJF).
* Strict priority handling: Q1 executes before Q2.
* Round Robin scheduling for Q1 with a time quantum of 3ms.
* Non-preemptive Shortest Job First scheduling for Q2 (based on original burst time).
* Accepts user input for process details (priority, arrival time, burst time).
* Simulates process execution over time, handling arrivals and context switches (implicitly).
* Calculates key performance metrics for each process:
    * Start Time
    * Termination Time
    * Turnaround Time
    * Waiting Time
    * Response Time
* Generates a `Report.txt` file containing:
    * The sequence of process execution (Scheduling Order).
    * A detailed table summarizing metrics for each completed process.
    * Average Turnaround Time, Waiting Time, and Response Time for all processes.
* Handles CPU idle time when no processes are ready to run.

## Requirements

* Java Development Kit (JDK) 8 or higher (for compiling and running).
* No external libraries are required.

## Input

The program takes input interactively via the command line:
1.  The total number of processes to simulate.
2.  For each process:
    * Priority (1 for Q1/RR, 2 for Q2/SJF)
    * Arrival Time (integer, non-negative)
    * CPU Burst Time (integer, positive)

## Usage

1.  **Save Files:** Ensure the following Java source files are in the same directory:
    * `Demo.java`
    * `PCB.java`
2.  **Compile:** Open a terminal or command prompt, navigate to the directory, and compile the source files:
    ```bash
    javac PCB.java Demo.java
    ```
    *(Or use `javac *.java`)*
3.  **Run:** Execute the main class:
    ```bash
    java Demo
    ```
    *(If you encounter `ClassNotFoundException`, try explicitly setting the classpath: `java -cp . Demo`)*
4.  **Interact:**
    * Choose option `1` to enter the process information as prompted.
    * Choose option `2` to run the simulation based on the entered data and generate `Report.txt`.
    * Choose option `3` to exit.

## Output Interpretation (`Report.txt`)

The generated `Report.txt` file contains:

1.  **Scheduling Order:** A sequence showing which process ran at each step (e.g., `P1 | P2 | P1 | P3 | IDLE | P4...`). Note that for RR, a process ID might appear multiple times if it runs in multiple time slices. `IDLE` indicates the CPU was idle.
2.  **Process Details Table:** A table summarizing the final metrics for each unique process after it has completed execution.
    * `ProcessID`: The process identifier.
    * `Priority`: The queue the process belonged to (1 or 2).
    * `Arrival Time`: When the process became available.
    * `Burst Time`: The total CPU time required by the process.
    * `Start Time`: The time the process *first* started executing.
    * `Termination Time`: The time the process finished execution.
    * `Turnaround Time`: `Termination Time - Arrival Time`.
    * `Waiting Time`: `Turnaround Time - Burst Time`.
    * `Response Time`: `Start Time - Arrival Time`.
3.  **Averages:** The average Turnaround Time, Waiting Time, and Response Time calculated across all completed processes.

## Code Structure

* **`Demo.java`:** The main driver class. Contains the `main` method for user interaction, methods for entering process info, the core `runSimulation()` method implementing the MLQ logic, and the `displayReport()` method for output generation. Manages the simulation state (queues, time, logs).
* **`PCB.java`:** Represents the Process Control Block. Stores all attributes and calculated time metrics for a single process. Implements `Comparable` for sorting (primarily by arrival time).

## Notes & Limitations

* **Non-Preemptive SJF:** Q2 uses non-preemptive SJF based on the *original* burst time. Once a Q2 process starts, it runs to completion unless a *higher priority* Q1 process arrives.
* **Context Switching:** The simulation assumes zero context switching overhead for simplicity.
* **Input Validation:** Basic input validation is included, but robust error handling for all edge cases might require further enhancement.

