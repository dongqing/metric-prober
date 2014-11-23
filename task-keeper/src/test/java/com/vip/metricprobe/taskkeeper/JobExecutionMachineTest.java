package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public class JobExecutionMachineTest {

    public static void main(String []args) throws InterruptedException {




        JobExecution start = new ImmediateJobExecution();
        start.setJob(new Job() {
            @Override
            public JobResult doWork(JobContext jobContext) {
                System.out.println("the s of jobContext is " + jobContext.getS());
                jobContext.setS("china");
                return JobResult.FAILURE;
            }
        });

        /*
        JobExecution jobExecution =  JobExecutionBuilder.getInstance().repeat(3)
                .repeatIntervalInMills(2000).build();
        jobExecution.setJob(new Job() {
            @Override
            public JobResult doWork(JobContext jobContext) {
                System.out.println("the s of jobContext is " + jobContext.getS());
                return JobResult.FAILURE;
            }
        });

        jobExecution.addJobTransition(JobResult.SUCCESS , JobExecutionBuilder.getSuccessEnd());
        jobExecution.addJobTransition(JobResult.FAILURE,JobExecutionBuilder.getFailureEnd());
        */

        final WaitUntilConditionOKJobExecution waitUntilConditionOKJobExecution = new WaitUntilConditionOKJobExecution();
        waitUntilConditionOKJobExecution.setJob(new Job() {
            @Override
            public JobResult doWork(JobContext jobContext) {
                System.out.println("waitUntilConditionOKJobExecution doWork()");
                return JobResult.SUCCESS;
            }
        });


        start.addJobTransition(JobResult.SUCCESS, JobExecutionBuilder.getSuccessEnd() );
        start.addJobTransition(JobResult.FAILURE, waitUntilConditionOKJobExecution );


        JobExecutionMachine jobExecutionMachine = new DefaultJobExecutionMachine();
        jobExecutionMachine.setStartJobExecution(start);

        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                waitUntilConditionOKJobExecution.onConditionOK();

            }
        }).start();




        jobExecutionMachine.startJobExecution(new JobContext());



    }
}
