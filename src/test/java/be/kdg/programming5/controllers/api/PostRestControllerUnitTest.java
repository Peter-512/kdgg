package be.kdg.programming5.controllers.api;

import be.kdg.programming5.model.Post;
import be.kdg.programming5.service.posts.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostRestControllerUnitTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Test
	@WithMockUser (roles = {"MOD", "ADMIN"})
	public void deletePostByModOrAdminAllowed() throws Exception {
		var id = 1L;
		var post = mock(Post.class);

		doNothing().when(postService).deletePost(id);
		given(postService.getPost(id)).willReturn(Optional.of(post));

		mockMvc.perform(delete("/api/posts/{id}", id)
				       .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				       .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				       .with(csrf()))
		       .andExpect(status().isNoContent());

		verify(postService).getPost(id);
		verify(postService).deletePost(id);
	}

	@Test
	@WithMockUser (roles = "USER")
	public void deletePostByUserForbidden() throws Exception {
		var id = 1L;
		var post = mock(Post.class);

		doNothing().when(postService).deletePost(id);
		given(postService.getPost(id)).willReturn(Optional.of(post));

		mockMvc.perform(delete("/api/posts/{id}", id)
				       .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				       .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				       .with(csrf()))
		       .andExpect(status().isForbidden());

		verify(postService, never()).getPost(id);
		verify(postService, never()).deletePost(id);
	}

	@Test
	@WithMockUser (roles = {"MOD", "ADMIN"})
	void deleteNonExistingPostByModOrAdminReturnsNotFound() throws Exception {
		var id = 1L;
		doNothing().when(postService).deletePost(id);
		given(postService.getPost(id)).willReturn(Optional.empty());

		mockMvc.perform(delete("/api/posts/{id}", id)
				       .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				       .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				       .with(csrf()))
		       .andExpect(status().isNotFound());

		verify(postService).getPost(id);
		verify(postService, never()).deletePost(id);
	}
}
