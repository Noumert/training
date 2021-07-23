package vova.model.entry;

public class NumberWithIndex {
    private int number;
    private int index;

    public NumberWithIndex(int number, int index) {
        this.number = number;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberWithIndex that = (NumberWithIndex) o;

        if (number != that.number) return false;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + index;
        return result;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
