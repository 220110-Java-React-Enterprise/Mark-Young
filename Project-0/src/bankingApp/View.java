package bankingApp;

// parent abstract class for all my screens
// don't feel the need to document this, as Kyle showed us all this
public abstract class View {
    protected String viewName;
    protected ViewManager viewManager;

    public View(String viewName, ViewManager viewManager) {
        this.viewName = viewName;
        this.viewManager = ViewManager.getViewManager();
    }

    public abstract void renderView();

    public String getViewName() {
        return viewName;
    }
}