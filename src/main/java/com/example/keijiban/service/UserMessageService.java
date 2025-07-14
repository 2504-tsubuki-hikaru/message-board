package com.example.keijiban.service;

import com.example.keijiban.dto.UserMessageDto;
import com.example.keijiban.repository.UserMessageRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
public class UserMessageService {

    @Autowired
    UserMessageRepository userMessageRepository;

    public List<UserMessageDto> findByUserMessages(String start, String end, String category) throws ParseException {

        String strStartDate;
        String strEndDate;

        if (!isBlank(start)) {
            strStartDate = start + " 00:00:00";
        } else {
            strStartDate = "2022-01-01 00:00:00";
        }

        if (!isBlank(end)) {
            strEndDate = end + " 23:59:59";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strEndDate = dateFormat.format(new Date());
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdFormat.parse(strStartDate);
        Date endDate = sdFormat.parse(strEndDate);

        if (StringUtils.isBlank(category)) {
            List<UserMessageDto> userMessages = userMessageRepository.findByCreatedDateBetween(startDate, endDate);
            return userMessages;
        } else {
            List<UserMessageDto> userMessages = userMessageRepository.findByCreatedDateBetweenAndCategory(startDate, endDate, category);
            return userMessages;
        }
    }
}
