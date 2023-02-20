package az.company.resources;

import az.company.dto.PasswordResetRequestDto;
import az.company.dto.PasswordResetResponseDto;
import az.company.service.TwilioOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TwilioOtpHandler {

    private final TwilioOtpService service;

    public Mono<ServerResponse> sendOtp(ServerRequest request) {
        return request.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> service.sentOTPForResetPassword(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(dto)));
    }

    public Mono<ServerResponse> validateOtp(ServerRequest request) {
        return request.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> service.validOTP(dto.getOneTimePassword(), dto.getUsername()))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(dto));

    }
}
