package com.x.commons.database.core;

import com.x.commons.collection.Where;
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
        
        DBSources.load(Files.getResourcesPath()+"db.properties");
        DaoManager daoManager = DBSources.getDaoManager();
        IDao<User> dao = daoManager.getDao(User.class);
        User user = new User();
        user.setId("123");
        user.setAge(3);
        user.setName("AD");
        user.setSex(true);
        user.setBirthday(LocalDateTime.now());
        dao.add(user);
        User old = dao.getByPrimary(user.getId());
        System.out.println(old);
        Where[] ws = new Where[]{
                new Where("name", "=", "AD")
        };
        User bean = dao.getBean(ws);
        System.out.println(bean);
        bean.setAge(38);
        int edit = dao.edit(bean);
        System.out.println("edit="+edit);
        bean.setSex(false);
        User put = dao.put(bean);
        System.out.println("put");
        System.out.println(put);
        int delete = dao.delete(new String[]{"id"}, new Object[]{"123"});
        System.out.println("delete="+delete);
    }
}
