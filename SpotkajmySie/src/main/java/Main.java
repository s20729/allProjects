import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    static LocalTime duration = LocalTime.parse("01:00");

    public static void main(String[] args) {

        MyCalendar calendar1 = getCalendarFromFile("file.json");
        MyCalendar calendar2 = getCalendarFromFile("file2.json");

        //overlapping start time working day calendar 1 and calendar 2
        LocalTime now;
        //earlier ending of working day
        LocalTime endWorkingDay;
        //list of duration where calendar 1 and calendar 2 may have a meeting
        ArrayList<StartEndTime> listOfPossiblesMeetings = new ArrayList<>();

        //union all planned meeting
        ArrayList<StartEndTime> listOfAllPlannedMeeting = Objects.requireNonNull(calendar1).getPlannedMeeting();
        listOfAllPlannedMeeting.addAll(Objects.requireNonNull(calendar2).getPlannedMeeting());

        //find the latest start and earliest end
        if (calendar2.getWorkingHours().start().isAfter(calendar1.getWorkingHours().start())){
            now = calendar2.getWorkingHours().start();
        }else{
            now = calendar1.getWorkingHours().start();
        }
        if (calendar2.getWorkingHours().end().isAfter(calendar1.getWorkingHours().end())){
            endWorkingDay = calendar1.getWorkingHours().end();
        }else {
            endWorkingDay = calendar2.getWorkingHours().end();
        }
        boolean isTimeHaveIntersectionWithPlannedMeeting=false;

        while (now.isBefore(endWorkingDay) && isBeforeOrEqualsTimeComparison(getEndOfPossibleMeeting(now), endWorkingDay)){
            for (int i = 0; i<listOfAllPlannedMeeting.size(); i++){

                //checking for a start of possible meeting to be inside a scheduled one
                if(now.isAfter(listOfAllPlannedMeeting.get(i).start()) && now.isBefore(listOfAllPlannedMeeting.get(i).end())){
                    isTimeHaveIntersectionWithPlannedMeeting = true;
                }
                //checking for a end of possible meeting to be inside a scheduled one
                if (getEndOfPossibleMeeting(now).isAfter(listOfAllPlannedMeeting.get(i).start()) && getEndOfPossibleMeeting(now).isBefore(listOfAllPlannedMeeting.get(i).end())){
                    isTimeHaveIntersectionWithPlannedMeeting = true;
                }
                //checking for a start and end of possible meeting to cover a scheduled one
                if (isBeforeOrEqualsTimeComparison(now, listOfAllPlannedMeeting.get(i).start())
                    && isAfterOrEqualsTimeComparison(getEndOfPossibleMeeting(now), listOfAllPlannedMeeting.get(i).end())){
                    isTimeHaveIntersectionWithPlannedMeeting = true;
                }
            }
            if (!isTimeHaveIntersectionWithPlannedMeeting){
                listOfPossiblesMeetings.add(new StartEndTime(now, getEndOfPossibleMeeting(now)));
            }
            isTimeHaveIntersectionWithPlannedMeeting = false;

            now = getEndOfPossibleMeeting(now);
        }
        System.out.println(listOfPossiblesMeetings);
    }
    private static MyCalendar getCalendarFromFile(String path){
        JSONParser parser = new JSONParser();
        try {
            MyCalendar calendar = new MyCalendar();
            Object obj = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject workingHours = (JSONObject) jsonObject.get("working_hours");
            calendar.setWorkingHours(parseJsonToStartEndTime(workingHours));

            JSONArray plannedMeeting = (JSONArray) jsonObject.get("planned_meeting");

            plannedMeeting.forEach( time -> {
                calendar.addStartEndTimeToPlannedMeeting( parseJsonToStartEndTime( (JSONObject) time ));
            });
            return calendar;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static StartEndTime parseJsonToStartEndTime(JSONObject time)
    {
        return new StartEndTime(LocalTime.parse(time.get("start").toString()), LocalTime.parse(time.get("end").toString()));
    }

    private static boolean isBeforeOrEqualsTimeComparison(LocalTime first, LocalTime second){
        return first.isBefore(second) || first.equals(second);
    }
    private static boolean isAfterOrEqualsTimeComparison(LocalTime first, LocalTime second){
        return first.isAfter(second) || first.equals(second);
    }
    private static LocalTime getEndOfPossibleMeeting(LocalTime now){
        return now.plusHours(duration.getHour()).plusMinutes(duration.getMinute());
    }
}