package com.spark.member.service;

import com.spark.base.config.CoolSmsConfig;
import org.springframework.stereotype.Component;

import com.spark.base.exception.CustomException;
import com.spark.base.util.FileUtil;
import com.spark.member.dto.request.PhoneRequest;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
@RequiredArgsConstructor
public class MemberPreprocessor {


    private final FileUtil fileUtil;
    private final CoolSmsConfig coolSmsConfig;




    public String coolSms(PhoneRequest phone) {


        int num[] = new int[6];

        for (int i = 0; i < num.length; i++) {
            num[i] = (int) (Math.random() * 9) + 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num.length; i++) {
            sb.append(num[i]);
        }
        String code = sb.toString();

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(coolSmsConfig.getApiKey(), coolSmsConfig.getSecretKey(), "https://api.coolsms.co.kr");

        Message message = new Message();
        message.setFrom("01055106509");
        message.setTo(phone.getPhone());
        message.setText("spark 인증 번호는 : [ " + code + " ] 입니다");

        try {

            messageService.send(message);
        } catch (NurigoMessageNotReceivedException exception) {
            throw new CustomException("문자 발송에 실패하였습니다.", 500);
        } catch (Exception exception) {
            throw new CustomException("문자 발송 중 예기치 못한 오류가 발생하였습니다.", 500);
        }


        return code;

    }





}
