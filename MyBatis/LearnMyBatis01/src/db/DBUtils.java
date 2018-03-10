package db;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.mapping.Environment;

/**
 * Created by treee on -2018/3/10-
 */

public class DBUtils {
    private static SqlSessionFactory sqlSessionFactory = null;
    private static final Class CLASS_LOCK = DBUtils.class;

    public static SqlSessionFactory initSqlSessionFactory(){
//        一般情况下一个数据库只需要有一个SqlSessionFactory实例，过多的SqlSessionFactory会导致数据库有过多的连接，从而消耗过多的数据库资源，因此SqlSessionFactory需要我们将之做成一个单例模式
        synchronized (CLASS_LOCK){
            if(sqlSessionFactory == null){
//                配置MySQL信息
                PooledDataSource dataSource = new PooledDataSource();
                dataSource.setDriver("com.mysql.jdbc.Driver");
                dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
                dataSource.setUsername("root");
                dataSource.setPassword("");
                TransactionFactory transactionFactory = new JdbcTransactionFactory();
                Environment environment = new Environment("development", transactionFactory, dataSource);
                Configuration configuration = new Configuration(environment);
                configuration.addMapper(UserMapper.class);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSession(){
        if(sqlSessionFactory == null)
        {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}
