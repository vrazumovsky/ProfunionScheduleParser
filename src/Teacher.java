/**
 * Created by vadim on 21/12/16.
 */
public class Teacher {

    private String name;
    private String cabinet;

    public Teacher(String name, String cabinet) {
        this.name = name;
        this.cabinet = cabinet;
    }

    public String getName() {
        return name;
    }

    public String getCabinet() {
        return cabinet;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", cabinet='" + cabinet + '\'' +
                '}';
    }
}
