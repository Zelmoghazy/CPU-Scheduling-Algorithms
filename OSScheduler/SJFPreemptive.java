import java.util.ArrayList;

public class SJFPreemptive extends SJF {

    public SJFPreemptive(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    void schedule() 
    {
        ArrayList <Process> list = new ArrayList<>(processes);
        GanttChart ganttChart = new GanttChart();
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        Process process = null;

        while (list.size() > 0){
            process = nextProcess(list, time);
            ganttChart.Schedule(process);
            process.remainingTime--;
            if(process.remainingTime == 0)
            {
                list.remove(process);
            }
            time++;
        }
        ganttChart.calculateWaitingTime();
        ganttChart.printGanttChart();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
    }
    
    @Override
    public Process nextProcess(ArrayList<Process> list, int time)
    {
        Process process = list.get(0); // First process in list
        for (Process p : list)
        {
            if ((p.getArrivalTime() <= time && p.remainingTime < process.remainingTime) || // Start time is less than current process and burst time is less than current process
                (p.getArrivalTime() <= time && process.getArrivalTime() > time))
            {
                process = p;
            }
        }
        return process;
    }

    @Override
    public String toString() {
        return "SJF Preemptive";
    }
    public ArrayList<GanttChartBar> getGanttChartBars(){
        ArrayList <Process> list = new ArrayList<>(processes);
        GanttChart ganttChart = new GanttChart();
        time = 0;
        totalWaitingTime = 0;
        averageWaitingTime = 0;
        Process process = null;

        while (list.size() > 0){
            process = nextProcess(list, time);
            ganttChart.Schedule(process);
            process.remainingTime--;
            if(process.remainingTime == 0)
            {
                list.remove(process);
            }
            time++;
        }
        ganttChart.calculateWaitingTime();
        return ganttChart.getGanttChartBars();
    }
    
    
}
