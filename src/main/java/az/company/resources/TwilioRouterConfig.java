package az.company.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class TwilioRouterConfig {

private final TwilioOtpHandler handler;


@Bean
    public RouterFunction<ServerResponse> handleSms(){
    return RouterFunctions.route().
            POST("/router/sendOTP",handler::sendOtp)
            .POST("/router/validateOTP",handler::validateOtp)
            .build();
}

}
