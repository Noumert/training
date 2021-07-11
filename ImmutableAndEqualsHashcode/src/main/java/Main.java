public class Main {

    public static void main(String[] args) {

        // создаем какой то, любой объект класса UGroup, он mutable
        UGroup group = new UGroup();
        group.setGroupName("IT");
        group.setNumber(18);

        //содаем IMMUTABLE объект класса ImmutableStudent
        ImmutableStudent immutableStudent = new ImmutableStudent("Andrew",20, group);
        System.out.println("Name : " + immutableStudent.getName() + "|" + immutableStudent.getAge() + " | " + "Group : "
                + immutableStudent.getGroup().getGroupName() + "-" + immutableStudent.getGroup().getNumber());

        // пытаемся изменить поля
        immutableStudent.setGroup(new UGroup("IK",45));
        immutableStudent.setAge(21);
        immutableStudent.setName("Ivan");

        //вывод не изменился и ЭТО ХОРОШО
        System.out.println("Name : " + immutableStudent.getName() + "|" + immutableStudent.getAge() + " | " + "Group : "
                + immutableStudent.getGroup().getGroupName() + "-" + immutableStudent.getGroup().getNumber());

        //еще пытаемся изменить поля
        group.setGroupName("IV");
        group.setNumber(90);

        //вывод не изменился и ЭТО ОТЛИЧНО
        System.out.println("Name : " + immutableStudent.getName() + "|" + immutableStudent.getAge() + " | " + "Group : "
                + immutableStudent.getGroup().getGroupName() + "-" + immutableStudent.getGroup().getNumber());

        //упорно пытаемся что то сломать
        immutableStudent.setAge(12);
        immutableStudent.getGroup().setNumber(50);
        immutableStudent.setName("Petro");

        //вывод не изменился, что то менять бесполезно
        System.out.println("Name : " + immutableStudent.getName() + "|" + immutableStudent.getAge() + " | " + "Group : "
                + immutableStudent.getGroup().getGroupName() + "-" + immutableStudent.getGroup().getNumber());

        System.out.println("Immutable test finished");

        //Equals and HashCode test

        StudentEqualsHashCode studentEqualsHashCode1 = new StudentEqualsHashCode("Vova",20,"IT");
        StudentEqualsHashCode studentEqualsHashCode2 = new StudentEqualsHashCode("Vova",20,"IT");
        StudentEqualsHashCode studentEqualsHashCode3 = new StudentEqualsHashCode("Vova",21,"IT");
        ImmutableStudent immutableStudent1 = new ImmutableStudent("Vova",21,group);
        StudentEqualsHashCode studentEqualsHashCode4 = null;

        //должно быть true
        System.out.println("Same object: " + studentEqualsHashCode1.equals(studentEqualsHashCode1));

        //должно быть true
        System.out.println("Same values: " + studentEqualsHashCode1.equals(studentEqualsHashCode2));

        //должно быть true
        System.out.println("Same values: " + studentEqualsHashCode2.equals(studentEqualsHashCode1));

        //должно быть false
        System.out.println("Different values: " + studentEqualsHashCode1.equals(studentEqualsHashCode3));

        //должно быть false
        System.out.println("Compare with null: " + studentEqualsHashCode1.equals(studentEqualsHashCode4));

        //должно быть false
        System.out.println("Different types: " + studentEqualsHashCode1.equals(immutableStudent1));

        System.out.println("Equals and hashCode test finished");
    }
}
