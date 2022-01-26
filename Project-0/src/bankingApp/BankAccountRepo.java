package bankingApp;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;

public class BankAccountRepo implements BankAcctCRUD<BankAccountData> {
    private final Connection connection;
    public BankAccountRepo() {
        connection = ConnectionMgr.getConnection();
    }

    public BankAccountData createAccount(BankAccountData model) {
        try {
            String sql = "INSERT INTO bankAccountData (userID, balance, accountType) VALUES (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            BigDecimal d = new BigDecimal(0);
            pstmt.setInt(1, model.getUserID());
            pstmt.setBigDecimal(2, d);
            pstmt.setString(3, model.getCheckingSavings());

            pstmt.executeUpdate();
            System.out.println("successfully created account in database.");

            model.setBalance((double) 0);

            ResultSet res = pstmt.getGeneratedKeys();
            Integer newID = 0;
            if (res.next()) {
                newID = Integer.parseInt(res.getString(1));
                model.setAccountID(newID);
            }

            CustomArrayList<CustomArrayList<Object>> acctList = model.getMyAccountList();
            CustomArrayList<Object> miniList = new CustomArrayList<>();
            miniList.add(newID);
            miniList.add(model.getUserID());
            miniList.add(0);
            miniList.add(model.getCheckingSavings());
            miniList.add(null);
            acctList.add(miniList);
            model.setMyAccountList(acctList);

            for (int i = 0; i < acctList.size(); i++) {
                for (int j = 0; j < acctList.get(i).size(); j++) {
                    System.out.print(acctList.get(i).get(j) + " ");
                }
                System.out.println(" ");
            }

            CustomArrayList<Integer> idList = model.getMyAccountIDList();
            idList.add(newID);
            model.setMyAccountIDList(idList);

            // entering into transactionData table
            String sql3 = "INSERT INTO transactionData (accountID, entryDate, withdrawDeposit, amount, newBalance) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt3 = connection.prepareStatement(sql3);

            java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            pstmt3.setInt(1, model.getAccountID());
            pstmt3.setDate(2, sqlDate);
            pstmt3.setString(3, "account created");
            pstmt3.setBigDecimal(4, new BigDecimal(0));
            pstmt3.setBigDecimal(5, new BigDecimal(0));

            pstmt3.executeUpdate();

            return model;

        } catch (SQLException e) {
            System.out.println("We are having difficulties accessing the database.");
        }
        return null;
    };

    @Override
    public CustomArrayList<CustomArrayList<Object>> readAccount(BankAccountData model) {
        try {
            String sql2 = "SELECT * FROM bankAccountData WHERE userID = ? OR userID2 = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql2);
            pstmt.setInt(1, model.getUserID());
            pstmt.setInt(2, model.getUserID());
            ResultSet rs = pstmt.executeQuery();

            String sql4 = "SELECT accountID FROM bankAccountData WHERE userID = ? OR userID2 = ?";
            PreparedStatement pstmt4 = connection.prepareStatement(sql4);
            pstmt4.setInt(1, model.getUserID());
            pstmt4.setInt(2, model.getUserID());
            ResultSet rs2 = pstmt4.executeQuery();

            // order: accountID, userID, balance, accountType, userID2
            CustomArrayList<CustomArrayList<Object>> accountList = new CustomArrayList<>();
            CustomArrayList<Object> innerList;
            CustomArrayList<Integer> idList = new CustomArrayList<>();

            while (rs.next() && rs2.next()) {
                innerList = new CustomArrayList<>();
                innerList.add(rs2.getInt("accountID"));
                idList.add(rs2.getInt("accountID"));
                innerList.add(rs.getInt("userID"));
                innerList.add((rs.getBigDecimal("balance")).doubleValue());
                innerList.add(rs.getString("accountType"));
                innerList.add(rs.getInt("userID2"));
                accountList.add(innerList);
            }
            model.setMyAccountList(accountList);
            model.setMyAccountIDList(idList);

            return accountList;

        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }

        return null;
    }

