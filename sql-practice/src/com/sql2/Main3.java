package com.sql2;

import java.sql.Connection;
import java.util.Scanner;

public class Main3 {

    public static void main(String[] args) {
        //We need to store dabatbase credentila safely and securely
        // wen eed connection to mariaDB

        // we will need an interface for CRUD
        // we will implement this interface on our repos
        //
        // JDBC driver = org.mariadb.jdbc.Driver


        Connection conn = ConnectionMgr.getConnection();

        Associate tiffany = new Associate(1, "Tiffany", "Obi", 25);
        Associate riffany = new Associate(2, "Riffany", "Obi", 26);
        Associate wiffany = new Associate(3, "Wiffany", "Obi", 27);
        Associate ziffany = new Associate(4, "Ziffany", "Obi", 28);
        Associate jiffany = new Associate(5, "Jiffany", "Obi", 28);

        AssociateRepo repo = new AssociateRepo();

        System.out.println("creating...");
        repo.create(tiffany);
        repo.create(riffany);
        repo.create(wiffany);
        repo.create(ziffany);
        repo.create(jiffany);

        System.out.println("Created.");

        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        System.out.println("updating...");
        tiffany.setLastName("Chestnut");
        wiffany.setFirstName("Sir Kyle");
        repo.update(tiffany);
        repo.update(wiffany);
        System.out.println("updated");
        sc.nextLine();

        System.out.println("associate with id: 4");
        Associate queryAssociate = repo.read(4);
        System.out.println("name: " + queryAssociate.getLastName() + ", " + queryAssociate.getFirstName());

        sc.nextLine();
        System.out.println("deleting...");
        repo.delete(1);
        repo.delete(5);

        System.out.println("deleted");

        sc.nextLine();
        sc.close();

    }


}
