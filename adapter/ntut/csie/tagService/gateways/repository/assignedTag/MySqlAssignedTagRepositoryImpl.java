package ntut.csie.tagService.gateways.repository.assignedTag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import ntut.csie.tagService.gateways.database.AssignedTagTable;
import ntut.csie.tagService.gateways.database.SqlDatabaseHelper;
import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagRepository;

public class MySqlAssignedTagRepositoryImpl implements AssignedTagRepository {
	private SqlDatabaseHelper sqlDatabaseHelper;
	private AssignedTagMapper assignedTagMapper;
	
	public MySqlAssignedTagRepositoryImpl() {
		sqlDatabaseHelper = new SqlDatabaseHelper();
		assignedTagMapper = new AssignedTagMapper();
	}

	@Override
	public synchronized void save(AssignedTag assignedTag) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			AssignedTagData data = assignedTagMapper.transformToAssignedTagData(assignedTag);
			String sql = String.format("Insert Into %s Values (?, ?, ?)",  
					AssignedTagTable.tableName);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, data.getAssignedTagId());
			preparedStatement.setString(2, data.getBacklogItemId());
			preparedStatement.setString(3, data.getTagId());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		} catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the database problem when save the assigned tag. Please contact to the system administrator!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized void remove(AssignedTag assignedTag) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			AssignedTagData data = assignedTagMapper.transformToAssignedTagData(assignedTag);
			String sql = String.format("Delete From %s Where %s = ?",
					AssignedTagTable.tableName,
					AssignedTagTable.assignedTagId,
					assignedTag.getAssignedTagId());
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, data.getAssignedTagId());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		}  catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the database problem when remove the assigned tag. Please contact to the system administrator!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}
	
	@Override
	public synchronized AssignedTag getAssignedTagById(String assignedTagId) {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		AssignedTag assignedTag = null;
		try {
			String sql = String.format("Select * From %s Where %s = ?",
					AssignedTagTable.tableName,
					AssignedTagTable.assignedTagId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, assignedTagId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				String backlogItemId = resultSet.getString(AssignedTagTable.backlogItemId);
				String tagId = resultSet.getString(AssignedTagTable.tagId);
				
				AssignedTagData data = new AssignedTagData();
				data.setAssignedTagId(assignedTagId);
				data.setBacklogItemId(backlogItemId);
				data.setTagId(tagId);

				assignedTag = assignedTagMapper.transformToAssignedTag(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
		return assignedTag;
	}

	@Override
	public synchronized Collection<AssignedTag> getAssignedTagsByBacklogItemId(String backlogItemId) {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<AssignedTag> assignedTags = new ArrayList<>();
		try {
			String sql = String.format("Select * From %s Where %s = ?",
					AssignedTagTable.tableName, 
					AssignedTagTable.backlogItemId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, backlogItemId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String assignedTagId = resultSet.getString(AssignedTagTable.assignedTagId);
				String tagId = resultSet.getString(AssignedTagTable.tagId);
				
				AssignedTagData data = new AssignedTagData();
				data.setAssignedTagId(assignedTagId);
				data.setBacklogItemId(backlogItemId);
				data.setTagId(tagId);

				AssignedTag assignedTag = assignedTagMapper.transformToAssignedTag(data);
				assignedTags.add(assignedTag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
		return assignedTags;
	}
	
	@Override
	public synchronized Collection<AssignedTag> getAssignedTagsByTagId(String tagId){
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<AssignedTag> assignedTags = new ArrayList<>();
		try {
			String sql = String.format("Select * From %s Where %s = ?",
					AssignedTagTable.tableName, 
					AssignedTagTable.tagId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, tagId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String assignedTagId = resultSet.getString(AssignedTagTable.assignedTagId);
				String backlogItemId = resultSet.getString(AssignedTagTable.backlogItemId);
				
				AssignedTagData data = new AssignedTagData();
				data.setAssignedTagId(assignedTagId);
				data.setBacklogItemId(backlogItemId);
				data.setTagId(tagId);

				AssignedTag assignedTag = assignedTagMapper.transformToAssignedTag(data);
				assignedTags.add(assignedTag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
		return assignedTags;
	}
}
