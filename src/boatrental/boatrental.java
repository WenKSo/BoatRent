//Name: Wenkang Su
//Login name: wsu1
package boatrental;

import java.sql.*;

public class boatrental {
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/test?verifyServerCertificate=false&useSSL=true";

	   //  Database credentials
	   static final String USER = "root";
	   //the user name; You can change it to your username (by default it is root).
	   static final String PASS = "hello";
	   //the password; You can change it to your password (the one you used in MySQL server configuration).
	   
	   public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   try{
	      //STEP 1: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 2: Open a connection to database
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);

	      System.out.println("Creating database...");
	      stmt = conn.createStatement();
	      String sql = "DROP DATABASE IF EXISTS BoatRental";
	      stmt.executeUpdate(sql);
	      //STEP 3: Use SQL to Create Database; 
	      sql = "CREATE DATABASE IF NOT EXISTS BoatRental";
	      stmt.executeUpdate(sql);
	      System.out.println("Database created successfully...");
	      
	      //STEP 4: Use SQL to select the database;
	      sql = "use BoatRental";
	      stmt.executeUpdate(sql);
	  
	    //STEP 5: Use SQL to create Tables;
	      //STEP 5.1: Create Table Sailor;
	       sql = "create table if not exists Sailor(sid integer not null PRIMARY KEY, " +
	       		"sname varchar(20) not null," +
	       		"rating real not null," +
	       		"age integer not null)";
	       stmt.executeUpdate(sql);
	       
	       //STEP 5.2: Create Table Boats;
	       sql = "create table if not exists Boats(bid integer not null PRIMARY KEY, " +
	       		"bname varchar(40) not null," +
	       		"color varchar(40) not null)"; 
	       stmt.executeUpdate(sql);
	       
	      //STEP 5.3: Create Table Reserves;
	      //Your Task 1!
	       sql = "create table if not exists Reserves(sid integer not null, " +
	         	"bid integer not null, " +
	         	"day date not null," +
	         	"FOREIGN KEY(sid) REFERENCES Sailor(sid), " +
	         	"FOREIGN KEY(bid) REFERENCES Boats(bid))";
	       stmt.executeUpdate(sql);

	       
	        //STEP 6: Use SQL to insert tuples into Sailors;
	        //STEP 6.1: insert tuples into Table Sailors;
	         sql = "insert into Sailor values(22, 'Dustin', 7, 45)";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Sailor values(29, 'Brutus', 1, 33)";
	         stmt.executeUpdate(sql);

	         sql = "insert into Sailor values(31, 'Lubber', 8, 55)";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Sailor values(32, 'Andy', 8, 26)";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Sailor values(58, 'Rusty', 10, 35)";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Sailor values(64, 'Horatio', 7, 35)";
	         stmt.executeUpdate(sql);

	         sql = "insert into Sailor values(71, 'Zorba', 20, 18)";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Sailor values(74, 'Horatio', 9, 35)";
	         stmt.executeUpdate(sql);
	         
	       //STEP 6.3: insert tuples into Table Boats;
	       //Your Task 5: insert all tuples into Table Boats;
	         sql = "insert into Boats values(101, 'Interlake', 'Blue')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Boats values(102, 'Interlake', 'Red')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Boats values(103, 'Clipper', 'Green')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Boats values(104, 'Marine', 'Red')";
	         stmt.executeUpdate(sql);
	       
	       //STEP 6.4: insert all tuples into Table Reserves;
	       //Your Task 6: insert all tuples into Table Reserves;
	         sql = "insert into Reserves values(22, 101, '2008-10-10')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(22, 102, '2008-10-10')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(22, 103, '2009-10-08')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(22, 104, '2009-10-09')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(22, 102, '2008-11-10')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(31, 103, '2008-11-06')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(31, 104, '2008-11-12')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(64, 101, '2008-09-05')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(64, 102, '2008-09-08')";
	         stmt.executeUpdate(sql);
	         
