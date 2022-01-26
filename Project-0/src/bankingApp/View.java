package bankingApp;

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