package bankingApp;

// interface blueprint for BankAccountRepo
public interface BankAcctCRUD<T> {
    public T createAccount(T t);
    public CustomArrayList<CustomArrayList<Object>> readAccount(T t);
    public T updateJointUser(T t);
    public void deposit(T t, Double d);
    public void withdraw(T t, Double d);
    public void transfer(T t, Double d);
    public void deleteAccount(T t);
    public void viewHistory(T t);
}
