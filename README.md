# Multilevel-Queue
This project aims to implement a MultiLevel Queue (MLQ) scheduling algorithm with two queues, Q1 and Q2, each with specific priorities and scheduling algorithms. 
The program allows users to enter process information, schedule processes execution, and generate detailed reports based on different scheduling criteria.

## Problem Statement 

The goal of this project is to develop a program that implements a MultiLevel Queue (MLQ) scheduling algorithm for managing processes in a computing system. 
The program should support two queues, Q1 and Q2, with different priorities and scheduling algorithms. 
Processes are assigned to the respective queue based on their priority, and the scheduler follows Round-Robin (RR) scheduling algorithm with a time quantum of 3 ms for Q1 and non-preemptive Shortest-Job-First (SJF) algorithm for Q2. 
The program should provide detailed information about each process, including process ID, priority, arrival time, CPU burst time, start and termination time, turnaround time, waiting time, and response time.

## Project Structure: 

This project consists of the following components:

### PCB Class:
Represents the Process Control Block (PCB) with attributes such as process ID, priority, arrival time, CPU burst, and various time metrics.

### Demo Class:
The driver class containing the main method for user interaction. It allows users to enter process information, schedule processes, and generate reports.

### Report.txt:
The program generates detailed reports of each process, including scheduling order, process information, and average turnaround time, waiting time, and response time for all processes in the system.

## Contents:

- "src/": contains the source code of the program in Java.
  
- "README.md": you are reading it right now!
