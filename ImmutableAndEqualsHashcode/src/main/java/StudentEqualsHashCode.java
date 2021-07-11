public class StudentEqualsHashCode {
    private String name;
    private int age;
    private String group;

    public StudentEqualsHashCode(String name, int age, String group) {
        this.name = name;
        this.age = age;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEqualsHashCode that = (StudentEqualsHashCode) o;

        return (age == that.age) && (group != null && group.equals(that.group)) && (name != null && name.equals(that.name));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
