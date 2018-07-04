package online.omnia.update;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.omnia.update.dao.MySQLDaoImpl;
import online.omnia.update.entities.Cheetah;
import online.omnia.update.entities.Token;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lollipop on 01.08.2017.
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        List<Cheetah> accounts = MySQLDaoImpl.getInstance().getCheetahAccounts();
        List<Token> tokens = MySQLDaoImpl.getInstance().getTokensCheetah();
        GsonBuilder builder = new GsonBuilder();
        Date currentDate = new Date();
        builder.registerTypeAdapter(Token.class, new TokenDeserializer());
        for (Cheetah account : accounts) {
            for (Token  token : tokens) {
                if (token.getAccountId() == account.getAccountId()
                        && ((token.getExpiresTime() == null)
                        || token.getExpiresTime().getTime() - currentDate.getTime() < 172800000)) {

                    System.out.println(token);
                    String answer = main.getJson("https://api.ori.cmcm.com", account.getClientId(), account.getClientSecret());
                    Gson gson = builder.create();
                    Token currentToken = gson.fromJson(answer, Token.class);
                    token.setAccessToken(currentToken.getAccessToken());
                    token.setExpiresTime(currentToken.getExpiresTime());
                    token.setCreateTime(currentToken.getCreateTime());
                    token.setTokenType(token.getTokenType());
                    MySQLDaoImpl.getInstance().updateToken(token);
                    answer = null;
                    currentToken = null;
                    gson = null;
                    answer = null;
                }
            }
        }
        builder = null;
        tokens = null;
        currentDate = null;
        MySQLDaoImpl.getSessionFactory().close();
    }

    private String getJson(String url, String clientId, String clientSecret) {
        if (!url.startsWith("https://")) url = "https://" + url;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url + "/oauth/access_token");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
        nameValuePairs.add(new BasicNameValuePair("client_id", clientId));
        nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            httpResponse.close();
            reader.close();
            httpClient.close();
            httpResponse = null;
            reader = null;
            httpClient = null;
            httpPost = null;
            nameValuePairs = null;
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClient = null;
        httpPost = null;
        return null;
    }
}
