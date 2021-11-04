package myproject.demo.updateTime.service;

import lombok.Value;

import java.time.LocalDateTime;


@Value
public class UpdateTimeDto {
    Long id;
    LocalDateTime updateTime;
}
