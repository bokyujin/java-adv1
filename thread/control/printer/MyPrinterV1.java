package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class MyPrinterV1 {

    public static void main(String[] args)  throws InterruptedException{
        Printer printer = new Printer();
        Thread printerTread = new Thread(printer, "printer");
        printerTread.start();

        Scanner userInput = new Scanner(System.in);
        while(true){
            log("프린터할 문서를 입력하세요. 종료(q) : ");
            String input = userInput.nextLine();
            if(input.equals("q")){
                printerTread.interrupt();
                break;
            }

            printer.addJob(input);
        }
    }

    static class Printer implements Runnable{
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run(){
            while(!Thread.interrupted()){
                if(jobQueue.isEmpty()){
                    Thread.yield();         // 추가 !!
                    continue;
                }

                try {
                    String job = jobQueue.poll();
                    log("출력 시작: " + job + ", 대기 문서:" + jobQueue);
                    Thread.sleep(3000);
                    log("출력 완료: "+job);
                } catch (InterruptedException e) {
                    log("인터럽트!");
                    break;
                }
            }

            log("프린터 종료");
        }

        public void addJob(String input){
            jobQueue.offer(input);
        }
    }
}
