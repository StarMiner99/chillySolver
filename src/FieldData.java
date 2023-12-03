public class FieldData {
    private int reachableIn = -1;

    private final Move destRight;
    private final Move destDown;
    private final Move destLeft;
    private final Move destUp;

    private final Field fieldType;


    public FieldData(Move destRight, Move destDown, Move destLeft, Move destUp, Field fieldType) {
        this.destRight = destRight;
        this.destDown = destDown;
        this.destLeft = destLeft;
        this.destUp = destUp;
        this.fieldType = fieldType;
    }

    public int getReachableIn() {
        return reachableIn;
    }

    public void setReachableIn(int reachableIn) {
        this.reachableIn = reachableIn;
    }

    public Move getDestRight() {
        return destRight;
    }

    public Move getDestDown() {
        return destDown;
    }

    public Move getDestLeft() {
        return destLeft;
    }

    public Move getDestUp() {
        return destUp;
    }

    public Field getFieldType() {
        return fieldType;
    }
}
