package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.PicarMember;

public class PicarMemberDAOImpl extends BaseDAO implements PicarMemberDAO {

	private static final String PICARMEMBER_INSERT_SQL
	="insert into picarmember VALUES(SEQ_MEMBERNUM.nextval,?,?,?,?,?,?,10)";
	
	private static final String PICARMEMBER_SELECT_BY_ID_SQL
	="SELECT * FROM picarmember WHERE id=? and password=?";
	
	private static final String PICARMEMBER_SELECT_ALL_SQL
	="SELECT membernum,id,password,name,phone,license,to_char(validdate,'yyyy-mm-dd')validdate,MEMBERGRADE,membergrade.gradeno FROM picarmember ,membergrade where picarmember.gradeno = membergrade.gradeno ORDER BY membernum";
	
	private static final String PICARMEMBER_CHECK_BY_ID_SQL
	="SELECT COUNT(*) AS cnt FROM picarmember WHERE id=?"; 
	
	private static final String PICARMEMBER_FIND_ID_SQL
	="SELECT * FROM picarmember WHERE name=? AND phone=?";
	
	private static final String PICARMEMBER_FIND_PASSWORD_SQL
	="select * from picarmember where id=? and name=? AND phone=?";
	
	private static final String PICARMEMBER_PASSWORD_CHANGE_SQL
	="update picarmember set password=? WHERE id=? AND NAME=? AND PHONE=?";
	
	private static final String PICARMEMBER_SELECT_MEMBERNUM_SQL
	="SELECT membernum,id,password,name,phone,license,to_char(validdate,'yyyy-mm-dd')validdate ,MEMBERGRADE,membergrade.gradeno FROM picarmember ,membergrade where picarmember.gradeno = membergrade.gradeno and picarmember.membernum =?";
	
	
	//ȸ������
	@Override
	public boolean insert(PicarMember picarMember) {

		boolean result = false;
		
		Connection connection =null;
		PreparedStatement preparedStatement =null;
		
		try {
			connection=getConnection();
			preparedStatement =connection.prepareStatement(PICARMEMBER_INSERT_SQL);
			
			preparedStatement.setString(1, picarMember.getId());
			preparedStatement.setString(2, picarMember.getPassword());
			preparedStatement.setString(3, picarMember.getName());
			preparedStatement.setString(4, picarMember.getPhone());
			preparedStatement.setInt(5,picarMember.getLicense());
			preparedStatement.setString(6, picarMember.getValidate());			
							
			int rowCount = preparedStatement.executeUpdate();
			
			if(rowCount>0) {
				result = true;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeDBObjects(null, preparedStatement, connection);
		}
		return result;
	}
	
	//�α���
	@Override
	public PicarMember selectById(String id, String password) {
		
		PicarMember picarMember = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = getConnection();
			preparedStatement =connection.prepareStatement(PICARMEMBER_SELECT_BY_ID_SQL);
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,password);
			resultSet = preparedStatement.executeQuery();
			
			 if(resultSet.next()) {
				 
				picarMember = new PicarMember();
								
				picarMember.setMemberNum(resultSet.getInt("membernum"));
				picarMember.setId(resultSet.getString("id"));
				picarMember.setPassword(resultSet.getString("password"));
				picarMember.setName(resultSet.getString("name"));
				picarMember.setPhone(resultSet.getString("phone"));
				picarMember.setLicense(resultSet.getInt("license"));
				picarMember.setValidate(resultSet.getString("validdate"));
				picarMember.setGradeNo(resultSet.getInt("gradeno"));
	
			}
						
		} catch (SQLException e) {			
			e.printStackTrace();			
		}finally {
			closeDBObjects(resultSet, preparedStatement, connection);
		}
		
