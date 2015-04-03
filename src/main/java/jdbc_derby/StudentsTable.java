package jdbc_derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentsTable {
	private String fixtureIntoStudents(int id, String lastname, String firstname, String middlename, int age) {
		String sql = "INSERT INTO STUDENTS VALUES ("
				+ id + ","
				+ "\'" + lastname + "\',"
				+ "\'" + firstname + "\',"
				+ "\'" + middlename + "\',"
				+ age + ")";
		return sql;
	}
	
	private String fixtureIntoOrders(int orderId, int customerId, int salerId) {
		String sql = "INSERT INTO ORDERS VALUES ("
				+ orderId + ","
				+ customerId + ","
				+ salerId + ")";
		return sql;
	}
	
	private void resToString(ResultSet res) {
		try {
			while (res.next()) {
				String s = "";
				int n = res.getMetaData().getColumnCount();
				for (int i = 1; i <= n; i++) {
					s += res.getString(i) + " ";
				}
				System.out.println(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try (Connection conn = DriverManager.getConnection("jdbc:derby:ITIS;create=true")){
			String creator = "CREATE TABLE STUDENTS ("
					+ "id INTEGER not NULL,"
					+ "lastname VARCHAR(256),"
					+ "firstname VARCHAR(256),"
					+ "middlename VARCHAR(256),"
					+ "age INTEGER not NULL,"
					+ "PRIMARY KEY(id))";
			System.out.println("Канал был открыт.");
			Statement stmt = conn.createStatement();
			stmt.execute(creator);
			
			stmt.executeUpdate(fixtureIntoStudents(1, "Филиппов", "Дмитрий", "Сергеевич", 19));
			stmt.executeUpdate(fixtureIntoStudents(2, "Петров", "Петр", "Алексеевич", 20));
			stmt.executeUpdate(fixtureIntoStudents(3, "Черечукина", "Анна", "Николаевна", 19));
			stmt.executeUpdate(fixtureIntoStudents(4, "Пестрова", "Розалина", "Михайловна", 66));
			stmt.executeUpdate(fixtureIntoStudents(5, "Кашапов", "Азат", "Сергеевич", 8));
			stmt.executeUpdate(fixtureIntoStudents(6, "Каяшев", "Михаил", "Владиславович", 19));
			stmt.executeUpdate(fixtureIntoStudents(7, "Чурукова", "Светлана", "Сергеевна", 13));
			stmt.executeUpdate(fixtureIntoStudents(8, "Чернова", "Талия", "Малюмовна", 21));
			stmt.executeUpdate(fixtureIntoStudents(9, "Гренков", "Станислав", "Дмитриевич", 18));
			stmt.executeUpdate(fixtureIntoStudents(10, "Каяшев", "Александр", "Александрович", 15));
			
			String sqlQuery = "SELECT * FROM STUDENTS WHERE MOD(id, 2) = 0 ORDER BY lastname, firstname";
			ResultSet res = stmt.executeQuery(sqlQuery);
			resToString(res);
			System.out.println();
			
			sqlQuery = "SELECT * FROM STUDENTS WHERE middlename LIKE \'Се%\'";
			res = stmt.executeQuery(sqlQuery);
			resToString(res);
			System.out.println();
			
			creator = "CREATE TABLE ORDERS ("
					+ "orderId INTEGER not NULL,"
					+ "customerId INTEGER not NULL,"
					+ "salesPersonId INTEGER not NULL,"
					+ "PRIMARY KEY(orderId))";
			stmt.execute(creator);
			System.out.println("Вторая таблица создана.");
			
			stmt.executeUpdate(fixtureIntoOrders(1, 3, 1));
			stmt.executeUpdate(fixtureIntoOrders(2, 4, 1));
			stmt.executeUpdate(fixtureIntoOrders(3, 9, 10));
			
			sqlQuery = "SELECT o.orderId, c.lastname||' '||c.firstname AS customerName, "
					+ "s.lastname||' '||s.firstname AS salesPersonName "
					+ "FROM ORDERS o, STUDENTS c, STUDENTS s "
					+ "WHERE o.customerId = c.id AND o.salesPersonId = s.id";
			res = stmt.executeQuery(sqlQuery);
			resToString(res);
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new StudentsTable().run();
	}
}