import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Scanner;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String time1String = scanner.nextLine();
        String time2String = scanner.nextLine();
        LocalTime time1 = LocalTime.of(Integer.parseInt(time1String.split(":")[0]),
                Integer.parseInt(time1String.split(":")[1]),
                Integer.parseInt(time1String.split(":")[2]));
        LocalTime time2 = LocalTime.of(Integer.parseInt(time2String.split(":")[0]),
                Integer.parseInt(time2String.split(":")[1]),
                Integer.parseInt(time2String.split(":")[2]));
        System.out.println(Math.abs(time1.toSecondOfDay() - time2.toSecondOfDay()));
    }
}