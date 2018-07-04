package online.omnia.update.dao;

import online.omnia.update.entities.Cheetah;
import online.omnia.update.entities.Token;

import java.util.List;

/**
 * Created by lollipop on 01.08.2017.
 */
public interface MySQLDao {
    List<Cheetah> getCheetahAccounts();
    List<Token> getTokensCheetah();
    void updateToken(Token token);
}
