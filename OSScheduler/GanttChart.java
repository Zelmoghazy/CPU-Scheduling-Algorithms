import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * Can be Used for any type of scheduling algorithm
 */
public class GanttChart {
    private ArrayList<GanttChartBar> ganttChartBars;
    private SortedSet<Process> processes; //Sorted processes with PID, no duplicates
    private int time;
    private double totalWaitingTime;
    private double totalTurnAroundTime;

    public GanttChart() 
    {
        this.ganttChartBars = new ArrayList<>();        
        /*Using Lambda Expression directly*/
        processes = new TreeSet<>(new Comparator<Process>(){
            @Override
            public int compare(Process p1, Process p2) {
                return p1.getPID() - p2.getPID();
            }
        });
        this.time = 0;
        this.totalWaitingTime = 0;
        this.totalTurnAroundTime = 0;
    }

    public GanttChart(ArrayList<Process> processes) 
    {
        ganttChartBars = new ArrayList<>();
        this.processes = new TreeSet<>(new Comparator<Process>(){
            @Override
            public int compare(Process p1, Process p2) {
                return p1.getPID() - p2.getPID();
            }
        });
        time = processes.get(0).getArrivalTime();
        for (Process process : processes) {
            if(process.getArrivalTime() < time)
            {
                time = process.getArrivalTime();
            }
        }
    }

    public void Schedule(Process process)
    {
        int last = ganttChartBars.size() - 1; //Last bar in the gantt chart
        /*Complete a bar and set its endTime to current time when a new process arises*/
        if(last >= 0 && ganttChartBars.get(last).getProcess() != process)
        {
            ganttChartBars.get(last).setEndTime(time);
        }
        /*If the process is not started yet or a difference process comes, add a new bar to the gantt chart*/
        if (last == -1 || ganttChartBars.get(last).getProcess() != process)
        {
            if(time != 0 && ganttChartBars.isEmpty()){
                GanttChartBar idle = new GanttChartBar(new Process(-1, time), 0);
                idle.setEndTime(time);
                ganttChartBars.add(idle);
            }
            if(time < process.getArrivalTime()) // Idle time
            {
                int idletime = process.getArrivalTime() - time;
                GanttChartBar idle = new GanttChartBar(new Process(-1,idletime),time);
                idle.setEndTime(process.getArrivalTime());
                ganttChartBars.add(idle);
                time = process.getArrivalTime();
            }
            ganttChartBars.add(new GanttChartBar(process, time));
        }
        else
        {
            /* Current process is the last one update its time */
            ganttChartBars.get(last).setEndTime(time+1);
        }
        time++;
    }

    public void passTime(int length){
        time += length-1;
        ganttChartBars.get(ganttChartBars.size()-1).setEndTime(time);
    }

    public boolean isStarted(Process process)
    {
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            if (ganttChartBar.getProcess() == process) {
                return true;
            }
        }
        return false;
    }

    public int getTime(){
        return time;
    }
    public double getTotalWaitingTime(){
        return totalWaitingTime;
    }
    public double getTotalTurnAroundTime(){
        return totalTurnAroundTime;
    }
    public void calculateWaitingTime()
    {
        totalWaitingTime = 0;
        for (GanttChartBar ganttChartBar : ganttChartBars)
        {
            processes.add(ganttChartBar.getProcess()); //Unique processes sorted by PID
        }
        for (Process process: processes)
        {
            GanttChartBar previous = null;
            int begin;
            int waitingTime=0;

            for (GanttChartBar ganttChartBar : ganttChartBars)
            {
                if (ganttChartBar.getProcess() == process) {
                    /*If there was no previous process*/
                    if (previous == null)
                    {
                        previous = ganttChartBar;
                        begin = ganttChartBar.getStartTime();
                        if(begin < process.getArrivalTime())
                        {
                            begin = process.getArrivalTime();
                        }
                        process.setStartTime(begin);
                        waitingTime = process.getStartTime() - process.getArrivalTime();
                    } 
                    else /*If there was a previous process */
                    {
                        waitingTime += ganttChartBar.getStartTime() - previous.getEndTime();
                        previous = ganttChartBar;
                    }
      
                }
            }
            process.setWaitingTime(waitingTime);
            process.setTurnAroundTime(waitingTime+process.getBurstTime());
            if (process.getPID() != -1){
                totalWaitingTime += waitingTime;
                totalTurnAroundTime += (waitingTime+process.getBurstTime());
            }
        }
    }

    public void printGanttChart()
    {
        int i = 0;
        System.out.println("Gantt Chart:");
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            if(ganttChartBar.getProcess().getPID() != -1)
            {
                System.out.print("|   P"+ganttChartBar.getProcess().getPID()+"\t");
            }else{
                System.out.print("| IDLE\t");
            }
        }
        System.out.println();
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            System.out.print(ganttChartBar.getStartTime()+"\t");
            /* Last bar */
            if(i==ganttChartBars.size()-1){
                System.out.print(ganttChartBar.getEndTime());
            }
            i++;
        }        
    }

    int getCurrentTime(){
        return time;
    }

    public ArrayList<GanttChartBar> getGanttChartBars()
    {
        return ganttChartBars;
    }

}

