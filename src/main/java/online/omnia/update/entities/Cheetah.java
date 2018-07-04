package online.omnia.update.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lollipop on 01.08.2017.
 */
@Entity
@Table(name = "accounts")
public class Cheetah {
    @Id
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "token_table_name")
    private String tokenTableName;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_secret")
    private String clientSecret;
    @Column(name = "api_key")
    private String apiKey;
    @Column(name = "username")
    private String username;
    @Column(name = "type")
    private String type;
    @Column(name = "password")
    private String password;
    @Column(name = "api_URL")
    private String apiURL;
    @Column(name = "name")
    private String name;
    @Column(name = "owner")
    private String owner;
    @Column(name = "description")
    private String description;

    public int getAccountId() {
        return accountId;
    }

    public String getTokenTableName() {
        return tokenTableName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public String getApiURL() {
        return apiURL;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }
}
