import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {

        ArrayList<Process> processes = new ArrayList<>();
        
        /* 
        processes.add(new Process(1, 5, 4));
        processes.add(new Process(2, 4, 6));
        processes.add(new Process(3, 3, 0));
        processes.add(new Process(4, 2, 6));
        processes.add(new Process(5, 4, 5));

        FCFS fcfs = new FCFS(processes);
        fcfs.print();
        */
     
        processes.add(new Process(1, 5, 0));
        processes.add(new Process(2, 3, 1));
        processes.add(new Process(3, 1, 2));
        processes.add(new Process(4, 2, 3));
        processes.add(new Process(5, 3, 4));

        RoundRobin rr = new RoundRobin(processes, 2);
        rr.print();
     
        /* 
        processes.add(new Process(1, 11, 0, 2));    
        processes.add(new Process(2, 28, 5, 0));    
        processes.add(new Process(3, 2, 12, 3));    
        processes.add(new Process(4, 10, 2, 1));    
        processes.add(new Process(5, 16, 9, 4));  
        Priority priority = new Priority(processes);
        priority.print();
        */
    }
}
