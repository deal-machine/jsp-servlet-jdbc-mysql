package net.java.usermanagement.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import net.java.usermanagement.model.User;
import net.java.usermanagement.util.JDBCUtils;

public class UserDao {	

	//define sql statements
	
	private static final String INSERT_USERS_SQL = 	"INSERT INTO users" + "(name, email, country) VALUES" + "(?, ?, ?);";
	
	private static final String SELECT_USER_BY_ID = "SELECT id, name, email, country from users WHERE id = ?";
	
	private static final String SELECT_ALL_USERS = "select * from users;";
	
	private static final String DELETE_USER = "DELETE from users WHERE id = ? ;";
	
	private static final String UPDATE_USER = "UPDATE users set name=?, email=?, country=? WHERE id=? ;";
	
	//insert record in database
	public void insertUser(User user) throws SQLException {

		try(Connection connection = JDBCUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//select user from database
	public User selectUser(int id) {
		User user = null;
		try(Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);){
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");
				
				user = new User(id, name, email, country);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//select all users from database
	public List <User> selectAllUsers(){
		List <User> users = new ArrayList <>();
		try(Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);)
		
		{
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					String email = resultSet.getString("email");
					String country = resultSet.getString("country");
					users.add(new User(id, name, email, country));
				}
			}catch(SQLException e) { e.printStackTrace(); }
		
			return users;
	}
	
	//delete user 
	public boolean deleteUser(int id) throws SQLException{
		boolean rowDeleted = false;
		try(Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);)
		{
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	//update user
	public boolean updateUser(User user) throws SQLException{
		boolean rowUpdated;
		try(Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);)
		{
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4, user.getId());
			
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
}