	         sql = "insert into Reserves values(74, 103, '2008-09-08')";
	         stmt.executeUpdate(sql);

	       //STEP 7: Use SQL to ask queries and retrieve data from the tables;
	         //Q1:  Find the sids of all sailors who have reserved red boats but not green boats;
	         Statement s = conn.createStatement ();
	         s.executeQuery ("SELECT S.sid FROM Sailor S, Boats B, Reserves R WHERE S.sid = R.sid AND R.bid = B.bid AND B.color = 'Red' AND S.sid NOT IN (SELECT S1.sid FROM Sailor S1, Boats B1, Reserves R1 WHERE S1.sid = R1.sid AND R1.bid = B1.bid AND B1.color = 'Green')");
	         ResultSet rs = s.getResultSet ();
	         int count = 0;
	         while (rs.next ())
	         {
	             int idVal = rs.getInt ("sid");
	             System.out.println (
	                     "sid = " + idVal);
	             ++count;
	         }
	         System.out.println (count + " rows were retrieved");

	         //Q2: Find the names of sailors who have NOT reserved a red boat;
	         s.executeQuery ("SELECT S.sname FROM Sailor S, Reserves R WHERE S.sid = R.sid and R.sid NOT IN (SELECT R.sid FROM Reserves R, Boats B WHERE R.bid = B.bid AND B.color = 'Red')");
	         rs = s.getResultSet();
	         int countd = 0;
	         while (rs.next ())
	         {
	             String nameVal = rs.getString ("sname");
	             System.out.println (
	                     "name = " + nameVal);
	             ++countd;
	         }
	         System.out.println (countd + " rows were retrieved"); 
	         
	         //Q3: Find sailors whose rating is better than every sailor named Horatio;
	         s.executeQuery ("SELECT S.sid FROM Sailor S WHERE S.rating > ALL(SELECT s1.rating FROM Sailor S1 WHERE S1.sname = 'Horatio')");
	         rs = s.getResultSet ();
	         int counte = 0;
	         while (rs.next ())
	         {
	             String idVal = rs.getString ("sid");
	             System.out.println (
	                     "sid = " + idVal);
	             ++counte;
	         }
	         System.out.println (counte + " rows were retrieved");
	         
	         //Q4: Find the names of sailors who have reserved all boats.
	         s.executeQuery ("SELECT S.sname FROM Sailor S WHERE NOT EXISTS (SELECT * From Boats B WHERE NOT EXISTS (SELECT * FROM Reserves R WHERE S.sid = R.sid and R.bid = B.bid))");   
	         rs = s.getResultSet ();
	         int countf = 0;
	         while (rs.next ())
	         {
	             String nameVal = rs.getString ("sname");
	             System.out.println (
	                     "name = " + nameVal);
	             ++countf;
	         }
	         System.out.println (countf + " rows were retrieved");
	         
	       //Q5: For each red boat, find its number of reservations.
	         s.executeQuery ("SELECT COUNT(R.sid) FROM Boats B, Reserves R WHERE B.bid = R.bid AND B.color = 'Red' GROUP BY B.bid");
	         rs = s.getResultSet ();
	         int countg = 0;
	         while (rs.next ())
	         {
	             String intVal = rs.getString ("COUNT(R.sid)");
	             System.out.println (
	                     "count = " + intVal);
	             ++countg;
	         }
	         System.out.println (countg + " rows were retrieved");
	         
	       }catch(SQLException se){
	       //Handle errors for JDBC
	       se.printStackTrace();
	    }catch(Exception e){
	       //Handle errors for Class.forName
	       e.printStackTrace();
	    }finally{
	       //finally block used to close resources
	       try{
	          if(stmt!=null)
	             stmt.close();
	       }catch(SQLException se2){
	       }// nothing we can do
	       try{
	          if(conn!=null)
	             conn.close();
	       }catch(SQLException se){
	          se.printStackTrace();
	       }//end finally try
	    }//end try
	    System.out.println("Goodbye!");
	 }//end main
	 }//end JDBCExample