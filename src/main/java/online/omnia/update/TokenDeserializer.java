package online.omnia.update;

import com.google.gson.*;
import online.omnia.update.entities.Token;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by lollipop on 01.08.2017.
 */
public class TokenDeserializer implements JsonDeserializer<Token>{

    @Override
    public Token deserialize(JsonElement jsonElement, Type type,
                             JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();
        System.out.println(status + " " + message);
        JsonElement element = object.get("data");
        if (element != null) {
            Token token = new Token();
            token.setAccessToken(element.getAsJsonObject().get("access_token").getAsString());
            token.setTokenType(element.getAsJsonObject().get("token_type").getAsString());
            Date currentDate = new Date(System.currentTimeMillis());
            token.setCreateTime(currentDate);
            token.setExpiresTime(new Date(currentDate.getTime() + element.getAsJsonObject().get("expires_in").getAsLong() * 1000));
            return token;
        }
        return null;
    }
}
