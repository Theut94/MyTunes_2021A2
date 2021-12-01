package bll.util;

public class ConvertTime {

    public static String secToTime(int sec)
    {

        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        String time;

        while (sec >= 3600)
        {
            hours++;
            sec -= 3600;
        }
        while (sec >= 60)
        {
            minutes++;
            sec -= 60;
        }
        while (sec >= 1)
        {
            seconds = sec;
            sec = 0;
        }

        if (hours == 0)
        {
            time = minutes + ":" + seconds;

            if (seconds < 10)
            {
                time = minutes + ":" + "0" + seconds;
            }
        } else
        {
            time = hours + ":" + minutes + ":" + seconds;
            if (minutes < 10 && seconds < 10)
            {
                time = hours + ":" + "0" + minutes + ":" + "0" + seconds;
            } else if (minutes < 10)
            {
                time = hours + ":" + "0" + minutes + ":" + seconds;
            } else if (seconds < 10)
            {
                time = hours + ":" + minutes + ":" + "0" + seconds;
            }
        }

        return time;
    }

    public static int timeToSec(String time)
    {

        String[] split = time.split(":");

        int count = split.length - 1;

        int total = 0;

        try
        {
            total += (Integer.parseInt(split[count]));
            count--;
        } catch (Exception e)
        {
            System.out.println("No seconds");
        }
        try
        {
            total += (Integer.parseInt(split[count]) * 60);
            count--;
        } catch (Exception e)
        {
            System.out.println("No minutes");
        }
        try
        {
            total += (Integer.parseInt(split[count]) * 60 * 60);
            count--;
        } catch (Exception e)
        {
            System.out.println("No hours");
        }

        return total;
    }

}
