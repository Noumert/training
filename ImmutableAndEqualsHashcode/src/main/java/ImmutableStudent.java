public class ImmutableStudent {
    private final String name;
    private final int age;
    private final UGroup group;

    public ImmutableStudent(String name, int age,UGroup group) {
        this.name = name;
        this.age = age;
        UGroup cloneGroup = new UGroup();
        cloneGroup.setGroupName(group.getGroupName());
        cloneGroup.setNumber(group.getNumber());
        this.group = cloneGroup;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public UGroup getGroup() {
        UGroup cloneGroup = new UGroup();
        cloneGroup.setGroupName(this.group.getGroupName());
        cloneGroup.setNumber(this.group.getNumber());
        return cloneGroup;
    }

    public ImmutableStudent setName(String name) {
        return new ImmutableStudent(name, this.age,this.group);
    }

    public ImmutableStudent setAge(int age) {
        return new ImmutableStudent(this.name, age,this.group);
    }

    public ImmutableStudent setGroup(UGroup group) {
        return new ImmutableStudent(this.name, this.age,group);
    }
}
