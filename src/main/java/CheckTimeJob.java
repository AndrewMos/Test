import org.quartz.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CheckTimeJob implements org.quartz.Job {
    public CheckTimeJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {

        Date[] schedule = new Date[12];

        Date now = new Date();
//        now.setHours(9);
//        now.setMinutes(50);

        schedule[0] = new Date();
        schedule[0].setHours(8);
        schedule[0].setMinutes(15);

        schedule[1] = new Date();
        schedule[1].setHours(9);
        schedule[1].setMinutes(45);

        schedule[2] = new Date();
        schedule[2].setHours(10);
        schedule[2].setMinutes(00);

        schedule[3] = new Date();
        schedule[3].setHours(11);
        schedule[3].setMinutes(30);

        schedule[4] = new Date();
        schedule[4].setHours(11);
        schedule[4].setMinutes(45);

        schedule[5] = new Date();
        schedule[5].setHours(13);
        schedule[5].setMinutes(15);

        schedule[6] = new Date();
        schedule[6].setHours(13);
        schedule[6].setMinutes(45);

        schedule[7] = new Date();
        schedule[7].setHours(15);
        schedule[7].setMinutes(15);

        schedule[8] = new Date();
        schedule[8].setHours(15);
        schedule[8].setMinutes(30);

        schedule[9] = new Date();
        schedule[9].setHours(17);
        schedule[9].setMinutes(00);

        schedule[10] = new Date();
        schedule[10].setHours(17);
        schedule[10].setMinutes(15);

        schedule[11] = new Date();
        schedule[11].setHours(18);
        schedule[11].setMinutes(45);

        for (int i = 0; i < 11; i++) {
            if (now.before(schedule[i])) {//check where we are in time of lesson
                long difference = schedule[i].getTime() - now.getTime();
                int minutes = (int) ((difference / (1000 * 60)));

                System.out.print(minutes);
                if (i%2 == 0) {
                    System.out.println(" minutes to lesson start");
                } else {
                    System.out.println(" minutes to lesson end");
                }
                break;// zamykamy petle zeby sprawdzic czas do nastepnego zajecia
            }

        }


    }
}
