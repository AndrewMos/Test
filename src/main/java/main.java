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

    public static Person inputinfo() {//loading a function which return object of type person
        Scanner scanner = new Scanner(System.in);//open scaner in consol
        System.out.println("Enter your city: ");
        String city = scanner.nextLine();//reading of city
        System.out.println("Enter your data: ");
        String data = scanner.nextLine();
        String name = data.split(" ")[0];//split string to smaller strings
        String surname = data.split(" ")[1];
        String pesel = data.split(" ")[2];
        if (checkpesel(pesel)) {//if pesel is correct, then create object person with data
            Person person = new Person(name, surname, pesel, city);//creating object
            return person;//return object of type person
        } else {
            return null;// return nothing
        }
    }

    public static boolean checkpesel(String pesel) {//function will return true or false
        int[] check = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};//array of numbers to check pesel

        String peselarray[] = pesel.split("");//we create array of strings , and write every number separately
        int numpesel[] = new int[peselarray.length];
        for (int i = 0; i < peselarray.length; i++) {
            numpesel[i] = Integer.parseInt(peselarray[i]);//every element we parse from string to integer
        }

        if (numpesel.length != 11) {
            System.out.println("MISTAKE");
            return false;// function is closed
        } else {
            int sum = 0;//
            for (int i = 0; i < 10; i ++) {//without last digit in Pesel
                sum = sum + numpesel[i] * check[i];
            }
            sum = sum % 10;
            if (sum != 0) {
                sum = 10 - sum;
            }
            if (sum != numpesel[10]) {//if chceck sum  not equal to last digit of pesel then return false
                System.out.println("MISTAKE");
                return false;
            } else {
                return true;
            }
        }
    }


    public static void main(String[] args){

        List<Person> people = new ArrayList<Person>();//list of objects of type person

        Scheduler scheduler = null;//start of scheduler
        try {
            JobDataMap map = new JobDataMap();
            map.put("people", people);

            scheduler = StdSchedulerFactory.getDefaultScheduler();//to create Scheduler ,??? it is quartz(library )

        JobDetail job = newJob(RewriteJob.class)// wyzywajem klas RewriteJob
                .withIdentity("job1", "group1")
                .usingJobData(map)
                .build();//zebrac objekt w calosc

        Trigger trigger = newTrigger()//trigger used to how often yuo will run job
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
        scheduler.scheduleJob(job, trigger);//first work for write data to file
        scheduler.scheduleJob(job2, trigger2);//show how much time to the end of the lesson
        scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }// the end of scheduler


        while (true) {// infinity loop
            Person person;//creating object of type Person
            person = inputinfo();//return object of type person
            if (person != null) {
                for (Person p:people) {
                    if (person.pesel.equals(p.pesel)) {
                        people.remove(p);
                        break;
                    }
                }
                people.add(person);//add object of type Person to array List
            }
        }
    }
}


// 00211585215