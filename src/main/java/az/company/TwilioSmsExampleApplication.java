package az.company;

import az.company.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwilioSmsExampleApplication {

    private final TwilioConfig twilioConfig;

    public TwilioSmsExampleApplication(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @PostConstruct
    public void initTwilio(){
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
}

    public static void main(String[] args) {
        SpringApplication.run(TwilioSmsExampleApplication.class, args);
    }

}
