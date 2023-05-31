public class GanttChartBar {
    private Process process;
    private int startTime;
    private int endTime;

    public GanttChartBar(Process process, int startTime) {
        this.process = process;
        this.startTime = startTime;
    }
    public Process getProcess() {
        return process;
    }
    public int getStartTime() {
        return startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public int getDuration() {
        return endTime - startTime;
    }
    public void setEndTime(int end) {
        this.endTime = end;
    }
}
