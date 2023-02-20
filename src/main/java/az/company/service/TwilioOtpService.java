package az.company.service;

import az.company.config.TwilioConfig;
import az.company.dto.OtpStatus;
import az.company.dto.PasswordResetRequestDto;
import az.company.dto.PasswordResetResponseDto;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.twilio.rest.api.v2010.account.Message;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TwilioOtpService {

private final TwilioConfig config;

private final Map<String,String> map=new HashMap<>();

public Mono<PasswordResetResponseDto> sentOTPForResetPassword(PasswordResetRequestDto resetRequestDto){
PasswordResetResponseDto passwordResetResponseDto=null;

try {
    PhoneNumber to= new PhoneNumber(resetRequestDto.getPhoneNumber());
    PhoneNumber from=new PhoneNumber(config.getTrialNumber());
    String otp=generateOtp();
    String otpMessage="Eziz mushtheri, Sizin OTP kodu ##"+otp+"##. Bunu Istifade ederek emeliyyati yerine yetirin";
    Message message =
            Message.creator(to,from,otpMessage)
            .create();
map.put(resetRequestDto.getUsername(),otp);
passwordResetResponseDto=new PasswordResetResponseDto(OtpStatus.DELIVERED,otpMessage);
}catch (Exception ex){
    passwordResetResponseDto=new PasswordResetResponseDto(OtpStatus.FAILED,ex.getMessage());
}
return Mono.just(passwordResetResponseDto);




}

public Mono<String> validOTP(String userInput,String username){
    if(userInput.equals(map.get(username))){
        map.remove(username,userInput);
        return Mono.just("Valid OTP ilerleyin!");
    }else{
        return Mono.error(new IllegalArgumentException("Invalid OTP retry"));
    }
}

private String generateOtp(){
    return new DecimalFormat("000000").format(new Random().nextInt(9999999));
}
}
