import java.time.LocalTime;

public record StartEndTime(LocalTime start, LocalTime end){
    @Override
    public String toString() {
        return "["+ start +", "+ end + "]";
    }
}
