package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataaccess.TableData.Row;

/**
 * This class represents a Customer DAO
 * It follows the Table Data Gateway Pattern
 * 
 * @author JoaoR
 *
 */
public class CustomerTDGW {

	
	/**
	 * SQL - Find a customer based on vat number
	 */
	private static String FIND_CUSTOMER_BY_VAT = "SELECT * FROM Customer c WHERE c.vat = ?";
	
	/**
	 * Gets a customer based on its vat number
	 * 
	 * @param vat, customer vat number
	 * @return the corresponding customer row
	 * 
	 * @throws PersistenceException
	 */
	public static Row getCustomerById(int vat) throws PersistenceException{
		
		// prepare sql statement
		try(PreparedStatement statement = DataSource.INSTANCE.prepareStatement(FIND_CUSTOMER_BY_VAT)){
			
			// set vat to statement
			statement.setInt(1, vat);
			
			// execute query
			ResultSet rs = statement.executeQuery();
			
			// create the table data object
			TableData table = new TableData();
			
			// populate it with retrieved result set
			table.populate(rs);
			
			return table.getRecords().next();
			
		} catch (SQLException e) {
			throw new PersistenceException("Error getting customer by vat number", e);
		}	
	} 
	
	/**
	 * SQL - Insert new customer
	 */
	private static String INSERT_NEW_CUSTOMER = "INSERT INTO Customer "
			+ "(id, vat, denomination, email) "
			+ "values (DEFAULT, ?, ?, ?)";
	
	/**
	 * Inserts a new Customer
	 * 
	 * @param vat, new customer's vat
	 * @param denomination, new customer's denomination
	 * @param email, new customer's email
	 * 
	 * @throws PersistenceException
	 */
	public void insert(int vat, String denomination, String email) throws PersistenceException{
		
		// prepare sql insert statement
		try(PreparedStatement statement = DataSource.INSTANCE.prepareStatement(INSERT_NEW_CUSTOMER)){
			
			// set query values
			statement.setInt(1, vat);
			statement.setString(2, denomination);
			statement.setString(3, email);
			
			// persist new customer
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException("Error inserting new customer", e);
		}
		
	}
	
}
