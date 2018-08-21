package ppf.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppf.bean.User;
import ppf.dao.userMapper;
import ppf.service.IUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * userService
 *
 * 缓存机制说明：所有的查询结果都放进了缓存，也就是把MySQL查询的结果放到了redis中去，
 * 然后第二次发起该条查询时就可以从redis中去读取查询的结果，从而不与MySQL交互，从而达到优化的效果，
 * redis的查询速度之于MySQL的查询速度相当于 内存读写速度 /硬盘读写速度
 * @Cacheable("a")注解的意义就是把该方法的查询结果放到redis中去，下一次再发起查询就去redis中去取，存在redis中的数据的key就是a；
 * @CacheEvict(value={"a","b"},allEntries=true) 的意思就是执行该方法后要清除redis中key名称为a,b的数据；
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private userMapper userDao;

    @Cacheable(value="User",key="#userId")//标注该方法查询的结果进入缓存，再次访问时直接读取缓存中的数据
    public User selectUser(Integer userId) {
        return this.userDao.selectUser(userId);
    }

    public int insert(User user){
        return userDao.insert(user);
    }

    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

   @Override
   @Cacheable(value="User",key="#username")//标注该方法查询的结果进入缓存，再次访问时直接读取缓存中的数据
    public User selectUserByName(String username) {
        return userDao.selectUserByName(username);
    }


}