    public BankAccountData updateJointUser(BankAccountData model) {
        try {
            String sql = "UPDATE bankAccountData SET userID2 = ? WHERE accountID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, model.getUserID2());
            pstmt.setInt(2, model.getAccountID());

            pstmt.executeUpdate();

            Integer temp1 = model.getAccountID();
            CustomArrayList<CustomArrayList<Object>> temp2 = model.getMyAccountList();
            Integer temp2_5 = null;
            for (int i = 0; i < temp2.size(); i++) {
                if (temp2.get(i).contains(temp1) == 0) {
                    temp2_5 = i;
                    break;
                }
            }
            CustomArrayList<Object> temp3;
            temp3 = temp2.get(temp2_5);
            temp3.set(4, model.getUserID2());
            temp2.set(temp2_5, temp3);
            model.setMyAccountList(temp2);

            return model;
        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
        return null;
    }

    public void deposit(BankAccountData model, Double d) {
        // id, userID, balance, type, userID2
        Integer temp1 = model.getToAccountID();
        CustomArrayList<CustomArrayList<Object>> temp2 = model.getMyAccountList();
        Integer temp2_5 = null;
        for (int i = 0; i < temp2.size(); i++) {
            if (temp2.get(i).contains(temp1) == 0) {
                temp2_5 = i;
                break;
            }
        }
        CustomArrayList<Object> temp3 = temp2.get(temp2_5);
        Double temp4 = (Double) temp3.get(2);
        model.setBalance(temp4+d);
        temp3.set(2, temp4 + d);
        temp2.set(temp2_5, temp3);
        model.setMyAccountList(temp2);
        try {
            String sql = "UPDATE bankAccountData SET balance = ? WHERE accountID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            BigDecimal bd = BigDecimal.valueOf(model.getBalance());
            pstmt.setBigDecimal(1, bd);
            pstmt.setInt(2, model.getToAccountID());

            pstmt.executeUpdate();

            // entering into transactionData table
            String sql3 = "INSERT INTO transactionData (accountID, entryDate, withdrawDeposit, amount, newBalance) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt3 = connection.prepareStatement(sql3);

            java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            pstmt3.setInt(1, model.getToAccountID());
            pstmt3.setDate(2, sqlDate);
            pstmt3.setString(3, "deposit");
            pstmt3.setBigDecimal(4, new BigDecimal(d));
            pstmt3.setBigDecimal(5, new BigDecimal(model.getBalance()));

            pstmt3.executeUpdate();
        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
    };

    public void withdraw(BankAccountData model, Double d) {
        // also enter into Transactions data
        // id, userID, balance, type, userID2
        Integer temp1 = model.getFromAccountID();
        CustomArrayList<CustomArrayList<Object>> temp2 = model.getMyAccountList();
        Integer temp2_5 = null;
        for (int i = 0; i < temp2.size(); i++) {
            if (temp2.get(i).contains(temp1) == 0) {
                temp2_5 = i;
                break;
            }
        }
        CustomArrayList<Object> temp3 = temp2.get(temp2_5);
        Double temp4 = (Double) temp3.get(2);
        model.setBalance(temp4-d);
        temp3.set(2, temp4 + d);
        temp2.set(temp2_5, temp3);
        model.setMyAccountList(temp2);

        //model.setBalance((Double) model.getMyAccountList().get(model.getToAccountID()).get(3)-d);
        try {
            String sql = "UPDATE bankAccountData SET balance = ? WHERE accountID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            BigDecimal bd = BigDecimal.valueOf(model.getBalance());
            pstmt.setBigDecimal(1, bd);
            pstmt.setInt(2, model.getFromAccountID());

            pstmt.executeUpdate();

            // entering into transactionData table
            String sql3 = "INSERT INTO transactionData (accountID, entryDate, withdrawDeposit, amount, newBalance) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt3 = connection.prepareStatement(sql3);

            java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            pstmt3.setInt(1, model.getFromAccountID());
            pstmt3.setDate(2, sqlDate);
            pstmt3.setString(3, "withdrawal");
            pstmt3.setBigDecimal(4, new BigDecimal(d));
            pstmt3.setBigDecimal(5, new BigDecimal(model.getBalance()));

            pstmt3.executeUpdate();
        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
    };

    @Override
    public void deleteAccount(BankAccountData model) {
        try {
            // entering into transactionData table
            String sql3 = "INSERT INTO transactionData (accountID, entryDate, withdrawDeposit, amount, newBalance) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt3 = connection.prepareStatement(sql3);

            java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            pstmt3.setInt(1, model.getAccountID());
            System.out.println("posted ID");
            pstmt3.setDate(2, sqlDate);
            System.out.println("posted date");
            pstmt3.setString(3, "account closed");
            System.out.println("posted description");
            pstmt3.setBigDecimal(4, new BigDecimal(0));
            System.out.println("posted change");
            pstmt3.setBigDecimal(5, new BigDecimal(0));
            System.out.println("posted new balance");

            pstmt3.executeUpdate();
            System.out.println("executed update");

            String sqlBefore = "SET FOREIGN_KEY_CHECKS=0";
            PreparedStatement pstmtBefore = connection.prepareStatement(sqlBefore);
            pstmtBefore.executeUpdate();
            System.out.println("executed before sql");

            String sql = "DELETE FROM bankAccountData WHERE accountID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, model.getAccountID());
            pstmt.executeUpdate();
            System.out.println("executed delete");

            String sqlAfter = "SET FOREIGN_KEY_CHECKS=1";
            PreparedStatement pstmtAfter = connection.prepareStatement(sqlAfter);
            pstmtAfter.executeUpdate();
            System.out.println("executed after sql");


            Integer indexDelete = model.getMyAccountIDList().contains(model.getAccountID());
            CustomArrayList<CustomArrayList<Object>> temp = model.getMyAccountList();
            temp.remove(indexDelete);
            model.setMyAccountList(temp);
            CustomArrayList<Integer> temp2 = model.getMyAccountIDList();
            temp2.remove(indexDelete);
            model.setMyAccountIDList(temp2);



        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
    }

    @Override
    public void viewHistory(BankAccountData model)  {
        try {
            String sql = "SELECT * FROM transactionData WHERE accountID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, model.getAccountID());
            ResultSet rs = pstmt.executeQuery();

            // order: transactionID, accountID, entryDate, withdrawDeposit, amount, newBalance
            System.out.println("Date" + "           " + "Description" + "              " + "Change" + "            " + "New Balance");
            while(rs.next()) {
                //System.out.print(rs.getInt("transactionID") + "     ");
                //System.out.print(rs.getInt("accountID") + "     ");
                System.out.print(rs.getDate("entryDate") + "     ");
                System.out.print(rs.getString("withdrawDeposit") + "                 ");
                System.out.print(BankAccountData.displayCurrency(rs.getBigDecimal("amount").doubleValue()) + "           ");
                System.out.println(BankAccountData.displayCurrency(rs.getBigDecimal("newBalance").doubleValue()) + "     ");
            }
        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
    }

    @Override
    public void transfer(BankAccountData model, Double d)  {
        withdraw(model, d);
        deposit(model, d);
    }

}
