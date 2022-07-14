package ace.studentmanagement.mybatis.mapper;


import java.util.List;

import org.apache.ibatis.annotations.*;

import ace.studentmanagement.mybatis.dto.User;

@Mapper
public interface UserMapperInterface {
	@Select("select * from user_table")
	List<User> selectAll();
	
	@Select("select * from user_table where id = #{id}")
	User selectUser(String id);
	
	@Insert("Insert into user_table(id, name, email, password, role) values(#{id}, #{name}, #{email}, #{password}, #{role})")
	int save(User user);
	
	@Update("update user_table set name=#{name}, email = #{email}, password = #{password}, role=#{role}")
	int update(User user);
	
	@Delete("delete from user_table where id = #{id}")
	int delete(String id);
	
	@Select("select * from user_table where email = #{email}")
	User selectByEmail(String email);
	
	@Select("select * from user_table where email = #{email} and password = #{password}")
	User selectByEmailAndPassword(String email, String password);
	
	@Select("select * from user_table where id=#{id} or name=#{name}")
	List<User> searchByIdOrName(String id, String name);
	
}
