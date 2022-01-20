package classroom;

import javax.swing.text.View;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// singleton design pattern: there should only ever be one instance
// of this object. Do this by making the constructor private, and
// having a public method which invokes constructor if needed. We
// abstract the viewer away from instantiation



public class ViewRenderer {
    private static ViewRenderer viewRenderer;
    private boolean running;
    private Scanner scanner;
    View nextView;

    // when adapting this in P-0, don't forget to replace this with custom list structure
    List<View> viewList;

    // private Constructor
    private ViewRenderer() {
        running = true;
        scanner = new Scanner(System.in);
        viewList = new LinkedList<>();
    }

    public static ViewRenderer getViewRenderer() {
        if(viewRenderer == null) {
            viewRenderer = new ViewRenderer();
        }
        return viewRenderer;
    }

    public void navigate(String destination) {
        for(View view : viewList) {
            if (true) {
                nextView = view;
            }
        }
    }



}
