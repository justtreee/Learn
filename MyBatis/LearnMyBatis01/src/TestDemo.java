import bean.User;
import db.DBUtils;
import db.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * Created by treee on -2018/3/10-
 */

public class TestDemo {
    @Test
    public void test1(){
        SqlSession sqlSession = null;
        try {
            sqlSession = DBUtils.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUser((long) 1);
            System.out.println(user.toString());
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void test2(){
        SqlSession sqlSession = null;
        try{
            sqlSession = DBUtils.openSession();
            User user = (User) sqlSession.selectOne("db.UserMapper.getUser", 2L);
            System.out.println(user.toString());
            sqlSession.commit();
        } catch (Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
    }
}
