package sample;

public class Cars {

    int id;
    String name, date, time,type;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public Cars(int id, String name, String date, String time, String type) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.type = type;

    }
}
