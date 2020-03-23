package com.x.commons.database.core;

import com.x.commons.util.file.Files;
import com.x.framework.database.DaoManager;
import com.x.framework.database.IDao;
import com.x.framework.database.test.data.User;

import java.time.LocalDateTime;

/**
 * @Desc TODO
 * @Date 2020-03-23 22:25
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        
        DBSources.load(Files.getResourcesPath() + "db.properties");
        DaoManager daoManager = DBSources.getDaoManager();
        IDao<User> dao = daoManager.getDao(User.class);
        dao.delete(new String[]{"id"}, new Object[]{"123"});
        User user = new User();
        user.setId("123");
        user.setAge(3);
        user.setName("AD");
        user.setSex(true);
        user.setBirthday(LocalDateTime.now());
        byte[] bs = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        user.setImage(bs);
        System.out.println(user);
        User after = dao.add(user);
        System.out.println(after);
        User old = dao.getByPrimary(user.getId());
        System.out.println(old);
    }
    
}
