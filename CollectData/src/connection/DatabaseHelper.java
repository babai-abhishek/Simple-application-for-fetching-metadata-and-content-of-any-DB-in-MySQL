package connection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.sql.DatabaseMetaData;
import com.mysql.jdbc.Statement;
import org.json.*;
import entity.TableName;

public class DatabaseHelper {

	public ArrayList<TableName> fetchMetadata(String dbName) throws SQLException {
		// TODO Auto-generated method stub

		String datbaseName = dbName;
		boolean bool = false;
		ArrayList<TableName> tables = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection myCon = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");

			if(myCon != null){
				ResultSet rs1 = myCon.getMetaData().getCatalogs();
				while(rs1.next()){
					String catalog = rs1.getString(1);
					if(datbaseName.equals(catalog))
					{
						bool = true ;
						break;
					}
				}
			}
			if(bool==true){
				tables = new ArrayList<TableName>();

				java.sql.DatabaseMetaData dbmd = myCon.getMetaData();  
				String   catalog          = dbName;
				String   schemaPattern    = null;
				String   tableNamePattern = null;
				String[] types            = null;

				ResultSet result = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types );

				while(result.next()) {
					String tableName = result.getString(3);			    
					tables.add(new TableName(tableName));
				}		
			}


			myCon.close();  		  

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tables;

	}

	public JSONArray fetchTableDetails(String tableName, String dbName) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;		

		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			String serverName = "localhost:3306";
			String schema = dbName;
			String url = "jdbc:mysql://" + serverName +  "/" + schema;
			String username = "root";
			String password = "";
			connection = DriverManager.getConnection(url, username, password);


			String sql = "select * from "+tableName+"";
			java.sql.Statement myStmt=connection.createStatement();
			ResultSet rs = myStmt.executeQuery(sql);
			JSONArray jsonArray = new JSONArray();

			while (rs.next()) {
				int total_rows = rs.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
				for (int i = 0; i < total_rows; i++) {
					String columnName = rs.getMetaData().getColumnLabel(i + 1).toLowerCase();
					System.out.println(columnName);
					Object columnValue = rs.getObject(i + 1);
					System.out.println(columnValue);
					// if value in DB is null, then we set it to default value
					if (columnValue == null){
						columnValue = "null";
					}

					if (obj.has(columnName)){
						columnName += "1";
					}
					obj.put(columnName, columnValue);


				}
				jsonArray.put(obj);
				//  System.out.println(jsonArray);
			}

			connection.close();
			return jsonArray;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */ catch (JSONException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		return null;
	}

}
