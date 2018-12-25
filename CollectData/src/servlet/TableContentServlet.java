package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import org.json.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.DatabaseHelper;

/**
 * Servlet implementation class TableContentServlet
 */
@WebServlet("/TableContentServlet")
public class TableContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request,response);
	}
	

	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String tableName = request.getParameter("tbName");
		String dbName = request.getParameter("dbName");

		DatabaseHelper databaseHelper = new DatabaseHelper();
		try {
			 JSONArray jsonArray = databaseHelper.fetchTableDetails(tableName,dbName);
	            JSONObject obj = new JSONObject();
	            obj.put("server_response", jsonArray);			
			    PrintWriter writer = response.getWriter();
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    writer.println(obj);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
