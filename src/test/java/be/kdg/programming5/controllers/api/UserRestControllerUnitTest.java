package be.kdg.programming5.controllers.api;

import be.kdg.programming5.controllers.api.dtos.UpdatedUserDTO;
import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;
import be.kdg.programming5.service.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerUnitTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser
	void updateUserShouldUpdateTheNameAndRoleAndBirthdate() throws Exception {
		var id = 1L;
		var name = "name";
		var birthDate = LocalDate.of(2000, 1, 1);
		var role = Role.USER;
		var user = mock(User.class);

		given(userService.getUser(id)).willReturn(Optional.of(user));
		given(userService.updateUser(id, name, birthDate, role)).will(invocation -> {
			user.setName(name);
			user.setBirthdate(birthDate);
			user.setRole(role);
			return user;
		});

		mockMvc.perform(put("/api/users/{id}", id)
				       .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				       .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				       .content(objectMapper.writeValueAsString(new UpdatedUserDTO(name, birthDate, role)))
				       .with(csrf()))
		       .andExpect(status().isOk());

		verify(userService).updateUser(id, name, birthDate, role);
		verify(user).setName(name);
		verify(user).setBirthdate(birthDate);
		verify(user).setRole(role);
	}
}
