import java.util.ArrayList;
import java.util.SortedSet;

public abstract class SchedulingAlgorithm 
{
    ArrayList<Process> processes;
    /* Doesnt allow duplicates, Sorted */
    SortedSet<Process> sortedProcesses; 
    double averageWaitingTime;
    double averageTurnAroundTime;
    double totalWaitingTime;
    double totalTurnAroundTime;
    int time;

    public SchedulingAlgorithm(ArrayList<Process> processes) 
    {
        this.processes = processes;
        for(Process p : processes)
        {
            p.init();
        }
        this.time = 0;  
    }

    abstract void sort(); /*Each Process has its own sorting method */
    abstract void schedule();
    abstract void print();

    public String getName(){
        return this.getClass().getName();
    }
}
