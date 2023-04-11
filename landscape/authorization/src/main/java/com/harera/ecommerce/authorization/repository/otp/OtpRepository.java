package com.harera.ecommerce.authorization.repository.otp;

import com.harera.ecommerce.authorization.model.otp.OTP;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends CrudRepository<OTP, String> {

}
