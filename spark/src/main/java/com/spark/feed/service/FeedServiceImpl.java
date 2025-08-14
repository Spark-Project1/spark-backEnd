package com.spark.feed.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.feed.dto.request.CommentRequest;
import com.spark.feed.dto.request.CreateFeedRequest;
import com.spark.feed.dto.request.ReplyCommentRequest;
import com.spark.feed.dto.response.FeedDetailResponse;
import com.spark.feed.dto.response.FeedListResponse;
import com.spark.feed.model.Comment;
import com.spark.feed.model.Feed;
import com.spark.member.service.MemberPreprocessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.util.FileUtil;
import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;
import com.spark.feed.repository.FeedDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedDao feedDao;
    private final FileUtil fileUtil;
    private final FeedPreprocessor feedPreprocessor;


    @Override
    public List<FeedListResponse> feedList() {

        List<Feed> feed = feedDao.feedList();

        return feed.stream()
            .map(FeedListResponse::from)
            .toList();
    }


    @Override
    public FeedDetailResponse feedDetail(int feedNo) {

        Feed feed = feedDao.feedDetail(feedNo);
        List<Comment> commentList = feedDao.feedComment(feedNo);

        return FeedDetailResponse.from(commentList,feed);
    }


    @Override
    public int createFeed(CreateFeedRequest createFeedRequest, MultipartFile uploadFile) {

        Feed tempFeed = Feed.tempFeed(createFeedRequest.getMemId(),createFeedRequest.getFeedContent());

        tempFeed.activeStatus();

        feedPreprocessor.uploadProfileImg(uploadFile, tempFeed);

        int result = feedDao.createFeed(tempFeed);

        if (result <= 0) {
            File file = new File("C:" + tempFeed.getFeedURL());
            if (file.exists()) file.delete();
        }
        return result;

    }


    @Override
    public int deleteFeed(int feedNo) {
        String url = feedDao.searchURL(feedNo);
        if (url == null) {
            return feedDao.deleteFeed(feedNo);
        } else {
            File file = new File("C:" + url);
            if (file.exists()) {
                file.delete();
            }
            return feedDao.deleteFeed(feedNo);
        }

    }


    @Override
    public int comment(CommentRequest commentRequest) {

        Comment comment = Comment.tempComment(commentRequest.getMemId(), commentRequest.getCommentContent(), commentRequest.getFeedNo());

        comment.setMainComment(); // 메인 댓글 설정

        return feedDao.comment(comment);
    }


    @Override
    public int replyComment(ReplyCommentRequest replyCommentRequest) {

        Comment comment = Comment.tempComment(
            replyCommentRequest.getMemId(),
            replyCommentRequest.getComContent(),
            replyCommentRequest.getFeedNo()
        );

        comment.setReplyComment(replyCommentRequest.getComNo());


        return feedDao.replyComment(comment);
    }


}
