package bankingApp;

import java.util.Scanner;

// singleton design pattern: there should only ever be one instance
// of this object. Do this by making the constructor private, and
// having a public method which invokes constructor if needed. We
// abstract the viewer away from instantiation

public class ViewManager {
    private static ViewManager viewManager;
    private boolean running;
    private Scanner scanner = new Scanner(System.in);
    CustomArrayList<View> viewList;
    View nextView;

    public static String quitWord = "quit";

    // private Constructor
    private ViewManager() {
        // set up starting values and references

        running = true;
        scanner = new Scanner(System.in);
        viewList = new CustomArrayList<>();
    }

    public static ViewManager getViewManager() {
        if(viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    public void navigate(String destination) {
        for(int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i).viewName.equals(destination)) {
                nextView = viewList.get(i);
            }
        }
    }

    public void registerView(View view) {
        viewList.add(view);
    }

    public void render() {
        nextView.renderView();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void quit() {
        System.out.println("Thank you for using The Banking App. Goodbye!");
        running = false;
        scanner.close();
    }

    public boolean isRunning() {
        return running;
    }
}
