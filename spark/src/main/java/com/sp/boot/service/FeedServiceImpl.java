package com.sp.boot.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sp.boot.dao.FeedDao;
import com.sp.boot.dto.FeedDto;
import com.sp.boot.util.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{
	
	private final FeedDao feedDao;
	private final FileUtil fileUtil;
	
	
	@Override
	public List<FeedDto> feedList() {
		return feedDao.feedList();
	}


	@Override
	public Map<String, Object> feedDetail(int feedNo) {
		
		FeedDto feedDto = feedDao.feedDetail(feedNo);
		List<String> comDto = feedDao.feedComment(feedNo);
		
		Map<String,Object> map = new HashMap<>();
		map.put("feedDto", feedDto);
		map.put("comDto", comDto);
		
		return map;
	}


	@Override
	public int createFeed(FeedDto feed,MultipartFile uploadFile) {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		feed.setFeedId(userId);
	    if (!uploadFile.isEmpty()) {
	        Map<String, String> map = fileUtil.fileupload(uploadFile, "feed");
	        String filePath = map.get("filePath") + "/" + map.get("filesystemName");
	        feed.setFeedURL(filePath);
	        int result = feedDao.createFeed(feed);
	        
	        if(result > 0) {
	        	return 1;	        	
	        }else {
	            File file = new File("C:" + filePath);
	            if (file.exists()) file.delete();
	        	return 0;
	        }
	        
	    } else {
	    	feed.setFeedURL(null);
	        return feedDao.createFeed(feed);

	    }
	}


	@Override
	public int deleteFeed(int feedNo) {
		
		String url = feedDao.searchURL(feedNo);
		
		if(url == null) {
			return feedDao.deleteFeed(feedNo);
		}else {
			String filePath = "C:" + url;
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			return feedDao.deleteFeed(feedNo);
		}
		
	}
	
	
	

}
