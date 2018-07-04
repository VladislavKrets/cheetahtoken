package online.omnia.update.dao;

import online.omnia.update.FileWorkingUtils;
import online.omnia.update.entities.Cheetah;
import online.omnia.update.entities.Token;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Map;

/**
 * Created by lollipop on 01.08.2017.
 */
public class MySQLDaoImpl implements MySQLDao{
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static MySQLDaoImpl instance;
    static {
        configuration = new Configuration()
                .addAnnotatedClass(Cheetah.class)
                .addAnnotatedClass(Token.class)
                .configure("/hibernate.cfg.xml");
        while (true) {
            try {
                Map<String, String> properties = FileWorkingUtils.iniFileReader();
                configuration.setProperty("hibernate.connection.password", properties.get("password"));
                configuration.setProperty("hibernate.connection.username", properties.get("username"));
                configuration.setProperty("hibernate.connection.url", properties.get("url"));
                try {
                    sessionFactory = configuration.buildSessionFactory();
                    break;
                } catch (PersistenceException e) {
                    System.out.println("No database connection");
                    System.out.println("Waiting for 30 seconds");
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ini file is incorrect");
                System.exit(1);
            }
        }
    }

    @Override
    public List<Cheetah> getCheetahAccounts() {
        Session session = sessionFactory.openSession();
        List<Cheetah> accounts = null;
        while (true) {
            try {
                accounts = session.createQuery("from Cheetah cheetah where cheetah.type=:cheetah", Cheetah.class)
                        .setParameter("cheetah", "cheetah").getResultList();
                break;
            } catch (PersistenceException e) {
                System.out.println("Cannot connect to db. Waiting for 30 seconds");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        session.close();
        return accounts;
    }

    @Override
    public List<Token> getTokensCheetah() {
        Session session = sessionFactory.openSession();
        List<Token> tokens = null;
        while (true) {
            try {
                tokens = session.createQuery("from Token", Token.class).getResultList();
                break;
            } catch (PersistenceException e) {
                System.out.println("Cannot connect to db. Waiting for 30 seconds");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        session.close();
        return tokens;
    }

    @Override
    public void updateToken(Token token) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        while (true) {
            try {
                session.createQuery("update Token set access_token=:access_token, create_time=:create_time, expires_time=:expires_time where account_id=:account_id")
                .setParameter("access_token", token.getAccessToken())
                .setParameter("create_time", token.getCreateTime())
                .setParameter("expires_time", token.getExpiresTime())
                .setParameter("account_id", token.getAccountId()).executeUpdate();
                break;
            } catch (PersistenceException e) {
                System.out.println("Cannot connect to db. Waiting for 30 seconds");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static MySQLDaoImpl getInstance() {
        if (instance == null) instance = new MySQLDaoImpl();
        return instance;
    }
}
