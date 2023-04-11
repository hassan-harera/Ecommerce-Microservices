package com.harera.ecommerce.authorization.repository.otp;

import com.harera.ecommerce.authorization.model.otp.OtpTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpTransactionRepository extends JpaRepository<OtpTransaction, Long> {
}
