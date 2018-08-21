package ppf.dao;

import ppf.bean.User;

import java.util.List;

public interface userMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUser(Integer userId);

    List<User>  selectAll();

    User selectUserByName(String username);
}