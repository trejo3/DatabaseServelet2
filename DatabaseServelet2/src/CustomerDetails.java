



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustomerDetails
 */
@WebServlet("/CustomerDetails")
public class CustomerDetails extends HttpServlet {
     
	static Connection conn;
	static String name, Lname, address, city, state, post, phone;
	static int cID;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*PrintWriter out = response.getWriter();
		 * request.setAttribute("message","Hello World");
		 * getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);*/
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		out.println("<html><head>");
		out.println("<h1>Customers</h1>");
		
		try{
			String url = "jdbc:oracle:thin:testuserdb/password@localhost";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Properties props = new Properties();
			props.setProperty("user", "testuserdb");
			props.setProperty("password", "password");
			
			try{
				conn = DriverManager.getConnection(url,props);
			} catch (SQLException e){
				String msg = e.getMessage();
				out.println("<p>"+msg);
			}
			
			Statement stmt = conn.createStatement();
			String custID = request.getParameter("custID");
			ResultSet rs = stmt.executeQuery("select customer_id, cust_first_name, cust_last_name, cust_street_address1, cust_city, cust_state, cust_postal_code, phone_number1 from Demo_Customers where customer_id ="+custID);
			
			out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\" integrity=\"sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==\" crossorigin=\"anonymous\">");
			out.println("</head>");
			
			out.println("<div class=\"container\">");
			out.println("<table class=\"table table-striped\">");
			out.println("<th>ID</th>");
			out.println("<th>First Name</th>");
			out.println("<th>Last Name</th>");
			out.println("<th>Street Address</th>");
			out.println("<th>City</th>");
			out.println("<th>State</th>");
			out.println("<th>Zip</th>");
			out.println("<th>Phone Number</th>");
			
			while (rs.next()){
				name = rs.getString("CUST_FIRST_NAME");
				Lname = rs.getString("CUST_LAST_NAME");
				cID = rs.getInt("CUSTOMER_ID");
				address = rs.getString("CUST_STREET_ADDRESS1");
				city = rs.getString("CUST_CITY");
				state = rs.getString("CUST_STATE");
				post = rs.getString("CUST_POSTAL_CODE");
				phone= rs.getString("PHONE_NUMBER1");
				
				out.println("<tr><td>"+cID+"</td><td>"+name+"</td><td>"+Lname+"</td><td>"+address+"</td><td>"+city+"</td><td>"+state+"</td><td>"+post+"</td><td>"+phone+"</td></tr>");
			}
			
			out.println("</table>");
			out.println("</div>");
			conn.close();
		}
		catch (Exception e){
			String msg = e.getMessage();
			out.println("<p>" + msg);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
