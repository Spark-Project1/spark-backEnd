package com.spark.feed.service;


import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.base.util.FileUtil;
import com.spark.feed.model.Feed;
import com.spark.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class FeedPreprocessor {

    private final FileUtil fileUtil;

    public void uploadProfileImg(MultipartFile uploadFile, Feed feed) {

        if (!uploadFile.isEmpty()) {
            try {
                Map<String, String> map = fileUtil.fileupload(uploadFile, "feed");
                feed.updateFeedURL(map.get("filePath") + "/" + map.get("filesystemName"));
            } catch (Exception e) {
                throw new SparkException(SparkErrorCode.SPARK_999);
            }
        } else {
            feed.updateFeedURL(null);
        }

    }



}
