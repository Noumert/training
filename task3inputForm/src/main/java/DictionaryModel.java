import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryModel {
    private List<UserEntry> dictionary = new ArrayList<UserEntry>();

    public List<UserEntry> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<UserEntry> dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * add new Entry to dictionary if successfully return true if not false
     * @param surname
     * @param nickname
     * @return resultOFAdding
     */
    public boolean addUserEntryToDictionary(String surname, String nickname) {
        if(this.dictionary.stream().anyMatch(el -> (el.getNickname().equals(nickname)))){
            return false;
        }
        this.dictionary.add(new UserEntry(surname,nickname));
        return true;
    }

    /**
     * transform dictionary to List<String>
     * @return changedList
     */
    public List<String> toStringList(){
        return this.dictionary.stream().map(UserEntry::toString).collect(Collectors.toList());
    }
}
