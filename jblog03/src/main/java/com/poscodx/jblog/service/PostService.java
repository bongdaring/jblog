package com.poscodx.jblog.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public void create(PostVo postVo) {
		postRepository.insert(postVo);
		
	}

	public List<PostVo> findByCategoryNo(Long categoryNo) {
		// TODO Auto-generated method stub
		return postRepository.findByCategoryNo(categoryNo);
	}

	public PostVo findByNo(Long postNo, Long categoryNo) {
		PostVo postVo = new PostVo();
		postVo.setNo(postNo);
		postVo.setCategoryNo(categoryNo);
		return postRepository.findByNo(postVo);
	}

	public void deletePost(Long categoryNo) {
		postRepository.deleteByCategoryNo(categoryNo);
		
	}

	public PostVo findRecentByCategoryNo(Long categoryNo) {
		return postRepository.findRecentByCategoryNo(categoryNo);
	}
	
	public PostVo findRecent(String blogId) {
		return postRepository.findRecent(blogId);
	}

	public PostVo findFirstPost(List<PostVo> postList) {
		PostVo vo = postList.stream().sorted(Comparator.comparing(PostVo::getNo)).findFirst().get();
		System.out.println("vo:"+vo.getNo());
		return postList.stream().sorted(Comparator.comparing(PostVo::getNo)).findFirst().get();
	}


}
