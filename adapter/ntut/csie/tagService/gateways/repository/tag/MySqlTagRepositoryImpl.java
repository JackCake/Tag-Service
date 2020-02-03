package ntut.csie.tagService.gateways.repository.tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import ntut.csie.tagService.gateways.database.TagTable;
import ntut.csie.tagService.gateways.database.SqlDatabaseHelper;
import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.useCase.tag.TagRepository;

public class MySqlTagRepositoryImpl implements TagRepository {
	private SqlDatabaseHelper sqlDatabaseHelper;
	private TagMapper tagMapper;
	
	public MySqlTagRepositoryImpl() {
		sqlDatabaseHelper = new SqlDatabaseHelper();
		tagMapper = new TagMapper();
	}

	@Override
	public synchronized void save(Tag tag) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			TagData data = tagMapper.transformToTagData(tag);
			String sql = String.format("Insert Into %s Values (?, ?, ?, ?) "
					+ "On Duplicate Key Update %s=?, %s=?", 
					TagTable.tableName, TagTable.orderId, TagTable.name);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, data.getTagId());
			preparedStatement.setInt(2, data.getOrderId());
			preparedStatement.setString(3, data.getName());
			preparedStatement.setString(4, data.getProductId());
			preparedStatement.setInt(5, data.getOrderId());
			preparedStatement.setString(6, data.getName());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		} catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the database problem when save the tag. Please contact to the system administrator!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized void remove(Tag tag) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			TagData data = tagMapper.transformToTagData(tag);
			String sql = String.format("Delete From %s Where %s = ?",
					TagTable.tableName,
					TagTable.tagId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, data.getTagId());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		}  catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the database problem when remove the tag. Please contact to the system administrator!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized Tag getTagById(String tagId) {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Tag tag = null;
		try {
			String sql = String.format("Select * From %s Where %s = ?",
					TagTable.tableName,
					TagTable.tagId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, tagId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				int orderId = resultSet.getInt(TagTable.orderId);
				String name = resultSet.getString(TagTable.name);
				String productId = resultSet.getString(TagTable.productId);
				
				TagData data = new TagData();
				data.setTagId(tagId);
				data.setOrderId(orderId);
				data.setName(name);
				data.setProductId(productId);

				tag = tagMapper.transformToTag(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
		return tag;
	}
	
	@Override
	public synchronized Collection<Tag> getTagsByProductId(String productId){
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<Tag> tags = new ArrayList<>();
		try {
			String sql = String.format("Select * From %s Where %s = ? Order By %s",
					TagTable.tableName, 
					TagTable.productId, 
					TagTable.orderId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, productId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String tagId = resultSet.getString(TagTable.tagId);
				int orderId = resultSet.getInt(TagTable.orderId);
				String name = resultSet.getString(TagTable.name);
				
				TagData data = new TagData();
				data.setTagId(tagId);
				data.setOrderId(orderId);
				data.setName(name);
				data.setProductId(productId);

				Tag tag = tagMapper.transformToTag(data);
				tags.add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
		return tags;
	}
}
