package com.company.service;

import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.company.model.dto.request.CertifyEmailRequestDTO;
import com.company.model.entity.Certify;
import com.company.repository.CertifyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	private final CertifyRepository certifyRepository;

	@Transactional
	public void sendCertifyCode(CertifyEmailRequestDTO dto) {

		Optional<Certify> found = certifyRepository.findByEmail(dto.getEmail());

		if (found.isPresent() && found.get().getStatus() != null) {
			// 익셉션 보내
		}

		int secretNum = (int) (Math.random() * 1_000_000);
		String code = String.format("%06d", secretNum);

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(dto.getEmail());
		message.setFrom("no-reply@gmail.com");
		message.setSubject("[app_cafe] 인증코드를 보내드립니다.");
		message.setText("""
					이메일 본인 인증 절차에 따라 인증코드를 보내드립니다.

					인증코드 : #{code}
				""".replace("#{code}", code));
		mailSender.send(message);

		Certify one = new Certify();
		if (found.isPresent()) {
			Certify data = found.get();
			one.setId(data.getId());
			one.setCode(code);
			one.setEmail(data.getEmail());
		} else {
			one.setCode(code);
			one.setStatus("N");
			one.setEmail(dto.getEmail());
		}

		certifyRepository.save(one);
	}
}