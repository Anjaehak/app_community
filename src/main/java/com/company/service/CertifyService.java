package com.company.service;

import java.util.UUID;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.exception.AlreadyCertifyException;
import com.company.exception.CertifyException;
import com.company.model.dto.request.AuthenticationEmailCodeRequest;
import com.company.model.entity.Certify;
import com.company.repository.CertifyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CertifyService {

	private final JavaMailSender javaMailSender;

	private final CertifyRepository certifyRepository;

	public void sendCerfityCode(String email) throws AlreadyCertifyException {
		// 이미인증을 통과했는지
		var certify = certifyRepository.findByEmail(email).orElse(null);
		if (certify != null && certify.getStatus() != null) {
			throw new AlreadyCertifyException();
		}
		// 보안코드생성
		String uuid = UUID.randomUUID().toString().split("-")[0];
		System.out.println(uuid);

		// 메일생성 및 전송
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setFrom("no-reply@gmail.com");
		message.setSubject("인증코드를 보내드립니다");
		message.setText("""
				본인 인증절차에 의해 이메일 인증코드를 보내드립니다

				인증코드 : #{uuid}

				""".replace("#{uuid}", uuid));
		javaMailSender.send(message);
		// 보낸코드값을 DB에 저장
		if (certify != null) {
			certify.setCode(uuid);
			certifyRepository.save(certify);
		} else {
			var one = new Certify();
			one.setCode(uuid);
			one.setEmail(email);
			certifyRepository.save(one);
		}

	}

	@Transactional
	public void confirmCertifyCode(AuthenticationEmailCodeRequest dto) throws CertifyException {
		var confirmEmail = certifyRepository.findByEmail(dto.getEmail()).orElse(null);
		if (confirmEmail == null) {
			throw new CertifyException("인증코드를 발급받은적이 없습니다");
		} else {
			if (confirmEmail.getCode().equals(dto.getCode())) {
				confirmEmail.setStatus("pass");
				certifyRepository.save(confirmEmail);
			} else {
				throw new CertifyException("인증코드가 일치하지않습니다");
			}
		}

	}

}
