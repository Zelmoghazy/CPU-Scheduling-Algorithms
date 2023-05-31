import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class PriorityPreemptive extends Priority {

    int priority = Integer.MAX_VALUE;
    ArrayList<Process> waitingProcesses = new ArrayList<Process>(processes);

    public PriorityPreemptive(ArrayList<Process> processes) {
        super(processes);
    }
    
    @Override
    void sort() {
        if (sortedProcesses == null) {
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
        } else {
            sortedProcesses.clear();
        }

        for (Process p : processes) {
            if (p.getArrivalTime() <= time && p.getPriority() <= priority && p.remainingTime > 0) {
                priority = p.getPriority();
                sortedProcesses.add(p);
            }
        }
    }

    @Override
    void schedule() {
        ganttChart = new GanttChart(processes);
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        do {
            if(sortedProcesses.isEmpty())
            {
                time++;
                sort();
                continue;
            }
            ganttChart.Schedule(sortedProcesses.first());
            sortedProcesses.first().remainingTime--;
            if (sortedProcesses.first().remainingTime == 0) {
                priority = Integer.MAX_VALUE;
                waitingProcesses.remove(sortedProcesses.first());
            }
            time++;
            sort();
        } while (!sortedProcesses.isEmpty() || (!waitingProcesses.isEmpty()));
        ganttChart.calculateWaitingTime();
        ganttChart.printGanttChart();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
    }
    @Override
    public ArrayList<GanttChartBar> getGanttChartBars() {
        ganttChart = new GanttChart(processes);
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        do {
            if(sortedProcesses.isEmpty())
            {
                time++;
                sort();
                continue;
            }
            ganttChart.Schedule(sortedProcesses.first());
            sortedProcesses.first().remainingTime--;
            if (sortedProcesses.first().remainingTime == 0) {
                priority = Integer.MAX_VALUE;
                waitingProcesses.remove(sortedProcesses.first());
            }
            time++;
            sort();
        } while (!sortedProcesses.isEmpty() || (!waitingProcesses.isEmpty()));
        ganttChart.calculateWaitingTime();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
        return ganttChart.getGanttChartBars();
    }
}
