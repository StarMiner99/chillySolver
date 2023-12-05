import java.util.*;

public class Main {

    public static void main(String[] args) {


        System.out.println("Find nearest present method: ");

        List<Action> actionsNearestMethod = FindNearestMethod.execute();

        System.out.println(actionsNearestMethod);
        System.out.println("Length: " + actionsNearestMethod.size());


    }

}