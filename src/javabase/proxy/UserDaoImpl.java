package javabase.proxy;

/**
 * @author HaoHao
 * @date 2021/4/15 11:55 上午
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("user save");
    }
}
