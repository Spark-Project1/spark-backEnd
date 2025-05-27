package com.spark.member.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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
	
	
	public void preprocess(MemberDto m, MultipartFile uploadFile) {
        
		try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(m.getBirthDate(), formatter);
            m.setBirthDate2(Date.valueOf(localDate));
        } catch (Exception e) {
            throw new CustomException("생년월일 형식이 올바르지 않습니다.", 400);
        }

        m.setInterest(String.join(",", m.getInterest2()));
        m.setCharacter(String.join(",", m.getCharacter2()));
        m.setTendencies(String.join(",", m.getTendencies2()));


        if (!uploadFile.isEmpty()) {
            try {
                Map<String, String> map = fileUtil.fileupload(uploadFile, "profile");
                String filePath = map.get("filePath") + "/" + map.get("filesystemName");
                m.setProFile(filePath);
            } catch (Exception e) {
                throw new CustomException("이미지 등록에 실패하였습니다", 500);
            }
        } else {
            m.setProFile(null);
        }
    }
	
	
	public void memberTallDB(MemberDto m) {
		
		if (m.getTall().equals("140 - 145")) {
		    m.setTall("A");
		} else if (m.getTall().equals("145 - 150")) {
		    m.setTall("B");
		} else if (m.getTall().equals("150 - 155")) {
		    m.setTall("C");
		} else if (m.getTall().equals("155 - 160")) {
		    m.setTall("D");
		} else if (m.getTall().equals("160 - 165")) {
		    m.setTall("E");
		} else if (m.getTall().equals("165 - 170")) {
		    m.setTall("F");
		} else if (m.getTall().equals("170 - 175")) {
		    m.setTall("G");
		} else if (m.getTall().equals("175 - 180")) {
		    m.setTall("H");
		} else if (m.getTall().equals("180 - 185")) {
		    m.setTall("I");
		} else if (m.getTall().equals("185 - 190")) {
		    m.setTall("J");
		}
		
		
	}
	
	
	
	public void memberSmockDB(MemberDto m) {
		
		if(m.getSmock().equals("자주")) {
			m.setSmock("Y");
		}else if(m.getSmock().equals("가끔")){
			m.setSmock("A");
		}else if(m.getSmock().equals("안함")){
			m.setSmock("N");
		}
		
	}
	
	
	public void memberTallFront(MemberDto m) {
		if (m.getTall().equals("A")) {
		    m.setTall("140 - 145");
		} else if (m.getTall().equals("B")) {
		    m.setTall("145 - 150");
		} else if (m.getTall().equals("C")) {
		    m.setTall("150 - 155");
		} else if (m.getTall().equals("D")) {
		    m.setTall("155 - 160");
		} else if (m.getTall().equals("E")) {
		    m.setTall("160 - 165");
		} else if (m.getTall().equals("F")) {
		    m.setTall("165 - 170");
		} else if (m.getTall().equals("G")) {
		    m.setTall("170 - 175");
		} else if (m.getTall().equals("H")) {
		    m.setTall("175 - 180");
		} else if (m.getTall().equals("I")) {
		    m.setTall("180 - 185");
		} else if (m.getTall().equals("J")) {
		    m.setTall("185 - 190");
		}
	}
	
	public void memberSmockFront(MemberDto m) {
		if(m.getSmock().equals("Y")) {
			m.setSmock("자주");
		}else if(m.getSmock().equals("A")){
			m.setSmock("가끔");
		}else if(m.getSmock().equals("N")){
			m.setSmock("안함");
		}
	}
	
	
	public String coolSms(PhoneRequest phone) {
		
		
		int num[] = new int[6];
    	
    	for(int i = 0;i< num.length;i++) {
    		num[i] = (int)(Math.random() * 9) + 1;
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < num.length; i++) {
    	    sb.append(num[i]);
    	}
    	String code = sb.toString();

    	DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSERQEIBVBBZJKR", "NPEW4QVQX3KP5A5V7EQLJKJ8M7PHWOWO", "https://api.coolsms.co.kr");
    	
    	Message message = new Message();
    	message.setFrom("01055106509");
    	message.setTo(phone.getPhone());
    	message.setText("spark 인증 번호는 : [ "+ code +" ] 입니다");

    	try {
    	  
    	  messageService.send(message);
    	} catch (NurigoMessageNotReceivedException exception) {
    	  throw new CustomException("문자 발송에 실패하였습니다.",500);
    	} catch (Exception exception) {
    		throw new CustomException("문자 발송 중 예기치 못한 오류가 발생하였습니다.",500);
    	}
    	
    	
    	return code;
    	
	}
	
	
	
	

}
