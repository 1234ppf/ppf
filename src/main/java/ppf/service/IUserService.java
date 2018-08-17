package ppf.service;

import ppf.bean.User;

import java.util.List;

public interface IUserService {

    User selectUser(long userId);

    int insert(User user);

    List<User> selectAll();

    User selectUserByName(String username);

}