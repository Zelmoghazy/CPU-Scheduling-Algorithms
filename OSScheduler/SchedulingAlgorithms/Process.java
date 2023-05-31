public class Process {
    private int PID;
    private int burstTime;
    private int arrivalTime;
    private int startTime;
    private int waitingTime;
    private int turnAroundTime;
    private int priority;
    int remainingTime;

    public Process(int pid, int burstTime) {
        this.PID = pid;
        this.burstTime = burstTime;
        this.arrivalTime = 0;
        this.priority = 0;
        init();
    }

    public Process(int pid, int burstTime, int arrivalTime) {
        this.PID = pid;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = 0;
        this.remainingTime = burstTime;
        init();
    }

    public Process(int pid, int burstTime, int arrivalTime, int priority) {
        this.PID = pid;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        init();
    }

    public int getPID() {
        return PID;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(int time) {
    	this.arrivalTime = time;
    }
    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime()
    {
        return turnAroundTime;
    }

    public void setTurnAroundTime (int turnAroundTime){
        this.turnAroundTime = turnAroundTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void init() {
        this.startTime = -1;
        this.waitingTime = -1;
        this.remainingTime = this.burstTime;
    }

    public static String getHeader() {
        return "PID\tBurst Time\tArrival Time\tStarting Time\tWaiting Time\tTurnaround Time\tPriority";
    }
    /*to string */
    public String toString() {
        return PID + "\t" + burstTime + "\t\t" + arrivalTime + "\t\t" + startTime + "\t\t" + waitingTime + "\t\t" + turnAroundTime + "\t\t" + priority;
    }

}