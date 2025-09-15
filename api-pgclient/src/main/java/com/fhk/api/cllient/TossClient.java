package com.fhk.api.cllient;

import com.fhk.api.dto.PgReq;
import com.fhk.api.dto.PgRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TossClient implements PgClient {

        private final WebClient webClient;

        public TossClient(@Value("${tosspayments.secret-key}") String secretKey){
            this.webClient = WebClient.builder()
                    .baseUrl("https://sandbox.tosspayments.com")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeaders(h -> h.setBasicAuth(secretKey, "")) // Toss는 비밀번호 빈 문자열
                    .build();
        }

        public Mono<PgRes> confirm(PgReq req) {

            Mono<PgRes> response = webClient.post()
                    .uri("/v1/payments/confirm")
                    .bodyValue(req)
                    .retrieve()
                    .bodyToMono(PgRes.class);
            return response;
        }

/*        public PaymentEntity search(SearchReq searchReq) {

            Mono<SearchRes> response = webClient.post()
                    .uri(" /v1/payments/orders/{orderId}", "/v1/payments/{paymentKey}")
                    .attribute("orderId", searchReq.getOrderId())
                    .attribute("paymentKey", searchReq.getPaymentKey())
                    .retrieve()
                    .bodyToMono(SearchRes.class);

            return null;
        }

        public PaymentEntity cancel(CancelReq cancelReq) {
            Mono<CancelRes> response = webClient.post()
                    .uri("/v1/payments/{paymentKey}/cancel")
                    .attribute("paymentKey", cancelReq.getPaymentKey())
                    .retrieve()
                    .bodyToMono(CancelRes.class);


            return null;
        }

        public PaymentEntity virtual(VirtualReq virtualReq) {
            Mono<VirtualRes> response = webClient.post()
                    .uri("/v1/virtual-accounts")
                    .bodyValue(virtualReq)
                    .retrieve()
                    .bodyToMono(VirtualRes.class);
            return null;
        }*/

}

// --- 헤더의 인증객체 ---
//Basic Auth → Authorization: Basic <Base64(user:pass)>
//Bearer Token → Authorization: Bearer <token>
//setBasicAuth()나 setBearerAuth()를 쓰는 이유는 자동으로 Authorization 헤더를 올바른 형식으로 만들어 주기 때문
//단순히 header("Authorization", ...) 직접 넣어도 되지만, 인코딩 등을 수동으로 처리해야 함


