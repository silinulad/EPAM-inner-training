import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.silin.guitarshop.model.Customer;

public class H2jdbcCreateDemo {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=UTF-8";

	// Database credentials
	static final String USER = "silin";
	static final String PASS = "1234";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("getting customers...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM customers";
			ResultSet rs = stmt.executeQuery(sql);
			List<Customer> list = new ArrayList<>();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("customer_id"));
				customer.setEmail(rs.getString("email"));
				customer.setHash(rs.getString("hash"));
				customer.setFirstName(rs.getString("firstname"));
				customer.setLastName(rs.getString("lastname"));
				list.add(customer);
			}
			for (Customer customer : list) {
				System.out.println(customer);
			}

			System.out.println("Created table in given database...");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}
}
