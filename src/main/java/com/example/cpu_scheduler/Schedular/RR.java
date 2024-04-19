import java.util.ArrayList; 

abstract class Scheduler {
    protected ArrayList<Process> queue;

    Scheduler() {
        queue = new ArrayList<>();
    }

    abstract boolean hasProcess();

    abstract void insertProcess(Process process);

    abstract Process currentProcess();
}

class RoundRobin extends Scheduler {
    private int quantum; 
    RoundRobin(int quantum) {
        super();
        this.quantum = quantum;
    }

    @Override
    boolean hasProcess() {
        return !queue.isEmpty();
    }

    @Override
    void insertProcess(Process process) {
        queue.add(process);
    }

    @Override
    Process currentProcess() {
        if (!queue.isEmpty()) {
            return queue.get(0);
        } else {
            return null;
        }
    }

    void runScheduler() throws InterruptedException {
        int currentTime = 0;

        while (hasProcess()) {
            Process currentProcess = queue.remove(0);
           // System.out.println("Processing " + currentProcess.name + " at time " + currentTime); اكتب هنا اللي انت مستنيه عندك يسمع في الجي يو اي مثلا فلاج او حاجة شوف انت اللوجيك عندك ازاي 

            if (currentProcess.burstTime > quantum) {
                currentProcess.burstTime -= quantum;
                queue.add(currentProcess);
                currentTime += quantum;
            } else {
                currentTime += currentProcess.burstTime;
                Thread.sleep(1000 * currentProcess.burstTime); 
            }
        }
       // System.out.println("CPU idle");نفس الموضوع
    }
}
