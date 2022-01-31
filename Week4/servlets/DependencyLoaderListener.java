package servlets;

import utils.GlobalStore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DependencyLoaderListener implements ServletContextListener {

    GlobalStore store;

    public GlobalStore getStore() {
        return store;
    }

    public void setStore(GlobalStore store) {
        this.store = store;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("servlet container contextIntitalized emhdo...");
        store = new GlobalStore();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}