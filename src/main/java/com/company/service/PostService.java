package com.company.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.ImageWrapper;
import com.company.model.dto.PostWrapper;
import com.company.model.dto.ReReplyWrapper;
import com.company.model.dto.ReplyWrapper;
import com.company.model.dto.post.request.CreatePostRequest;
import com.company.model.dto.post.request.PostDeleteRequest;
import com.company.model.dto.post.request.UpdatePostRequest;
import com.company.model.dto.post.response.AllPostsResponse;
import com.company.model.dto.reply.request.ReplyDeleteRequest;
import com.company.model.entity.Image;
import com.company.model.entity.Post;
import com.company.model.entity.Reply;
import com.company.model.entity.User;
import com.company.repository.ImageRepository;
import com.company.repository.PostRecommendRepository;
import com.company.repository.PostRepository;
import com.company.repository.ReplyRecommendRepository;
import com.company.repository.ReplyRepository;
import com.company.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

	@Value("${upload.server}")
	String uploadServer;
	@Value("${upload.basedir}")
	String uploadBaseDir;

	private final PostRepository postRepository;
	private final PostRecommendRepository postRecommendRepository;

	private final UserRepository userRepository;
	private final ImageRepository imageRepository;

	private final ReplyRepository replyRepository;
	private final ReplyRecommendRepository replyRecommendRepository;

	private final ReplyService replyService;

	public AllPostsResponse allPosts(Integer page) {

		if (page == null) {
			page = 1;
		}

		Sort sort = Sort.by(Sort.Direction.DESC, "postDate");

		Pageable pageable = PageRequest.of(page - 1, 10, sort);

		List<Post> postLi = postRepository.findAll(pageable).getContent();

		List<PostWrapper> li = postLi.stream().map(e -> new PostWrapper(e)).toList();

		Long cnt = postRepository.count();

		return new AllPostsResponse(cnt, li);

	}

	public void save(String principal, CreatePostRequest req)
			throws NotExistUserException, IllegalStateException, IOException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		Post post = new Post(req, user);
		Post saved = postRepository.save(post);

		if (req.getAttaches() != null) {
			File uploadDirectory = new File(uploadBaseDir + "/post/" + saved.getId());
			uploadDirectory.mkdirs();

			for (MultipartFile multi : req.getAttaches()) {
				String fileName = String.valueOf(System.currentTimeMillis());
				String extension = multi.getOriginalFilename().split("\\.")[1];
				File dest = new File(uploadDirectory, fileName + "." + extension);

				multi.transferTo(dest);

				Image image = new Image();

				image.setImageUrl(uploadServer + "/post/" + saved.getId() + "/" + fileName + "." + extension);
				image.setPostsId(saved);

				imageRepository.save(image);

			}
		}
	}

	public void update(String principal, UpdatePostRequest req) throws NotExistUserException, NotExistPostException {
		userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		Integer id = req.getId();

		Post post = postRepository.findById(id).orElseThrow(() -> new NotExistPostException());

		Post saved = new Post(post, req);

		postRepository.save(saved);

	}

	public PostWrapper getSpecificPost(Integer id) throws NumberFormatException, NotExistPostException {
		Post data = postRepository.findById(id).orElseThrow(() -> new NotExistPostException());
		Post post = new Post(data);
		log.warn("post = {}", post.getReplies());
		Post saved = postRepository.save(post);

//		new PostWrapper(saved);
		List<Image> images = imageRepository.findByPostsId(saved);
		List<ImageWrapper> imageWrappers = images.stream().map(e -> new ImageWrapper(e)).toList();

		if (replyRepository.findByPostsId(post).size() == 0) {
			int recommendCnt = postRecommendRepository.findByPostsId(post).size();

			return new PostWrapper(saved, recommendCnt);
		} else {

			List<Reply> replyDatas = replyRepository.findByPostsId(saved);

			List<Reply> replyLi = replyDatas.stream().filter(t -> t.getParentId() == 0).toList();

			List<ReReplyWrapper> reReplyWrapperLi = new ArrayList<>();
			List<ReplyWrapper> replyWrapperLi = new ArrayList<>();

			for (Reply out : replyLi) {
				log.info("out={}", out);

				for (Reply in : replyDatas) {
					log.info("in={}", in);
					if (out.getId().equals(in.getParentId())) {
						int recommendCnt = replyRecommendRepository.findByRepliesId(in).size();

						reReplyWrapperLi.add(new ReReplyWrapper(in, recommendCnt));
					}
				}
				int recommendCnt = replyRecommendRepository.findByRepliesId(out).size();
				ReplyWrapper replyWrapper = new ReplyWrapper(out, reReplyWrapperLi, recommendCnt);

				replyWrapperLi.add(replyWrapper);
			}

			int recommendCnt = postRecommendRepository.findByPostsId(post).size();

			return new PostWrapper(saved, replyWrapperLi, recommendCnt, imageWrappers);
		}
	}

	public void deleteSpecificPost(String principal, PostDeleteRequest req)
			throws NotExistPostException, NotExistReplyException {
		Post postData = postRepository.findById(req.getId()).orElseThrow(() -> new NotExistPostException());

		List<Reply> replyDatas = postData.getReplies();

		for (Reply reply : replyDatas) {

			replyService.specificPostReplyDelete(principal, new ReplyDeleteRequest(reply.getId()));
		}

		postRepository.delete(postData);
	}
}
