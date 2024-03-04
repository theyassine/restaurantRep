package org.example.Utils;


    import com.twilio.Twilio;
import com.twilio.converter.Promoter;
    import com.twilio.rest.api.v2010.account.Call;
    import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
    import org.example.entities.Gerant;

    import java.net.URI;
import java.math.BigDecimal;
    import java.net.URISyntaxException;

public class Sms {
    // Find your Account Sid and Token at twilio.com/console

    public static void sendSms(Gerant gerant, String code ) {
       String ACCOUNT_SID = "ACc530ea679fa136df8d9d6295e473504d";
       String AUTH_TOKEN = "fb360275c4c55b287929e890754b9125";


        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        // to phone number
                        new com.twilio.type.PhoneNumber("+216"+gerant.getNum_telephone()),
                        new com.twilio.type.PhoneNumber("+19124552157"),
                        " Votre code de reinitialisation du not de passe est : "+code)
                .create();

        System.out.println(message.getSid());
    }



}

