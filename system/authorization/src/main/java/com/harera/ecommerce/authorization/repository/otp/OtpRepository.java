package com.harera.ecommerce.authorization.repository.otp;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.harera.ecommerce.authorization.model.otp.OTP;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends CrudRepository<OTP, String> {

}
