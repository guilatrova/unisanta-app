package si.unisanta.tcc.unisantaapp.domain.valueobjects;

public class Time {
    protected int hour;
    protected int minute;

    protected Time() {}

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time(String time) {
        String[] split = time.split(":");
        this.hour = Integer.parseInt(split[0]);
        this.minute = Integer.parseInt(split[1]);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (hour != time.hour) return false;
        return minute == time.minute;

    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        return result;
    }
}
