package com.spark.base.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SparkKeyConfig {

    @Value("${spark.secret.key}")
    private String secretKey;
}
