package bankingApp;

public interface DataSourceCRUD<T> {
    // CRUD - create read update delete
    public T create(T t);
    public T readEmail(T t);
    //public T readUsername(String username);
    //public T readPassword(String username);
    public T update(T t);
    public void delete(Integer id);
}