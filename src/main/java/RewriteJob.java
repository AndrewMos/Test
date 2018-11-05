import org.quartz.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RewriteJob implements org.quartz.Job {
    public RewriteJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        List<Person> people = (ArrayList)context.getMergedJobDataMap().get("people");

        List<String> cities = new ArrayList<String>();
        for (Person s:people) {
            cities.add(s.city);
        }

        //delete same cities an sort it
        Set<String> sort = new LinkedHashSet<String>(cities);
        cities = new ArrayList<String>(sort);
        Collections.sort(cities);

        //write to file
        try {
            FileWriter writer = new FileWriter( "result.txt", false);
            for (String c:cities) {
                writer.write(c + "\n");
                for (Person p:people) {
                    if ( p.city.equals(c) ) {
                        writer.write(p.name + " " + p.surname + " " + p.pesel +"\n");
                    }
                }
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }


}
