import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;



public class RoundRobin extends FCFS {
    
    private int timeQuantum;
    public RoundRobin(ArrayList<Process> processes, int timeQuantum) 
    {
        super(processes);
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void schedule() 
    {
        ArrayList <Process> list ;
        GanttChart ganttChart = new GanttChart();

        time = 0;
        totalWaitingTime = 0; 
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        list = new ArrayList<>(sortedProcesses);
        
        ArrayList <Process> waiting = new ArrayList<>(sortedProcesses);
        Queue<Process> readyQueue = new LinkedList<>();

        int index = 0;
        while (list.size() > 0)
        {
            Process process = list.get(index);
            if(!readyQueue.isEmpty())
            {
                process = readyQueue.poll();
            }
            ganttChart.Schedule(process);
            int processedTime = Math.min(process.remainingTime, timeQuantum);
            ganttChart.passTime(processedTime);
            process.remainingTime -= processedTime;
            if(process.remainingTime == 0)
            {
                list.remove(list.indexOf(process));
                if(list.size() > 0)
                {
                    index = index % list.size();
                }
            }
            else
            {
                index = (index+1) % list.size();
                int currentTime = ganttChart.getCurrentTime();
                for(int i = index; i < list.size(); i++)
                {
                    Process p = list.get(i);
                    if(p.getArrivalTime() <= currentTime && !readyQueue.contains(p) && waiting.contains(p))
                    {
                        readyQueue.add(p);
                        waiting.remove(p);
                    }
                }
                if(readyQueue.isEmpty() && index > 0){
                    index --;
                }
                if(!readyQueue.contains(process)){
                    readyQueue.add(process);
                }
            }
        }
        ganttChart.calculateWaitingTime();
        ganttChart.printGanttChart();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
    }

    public ArrayList<GanttChartBar> getGanttChartBars() {
        ArrayList <Process> list ;
        GanttChart ganttChart = new GanttChart();

        time = 0;
        totalWaitingTime = 0; 
        averageWaitingTime = 0;
        totalTurnAroundTime = 0;
        averageTurnAroundTime = 0;
        sort();
        list = new ArrayList<>(sortedProcesses);
        
        ArrayList <Process> waiting = new ArrayList<>(sortedProcesses);
        Queue<Process> readyQueue = new LinkedList<>();

        int index = 0;
        while (list.size() > 0)
        {
            Process process = list.get(index);
            if(!readyQueue.isEmpty())
            {
                process = readyQueue.poll();
            }
            ganttChart.Schedule(process);
            int processedTime = Math.min(process.remainingTime, timeQuantum);
            ganttChart.passTime(processedTime);
            process.remainingTime -= processedTime;
            if(process.remainingTime == 0)
            {
                list.remove(list.indexOf(process));
                if(list.size() > 0)
                {
                    index = index % list.size();
                }
            }
            else
            {
                index = (index+1) % list.size();
                int currentTime = ganttChart.getCurrentTime();
                for(int i = index; i < list.size(); i++)
                {
                    Process p = list.get(i);
                    if(p.getArrivalTime() <= currentTime && !readyQueue.contains(p) && waiting.contains(p))
                    {
                        readyQueue.add(p);
                        waiting.remove(p);
                    }
                }
                if(readyQueue.isEmpty() && index > 0){
                    index --;
                }
                if(!readyQueue.contains(process)){
                    readyQueue.add(process);
                }
            }
        }
        ganttChart.calculateWaitingTime();
        averageWaitingTime = ganttChart.getTotalWaitingTime() / processes.size();
        averageTurnAroundTime = ganttChart.getTotalTurnAroundTime() / processes.size();
        return ganttChart.getGanttChartBars();
    }
}
