package online.omnia.update.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by lollipop on 01.08.2017.
 */
@Entity
@Table(name = "cheetah_token")
public class Token {
    @Id
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "expires_time")
    private Date expiresTime;


    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "accountId=" + accountId +
                ", tokenType='" + tokenType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", createTime=" + createTime +
                ", expiresTime=" + expiresTime +
                '}';
    }
}
