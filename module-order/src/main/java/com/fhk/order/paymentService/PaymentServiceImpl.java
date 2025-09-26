package com.fhk.order.paymentService;

import com.fhk.core.service.PaymentService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {



/*    private final TossClient tossClient;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
   */
    public PayRes createPayment(PayReq payReq){
  /*      PaymentEntity paymentEntity = PaymentEntity.create(payReq.getOrderId(), payReq.getAmount());
        paymentRepository.save(paymentEntity);
        return modelMapper.map(paymentEntity, PayRes.class);*/
        log.info(payReq.toString());
        return null;
    }


    public PayRes approvePayment(PayReq payReq){
        log.info(payReq.toString());

        return null;
    }

}
