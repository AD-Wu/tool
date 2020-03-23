package com.x.framework.database.test;

import com.x.framework.database.DaoManager;
import com.x.framework.database.IDao;
import com.x.framework.database.core.ITableInfoGetter;
import com.x.framework.database.core.XTableInfoGetter;
import com.x.framework.database.test.data.User;
import com.x.protocol.layers.application.config.DatabaseConfig;

import java.util.Date;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/23 16:53
 */
public class DaoTest {
    public static void main(String[] args) throws Exception {
        DatabaseConfig config = new DatabaseConfig();
        config.setType("mysql");
        config.setDriverClass("com.mysql.cj.jdbc.Driver");
        config.setUrl("jdbc:mysql://localhost:3306/ad?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF8");
        config.setUser("root");
        config.setPassword("123456");
        DaoManager daoManager = new DaoManager("X", config);
        ITableInfoGetter<User> getter = new XTableInfoGetter<>();
        IDao<User> dao = daoManager.getDao(User.class, getter);
        User user = new User();
        user.setId("x");
        user.setAge(3);
        user.setName("AD");
        user.setSex(true);
        user.setBirthday(new Date());
        dao.add(user);
        User old = dao.getByPrimary(user.getId());
        System.out.println(old);
    }
}
