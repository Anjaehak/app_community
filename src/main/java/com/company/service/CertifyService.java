package com.company.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.company.exception.AlreadyCertifyException;
import com.company.exception.CertifyFailException;
import com.company.model.dto.user.request.CertifyCodeRequest;
import com.company.model.dto.user.request.CertifyEmailRequest;
import com.company.model.dto.user.response.CertifyResponse;
import com.company.model.entity.Certify;
import com.company.repository.CertifyRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertifyService {

	private final JavaMailSender mailSender;

	private final CertifyRepository certifyRepository;

	@Transactional
	public CertifyResponse sendCertifyCode(CertifyEmailRequest dto) throws AlreadyCertifyException {

		Optional<Certify> found = certifyRepository.findByEmail(dto.getEmail());

		if (found.isPresent() && found.get().getStatus() != null) {
			throw new AlreadyCertifyException();
		}

		String uuid = UUID.randomUUID().toString().split("-")[0];

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(dto.getEmail());
		message.setFrom("no-reply@gmail.com");
		message.setSubject("[app_comunity] 인증코드를 보내드립니다.");
		message.setText("""
					이메일 본인 인증 절차에 따라 인증코드를 보내드립니다.

					인증코드 : #{uuid}
				""".replace("#{uuid}", uuid));
		mailSender.send(message);

		Certify one = new Certify();
		if (found.isPresent()) {
			Certify data = found.get();
			one.setId(data.getId());
			one.setCode(uuid);
			one.setEmail(data.getEmail());
		} else {
			one.setCode(uuid);
			one.setStatus("N");
			one.setEmail(dto.getEmail());
		}

		certifyRepository.save(one);

		return new CertifyResponse("이메일 인증코드가 정상 발급되어있습니다");
	}

	@Transactional
	public CertifyResponse certifySpecificCode(@Valid CertifyCodeRequest req) throws CertifyFailException {

		Certify data = certifyRepository.findByEmail(req.getEmail())
				.orElseThrow(() -> new CertifyFailException("인증코드 발급받은 적이 없습니다."));

		if (!data.getCode().equals(req.getCode())) {
			throw new CertifyFailException("인증코드가 일치하지 않습니다.");
		}

		data.setStatus("Y");

		certifyRepository.save(data);

		return new CertifyResponse("이메일 인증 성공");
	}

}