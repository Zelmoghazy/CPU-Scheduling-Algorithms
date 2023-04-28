import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class Priority extends SchedulingAlgorithm {
    private GanttChart ganttChart;
    ArrayList<Process> waitingProcesses = new ArrayList<Process>(processes);

    public Priority(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    void sort() {
        if (sortedProcesses == null) 
        {
            sortedProcesses = new TreeSet<>(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    int result = o1.getPriority() - o2.getPriority();
                    if (result == 0) {
                        result = o1.getArrivalTime() - o2.getArrivalTime();
                    }
                    if (result == 0) {
                        result = o1.getPID() - o2.getPID();
                    }
                    return result;
                }
            });
        }
        else
        {
            sortedProcesses.clear();
        }
        for (Process p : processes) 
        {
            if(p.getArrivalTime() <= time && !ganttChart.isStarted(p))
            {
                sortedProcesses.add(p);
            }
        }
       
    }

    @Override
    void schedule() 
    {
        ganttChart = new GanttChart(processes);
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        do{
            if(sortedProcesses.isEmpty())
            {
                time++;
                sort();
                continue;
            }
            ganttChart.Schedule(sortedProcesses.first());
            ganttChart.passTime(sortedProcesses.first().getBurstTime());
            time = ganttChart.getTime();
            waitingProcesses.remove(sortedProcesses.first());
            sort();
        }while(!sortedProcesses.isEmpty() || (!waitingProcesses.isEmpty()));
        ganttChart.calculateWaitingTime();
        ganttChart.printGanttChart();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
    }

    @Override
    void print() {
        if(sortedProcesses == null)
        {
            schedule();
        }
        System.out.println();
        System.out.println(Process.getHeader());
        for (Process p : processes) 
        {
            System.out.println(p);
        }
        System.out.println("Average Waiting Time: " + averageWaitingTime + " ms");
        System.out.println("Average Turnaround Time: " + averageTurnAroundTime + " ms");
    }
    
    public ArrayList<GanttChartBar> getGanttChartBars() {
        ganttChart = new GanttChart(processes);
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        do{
            if(sortedProcesses.isEmpty())
            {
                time++;
                sort();
                continue;
            }
            ganttChart.Schedule(sortedProcesses.first());
            ganttChart.passTime(sortedProcesses.first().getBurstTime());
            time = ganttChart.getTime();
            waitingProcesses.remove(sortedProcesses.first());
            sort();
        }while(!sortedProcesses.isEmpty() || (!waitingProcesses.isEmpty()));
        ganttChart.calculateWaitingTime();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
        return ganttChart.getGanttChartBars();
    }
}
