import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class FCFS extends SchedulingAlgorithm {

    public FCFS(ArrayList<Process> processes) 
    {
        super(processes);
    }

    @Override
    protected void sort() 
    {
        /* Lambda Expression used instead of Creating seperate class */
        this.sortedProcesses = new TreeSet<>(new Comparator<Process>() {
            @Override
            /*-ve -> less than , 0 -> equal, +ve -> greater than */
            public int compare(Process o1, Process o2) {
                int result = o1.getArrivalTime() - o2.getArrivalTime();
                /*If equal sort by name */
                if (result == 0) {
                    result = o1.getPID() - o2.getPID();
                }
                return result;
            }
        });

        for (Process p : processes) {
            // Add all processes to sortedProcesses which will sort them based on arrival time
            sortedProcesses.add(p); 
        }
    }

    @Override
    protected void schedule() 
    {
        GanttChart ganttChart = new GanttChart();
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        for (Process p : sortedProcesses) {
            ganttChart.Schedule(p);
            ganttChart.passTime(p.getBurstTime());
        }
        ganttChart.calculateWaitingTime();
        ganttChart.printGanttChart();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
    }

    @Override
    protected void print() {
        if(sortedProcesses == null)
        {
            schedule();
        }
        System.out.println();
        System.out.println(Process.getHeader());
        for (Process p : sortedProcesses) {
            System.out.println(p);
        }
        System.out.println("Average Waiting Time: " + averageWaitingTime + " ms");
        System.out.println("Average Turnaround Time: " + averageTurnAroundTime + " ms");
    }

    public ArrayList<GanttChartBar> getGanttChartBars() {
        GanttChart ganttChart = new GanttChart();
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        for (Process p : sortedProcesses) {
            ganttChart.Schedule(p);
            ganttChart.passTime(p.getBurstTime());
        }
        ganttChart.calculateWaitingTime();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
        return ganttChart.getGanttChartBars();
    }
}
