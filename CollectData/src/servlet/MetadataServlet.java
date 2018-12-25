package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import connection.DatabaseHelper;
import connection.GsonHelper;
import entity.TableName;

/**
 * Servlet implementation class MetadataServlet
 */
@WebServlet("/MetadataServlet")
public class MetadataServlet extends HttpServlet {
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

		String dbName = request.getParameter("dbName");
		System.out.println(dbName);
		PrintWriter writer = response.getWriter();
		JsonObject object = new JsonObject();

		DatabaseHelper dbHelper = new DatabaseHelper();

		try {
			ArrayList<TableName> tb = dbHelper.fetchMetadata(dbName);
			if(tb!=null){
				JsonElement element = GsonHelper.getGsonInstance().toJsonTree(tb);
				JsonArray jsonArray = element.getAsJsonArray();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(200);
				object.add("server_response", jsonArray);
				response.getWriter().write(object.toString());
				
			}else{
				response.setStatus(404);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
