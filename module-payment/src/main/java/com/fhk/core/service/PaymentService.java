package com.fhk.core.service;

import com.fhk.core.domain.PaymentEntity;
import com.fhk.core.domain.PaymentRepository;
import com.fhk.core.dto.PayReq;
import com.fhk.core.dto.PayRes;
import com.fhk.core.modulepgclient.cllient.TossClient;
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
