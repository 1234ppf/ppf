package ppf.service;

import ppf.bean.User;

import java.util.List;

public interface IUserService {

    User selectUser(Integer userId);

    int insert(User user);

    List<User> selectAll();

    User selectUserByName(String username);

    List<User> selectElse(User user);

}