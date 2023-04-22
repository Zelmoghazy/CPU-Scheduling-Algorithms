import java.util.ArrayList;

public class SJF extends SchedulingAlgorithm {

    public SJF(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    void sort() {

    }

    @Override
    void schedule() 
    {
        ArrayList <Process> list = new ArrayList<>(processes);
        GanttChart ganttChart = new GanttChart();
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        Process process = null;
        while (list.size() > 0){
            process = nextProcess(list, time);
            ganttChart.Schedule(process);
            ganttChart.passTime(process.getBurstTime());
            list.remove(process);
            time += process.getBurstTime();
        }
        ganttChart.calculateWaitingTime();
        ganttChart.printGanttChart();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
    }

    @Override
    void print() 
    {
        if(time == 0)
        {
            schedule();
        }
        System.out.println();
        System.out.println(Process.getHeader());
        for (Process p : processes) {
            System.out.println(p);
        }
        System.out.println("Average Waiting Time: " + averageWaitingTime + " ms");
        System.out.println("Average Turnaround Time: " + averageTurnAroundTime + " ms");
    }

    /*Shortest process within time period */
    public Process nextProcess(ArrayList<Process> list, int time)
    {
        Process process = list.get(0); // First process in list
        for (Process p : list)
        {
            if ((p.getArrivalTime() <= time && p.getBurstTime() < process.getBurstTime()) || // Start time is less than current process and burst time is less than current process
                (p.getArrivalTime() <= time && process.getArrivalTime() > time))
            {
                process = p;

            }
        }
        return process;
    }
    
    public ArrayList<GanttChartBar> getGanttChartBars() 
    {
        ArrayList <Process> list = new ArrayList<>(processes);
        GanttChart ganttChart = new GanttChart();
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        Process process = null;
        while (list.size() > 0){
            process = nextProcess(list, time);
            ganttChart.Schedule(process);
            ganttChart.passTime(process.getBurstTime());
            list.remove(process);
            time += process.getBurstTime();
        }
        ganttChart.calculateWaitingTime();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
        return ganttChart.getGanttChartBars();
    }
}
