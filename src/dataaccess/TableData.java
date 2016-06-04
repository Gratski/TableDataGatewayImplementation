package dataaccess;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class represents a generic table data
 * It considers an arbitrary number of columns and types
 * 
 * @author JoaoR
 *
 */
public class TableData {

	/**
	 * Table data records already ready to be used as rows
	 */
	List<Row> records;
	
	/**
	 * Constructor
	 * 
	 * Initializes the records list
	 */
	public TableData(){
		this.records = new ArrayList<>();
	}
	
	/**
	 * Populates table records
	 * 
	 * @param rs, result set to be considered
	 * 
	 * @throws PersistenceException
	 */
	public void populate(ResultSet rs) throws PersistenceException{
		
		try {
			// obatin result set meta data
			ResultSetMetaData rsmd = rs.getMetaData();
			
			// iterate over results
			while(rs.next())
			{
				// instanciate the new row
				Row row = new Row();
				
				// add all values from all columns of this row
				for(int i = 0; i < rsmd.getColumnCount(); i++)
				{
					// obtain column name
					String colName = rsmd.getColumnLabel(i);
					
					// update row with key and value
					row.update(rsmd.getColumnLabel(i), rs.getObject(colName));
				}
				
				// add the new row to table data records
				records.add(row);
			}
			
		} catch (SQLException e) {
			throw new PersistenceException("Error populating records", e);
		}
		
	}
	
	/**
	 * Gets table records as an iterator 
	 * 
	 * @return the records as an iterator
	 */
	public Iterator<Row> getRecords(){
		return this.records.iterator();
	}
	
	/**
	 * This inner class represents a generic row 
	 * 
	 * @author JoaoR
	 *
	 */
	public class Row{
		
		/**
		 * Row columns and it's data
		 */
		private Map<String, Object> data;
		
		/**
		 * Constructor
		 * 
		 * Initializes the data map
		 */
		public Row(){
			data = new HashMap<>();
		}
		
		/**
		 * Gets an integer based on row column name
		 * 
		 * @param key, column name to be considered
		 * @return the corresponding integer value
		 * 
		 * @throws PersistenceException
		 */
		public int getInt(String key) throws PersistenceException{
			try {
				return (int) data.get(key);				
			} catch (ClassCastException e) {
				throw new PersistenceException("Error casting to Integer", e);
			}
		}
		
		/**
		 * Gets a String based on row column name
		 * 
		 * @param key, column name to be considered
		 * @return the corresponding String value
		 * 
		 * @throws PersistenceException
		 */
		public String getString(String key) throws PersistenceException{
			try {				
				return (String) data.get(key);
			} catch (ClassCastException e) {
				throw new PersistenceException("Error casting to string", e);
			}
		}
		
		/**
		 * Gets a Date based on row column name
		 * 
		 * @param key, column name to be considered
		 * @return the corresponding Date value
		 * 
		 * @throws PersistenceException
		 */
		public Date getDate(String key) throws PersistenceException{
			try {				
				return (Date) data.get(key);
			} catch (ClassCastException e) {
				throw new PersistenceException("Error casting to date", e);
			}
		}
		
		/**
		 * Gets an double based on row column name
		 * 
		 * @param key, column name to be considered
		 * @return the corresponding double value
		 * 
		 * @throws PersistenceException
		 */
		public double getDouble(String key) throws PersistenceException {
			try {				
				return (double) data.get(key);
			} catch ( ClassCastException e ) {
				throw new PersistenceException("Error casting double value", e);
			}
		}
		
		/**
		 * Updates the row data columns
		 * 
		 * @param key, column name to be added
		 * @param value, column value to be added
		 */
		public void update(String key, Object value) {
			this.data.put(key, value);
		}
	}
	
	
}
