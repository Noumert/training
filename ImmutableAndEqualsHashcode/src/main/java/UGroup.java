public class UGroup {
    private String groupName;
    private int number;

    public UGroup(){
        groupName = "Deafault";
        number = 0;
    }

    public UGroup(String groupName,int number){
        this.groupName = groupName;
        this.number = number;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
