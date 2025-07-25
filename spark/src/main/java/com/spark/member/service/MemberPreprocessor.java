package com.spark.member.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.spark.base.config.CoolSmsConfig;
import com.spark.member.model.Member;
import com.spark.member.dto.request.InsertMemberInfoRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.exception.CustomException;
import com.spark.base.util.FileUtil;
import com.spark.member.dto.MemberDto;
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

    public MemberDto preprocess(InsertMemberInfoRequest m, MultipartFile uploadFile) {

        MemberDto memberDto = m.toBuilder();


        if (!uploadFile.isEmpty()) {
            try {
                Map<String, String> map = fileUtil.fileupload(uploadFile, "profile");
                String filePath = map.get("filePath") + "/" + map.get("filesystemName");
                memberDto.setProFile(filePath);
            } catch (Exception e) {
                throw new CustomException("이미지 등록에 실패하였습니다", 500);
            }
        } else {
            memberDto.setProFile(null);
        }
        return memberDto;

    }


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
