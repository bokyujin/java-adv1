package thread.control.interrupt;

import static util.ThreadUtils.sleep;
import static util.MyLogger.log;

public class ThreadStopMainV1 {
    public static void main(String[] args) {
        Mytask task= new Mytask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 runFlag=false");
        task.runFlag = false;

    }
    static class Mytask implements Runnable{

        volatile boolean runFlag = true;

        @Override
        public void run(){
            while(runFlag){
                log("작업중");
                sleep(3000);
            }
        }
    }
}
