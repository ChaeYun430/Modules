package com.fhk.pay.service;

import com.fhk.pay.domain.PaymentEntity;
import com.fhk.pay.domain.PaymentRepository;
import com.fhk.pay.dto.PayReq;
import com.fhk.pay.dto.PayRes;
import com.fhk.api.cllient.TossClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossClient tossClient;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    public PayRes createPayment(PayReq payReq){
        PaymentEntity paymentEntity = PaymentEntity.create(payReq.getOrderId(), payReq.getAmount());
        paymentRepository.save(paymentEntity);
        return modelMapper.map(paymentEntity, PayRes.class);
    }

    public PayRes approvePayment(PayReq payReq){

        return null;
    }

}
