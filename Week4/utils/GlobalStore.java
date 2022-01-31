package utils;

import java.util.LinkedList;
import java.util.List;

public class GlobalStore {
    //private static DataObject obj;
    List<Object> objList;

    public GlobalStore() {
        this.objList = new LinkedList<>();
    }

    public void setObjList(List<Object> objList) {
        this.objList = objList;
    }

    public List<Object> getObjList() {
        return objList;
    }
}