		return picarMember;		
	}

	//ȸ������Ʈ
	@Override
	public List<PicarMember> selectAll() {
		
		List<PicarMember> picarMembers = new ArrayList<PicarMember>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(PICARMEMBER_SELECT_ALL_SQL);
			resultSet = preparedStatement.executeQuery();
			
		while(resultSet.next()) {
			PicarMember picarMember = new PicarMember();
			
			picarMember.setMemberNum(resultSet.getInt("memberNum"));
			picarMember.setId(resultSet.getString("id"));
			picarMember.setPassword(resultSet.getString("password"));
			picarMember.setName(resultSet.getString("name"));
			picarMember.setPhone(resultSet.getString("phone"));
			picarMember.setLicense(resultSet.getInt("license"));
			picarMember.setValidate(resultSet.getString("validdate"));			
			picarMember.setGradeNo(resultSet.getInt("gradeNo"));
			picarMember.setMemberGrade(resultSet.getString("membergrade"));

			
			picarMembers.add(picarMember);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeDBObjects(resultSet, preparedStatement, connection);
		}
		return picarMembers;
	}
	
	//���̵�ã��
	@Override
	public PicarMember selectFindId(String name, String phone) {
		
		PicarMember picarMember = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = getConnection();
			preparedStatement =connection.prepareStatement(PICARMEMBER_FIND_ID_SQL);
			preparedStatement.setString(1,name);
			preparedStatement.setString(2, phone);
			resultSet = preparedStatement.executeQuery();
			
			 if(resultSet.next()) {
				 
				picarMember = new PicarMember();
						
				picarMember.setName(resultSet.getString("name"));
				picarMember.setPhone(resultSet.getString("phone"));				
				picarMember.setId(resultSet.getString("id"));	
					
			}
						
		} catch (SQLException e) {			
			e.printStackTrace();			
		}finally {
			closeDBObjects(resultSet, preparedStatement, connection);
		}
		System.out.println(picarMember);
		return picarMember;		
	}

	//��й�ȣ ã��
	@Override
	public PicarMember selectFindPassword(String id, String name, String phone) {
		
		PicarMember picarMember = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = getConnection();
			preparedStatement =connection.prepareStatement(PICARMEMBER_FIND_PASSWORD_SQL);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2,name);
			preparedStatement.setString(3, phone);
			resultSet = preparedStatement.executeQuery();
			
			 if(resultSet.next()) {
				 
				picarMember = new PicarMember();
												
				picarMember.setId(resultSet.getString("id"));			
				picarMember.setName(resultSet.getString("name"));
				picarMember.setPhone(resultSet.getString("phone"));
				picarMember.setPassword(resultSet.getString("password"));
									
			}
						
		} catch (SQLException e) {			
			e.printStackTrace();			
		}finally {
			closeDBObjects(resultSet, preparedStatement, connection);
		}
		
		return picarMember;
	}

	//��й�ȣ ����
	@Override
	public boolean update(PicarMember picarMember) {
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(PICARMEMBER_PASSWORD_CHANGE_SQL);
			preparedStatement.setString(1, picarMember.getPassword());
			preparedStatement.setString(2, picarMember.getId());
			preparedStatement.setString(3, picarMember.getName());
			preparedStatement.setString(4, picarMember.getPhone());
		
			int rowCount = preparedStatement.executeUpdate();
			
			if(rowCount>0) {
					result = true;
			}
				
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeDBObjects(null, preparedStatement, connection);
			}

			return result;

	}

	//���̵��ߺ�üũ
		@Override
	public int checkById(String id) {
			int count = 1;
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			
			try {
				connection = getConnection();
				preparedStatement = connection.prepareStatement(PICARMEMBER_CHECK_BY_ID_SQL);
				preparedStatement.setString(1, id);
				
				resultSet = preparedStatement.executeQuery();

				if(resultSet.next()) {
					count =resultSet.getInt("cnt");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}

	//������ ȸ�� ������
	@Override
	public PicarMember selectByNum(int membernum) {
		
		PicarMember picarmember = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = getConnection();
			preparedStatement =connection.prepareStatement(PICARMEMBER_SELECT_MEMBERNUM_SQL);
			preparedStatement.setInt(1,membernum);
			resultSet = preparedStatement.executeQuery();
			
			 if(resultSet.next()) {
				picarmember = new PicarMember();
				picarmember.setMemberNum(resultSet.getInt("membernum"));
				picarmember.setId(resultSet.getNString("id"));
				picarmember.setPassword(resultSet.getString("password"));
				picarmember.setName(resultSet.getString("name"));
				picarmember.setPhone(resultSet.getString("phone"));
				picarmember.setLicense(resultSet.getInt("license"));
				picarmember.setValidate(resultSet.getString("validdate"));
				picarmember.setMemberGrade(resultSet.getString("membergrade"));
				picarmember.setGradeNo(resultSet.getInt("gradeno"));
				 							
			}
						
		} catch (SQLException e) {			
			e.printStackTrace();			
		}finally {
			closeDBObjects(resultSet, preparedStatement, connection);
		}
					
		return picarmember;
	}
	
}
