public class Present {
    private final Vec2 location;

    boolean pickedUp;

    public Present(Vec2 location) {
        this.location = location;
        pickedUp = false;
    }

    public Vec2 getLocation() {
        return location;
    }

    public void pickUp() {
        pickedUp = true;
    }
}
