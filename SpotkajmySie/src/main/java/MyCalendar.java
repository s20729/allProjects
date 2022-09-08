import java.util.ArrayList;

public class MyCalendar {
    private StartEndTime workingHours;

    private ArrayList<StartEndTime> plannedMeeting = new ArrayList<>();


    public void addStartEndTimeToPlannedMeeting(StartEndTime startEndTime){
        plannedMeeting.add(startEndTime);
    }

    public ArrayList<StartEndTime> getPlannedMeeting() {
        return plannedMeeting;
    }

    public StartEndTime getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(StartEndTime workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public String toString() {
        return "MyCalendar{" +
                "workingHours=" + workingHours +
                ", plannedMeeting=" + plannedMeeting +
                '}';
    }
}
