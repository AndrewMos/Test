import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalTime;
import java.util.*;
import java.io.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class main {

    public static Person inputinfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your city: ");
        String city = scanner.nextLine();
        System.out.print("Enter your data: ");
        String data = scanner.nextLine();
        String name = data.split(" ")[0];
        String surname = data.split(" ")[1];
        String pesel = data.split(" ")[2];
        Person person = new Person(name, surname, pesel, city);
        if (checkpesel(pesel)) {
            return person;
        } else {
            return null;
        }
    }

    public static boolean checkpesel(String pesel) {
        int[] check = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        String peselarray[] = pesel.split("");
        int numpesel[] = new int[peselarray.length];
        for (int i = 0; i < peselarray.length; i++) {
            numpesel[i] = Integer.parseInt(peselarray[i]);
        }

        if (numpesel.length != 11) {
            System.out.println("MISTAKE");
            return false;
        } else {
            int temp = 0;
            for (int i = 0; i < 10; i ++) {
                temp += numpesel[i] * check[i];
            }
            temp = temp % 10;
            if (temp != 0) {
                temp = 10 - temp;
            }
            if (temp != numpesel[10]) {
                System.out.println("MISTAKE");
                return false;
            } else {
                return true;
            }
        }
    }


    public static void main(String[] args){

        List<Person> people = new ArrayList<Person>();

        Scheduler scheduler = null;
        try {
            JobDataMap map = new JobDataMap();
            map.put("people", people);

            scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail job = newJob(RewriteJob.class)
                .withIdentity("job1", "group1")
                .usingJobData(map)
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/30 * * * * ? *"))
                .build();

            JobDetail job2 = newJob(CheckTimeJob.class)
                    .withIdentity("job2", "group1")
                    .build();

            Trigger trigger2 = newTrigger()
                    .withIdentity("trigger2", "group1")
                    .withSchedule(cronSchedule("0/60 * * * * ? *"))
                    .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
//        scheduler.scheduleJob(job2, trigger2);
        scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }


        while (true) {
            Person person;
            person = inputinfo();
            if (person != null) {
                for (Person p:people) {
                    if (person.pesel.equals(p.pesel)) {
                        people.remove(p);
                        break;
                    }
                }
                people.add(person);
            }
        }
    }
}


// 00211585215