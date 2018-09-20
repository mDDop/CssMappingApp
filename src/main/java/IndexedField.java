import java.util.Objects;

public class IndexedField {
    private int indexNumber;
    private String nameOfField;

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getNameOfField() {
        return nameOfField;
    }

    public void setNameOfField(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    @Override
    public String toString() {
        return "IndexedField{" +
                "indexNumber=" + indexNumber +
                ", nameOfField='" + nameOfField + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexedField that = (IndexedField) o;
        return indexNumber == that.indexNumber &&
                Objects.equals(nameOfField, that.nameOfField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexNumber, nameOfField);
    }
}
