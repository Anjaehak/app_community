package com.company.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.PostWrapper;
import com.company.model.dto.ReReplyWrapper;
import com.company.model.dto.ReplyWrapper;
import com.company.model.dto.post.request.CreatePostRequest;
import com.company.model.dto.post.request.UpdatePostRequest;
import com.company.model.dto.post.response.AllPostsResponse;
import com.company.model.dto.post.response.PostResponse;
import com.company.model.entity.Image;
import com.company.model.entity.Post;
import com.company.model.entity.Recommend;
import com.company.model.entity.Reply;
import com.company.model.entity.User;
import com.company.repository.ImageRepository;
import com.company.repository.PostRepository;
import com.company.repository.RecommendRepository;
import com.company.repository.ReplyRepository;
import com.company.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	@Value("${upload.server}")
	String uploadServer;
	@Value("${upload.basedir}")
	String uploadBaseDir;

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final RecommendRepository likeRepository;

	private final ReplyRepository replyRepository;

	public AllPostsResponse allPosts() {
		List<Post> postLi = postRepository.findAll();

		List<PostWrapper> li = postLi.stream().map(e -> new PostWrapper(e)).toList();

		Long cnt = postRepository.count();

		return new AllPostsResponse(cnt, li);

	}

	public void save(String principal, CreatePostRequest req)
			throws NotExistUserException, IllegalStateException, IOException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		Post post = new Post();
		LocalDateTime currentTime = LocalDateTime.now();
		post.setPostContent(req.getPostContent());
		post.setPostDate(currentTime);
		post.setTitle(req.getTitle());
		post.setPostWriter(user);

		var saved = postRepository.save(post);
		System.out.println(req.getAttaches());
		if (req.getAttaches() != null) { // 파일이 넘어왔다면
			File uploadDirectory = new File(uploadBaseDir + "/feed/" + saved.getId());
			uploadDirectory.mkdirs();

			for (MultipartFile multi : req.getAttaches()) { // 하나씩 반복문 돌면서
				// 어디다가 file 옮겨둘껀지 File 객체로 정의하고
				String fileName = String.valueOf(System.currentTimeMillis());
				String extension = multi.getOriginalFilename().split("\\.")[1];
				File dest = new File(uploadDirectory, fileName + "." + extension);

				multi.transferTo(dest); // 옮기는걸 진행

				// 업로드가 끝나면 DB에 기록
				Image image = new Image();
				// 업로드를 한 곳이 어디냐에 따라서 결정이 되는 값
				image.setImageUrl(uploadServer + "/resource/feed/" + saved.getId() + "/" + fileName + "." + extension);
				image.setPostsId(saved);

				imageRepository.save(image);

			}
		}
	}

	public void update(String principal, UpdatePostRequest req) throws NotExistUserException, NotExistPostException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		System.out.println("user id =" + user.getId());
		Integer id = req.getId();

		Post post = postRepository.findById(id).orElseThrow(() -> new NotExistPostException());
		LocalDateTime currentTime = LocalDateTime.now();
		post.setTitle(req.getTitle());
		post.setPostDate(currentTime);
		post.setPostContent(req.getPostContent());

		postRepository.save(post);

	}

	public void recommendPost(String email, Integer postNumber) throws NotExistUserException, NotExistPostException {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistUserException());
		Post post = postRepository.findById(postNumber).orElseThrow(() -> new NotExistPostException());

		Recommend recommend = Recommend.builder().usersId(user).postsId(post).build();
		likeRepository.save(recommend);

	}

	public void test(String id) throws NumberFormatException, NotExistPostException {
		Post post = postRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new NotExistPostException());

		List<Reply> replyLi = replyRepository.findByPostsId(post);

		Map<Integer, ReplyWrapper> b = new LinkedHashMap<>();

		List<ReReplyWrapper> c = new LinkedList<>();

		ReReplyWrapper asc = new ReReplyWrapper();

	}

	public PostResponse getSpecificPost(Integer postId) throws NotExistPostException {

		Post post = postRepository.findById(postId).orElseThrow(() -> new NotExistPostException());
		var postResponse = new PostResponse();
		postResponse.setId(post.getId());
		postResponse.setImages(post.getImages());
		postResponse.setPostContent(post.getPostContent());
		postResponse.setPostDate(post.getPostDate());
		postResponse.setPostWriter(post.getPostWriter());
		postResponse.setReplies(post.getReplies());
		postResponse.setTitle(post.getTitle());

		return postResponse;
	}

}
