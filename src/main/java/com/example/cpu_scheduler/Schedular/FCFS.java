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

class FCFS extends  /*  Scheduler اكتب هن يسطا اسم الكلاس الاساسي*/ {

    FCFS() {
        super();
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

        synchronized (this) {
            while (hasProcess()) {
                Process currentProcess = queue.remove(0);
                /*System.out.println("Processing " + currentProcess.name + " at time " + currentTime);*/
                /*اكتب هنا اللي انت مستنيه عندك يسمع في الجي يو اي مثلا فلاج او حاجة شوف انت اللوجيك عندك ازاي */
                try {
                    Thread.sleep(1000 * currentProcess.burstTime); //هنا انا بعطل لتايم يونيت
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); 
                }

                currentTime += currentProcess.burstTime;
            }
           // System.out.println("CPU idle");اكتب بقى هنا الللي يسمع في ال جي يو اي انه ايدل
        }
    }
}
