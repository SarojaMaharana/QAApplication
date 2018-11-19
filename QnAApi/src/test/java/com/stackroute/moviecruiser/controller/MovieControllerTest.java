/*package com.stackroute.moviecruiser.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.moviecruiser.constants.Url;
import com.stackroute.moviecruiser.exceptions.MovieAlreadyExistsException;
import com.stackroute.moviecruiser.exceptions.MovieNotFoundException;
import com.stackroute.moviecruiser.model.Movie;
import com.stackroute.moviecruiser.service.impl.MovieServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

	@Autowired
	private transient MockMvc mockMvc;

	@MockBean
	private transient MovieServiceImpl movieImpl;

	@Mock
	private transient Movie movie;

	@Mock
	private transient List<Movie> movieList;

	@Autowired
	@InjectMocks
	private MovieController movieController;

	private String movieJson;
	
	@Before
	public void init() throws JsonProcessingException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(movieController).addFilters(new CorsFilter()).build();
		movieList = new ArrayList<>();
		movie = new Movie(49l, "Gangajal", "Super movie", "/image/i.png", Date.valueOf("2018-10-29"), "12.5", "100");
		movieList.add(movie);
		movie = new Movie(50l, "KICK", "Super Duper movie", "/image/j.png", Date.valueOf("2018-10-29"), "20.5", "200");
		movieList.add(movie);
		ObjectMapper mapper = new ObjectMapper();
		movieJson = mapper.writeValueAsString(movie);
	}

	@Test
	public void saveActionTest() throws MovieAlreadyExistsException, Exception {
		when(movieImpl.saveMovie(movie)).thenReturn(true);
		mockMvc.perform(post(Url.SAVE_MOVIE_API_URL).contentType(MediaType.APPLICATION_JSON).content(movieJson)).andExpect(MockMvcResultMatchers.status().isCreated());
		verify(movieImpl,times(1)).saveMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieImpl);
	}
	
	@Test
	public void saveActionExceptionTest() throws MovieAlreadyExistsException, Exception {
		when(movieImpl.saveMovie(movie)).thenThrow(MovieAlreadyExistsException.class);
		mockMvc.perform(post(Url.SAVE_MOVIE_API_URL).contentType(MediaType.APPLICATION_JSON).content(movieJson)).andExpect(MockMvcResultMatchers.status().isCreated());
		verify(movieImpl,times(1)).saveMovie(Mockito.any(Movie.class));
		verifyZeroInteractions(movieImpl);
	}
	
	@Test
	public void updateMovieTest() throws MovieNotFoundException, Exception {
		movie.setComments("This is comments");
		when(movieImpl.updateMovie(movie)).thenReturn(movieList.get(1));
		mockMvc.perform(post(Url.UPDATE_MOVIE_API_URL).contentType(MediaType.APPLICATION_JSON).content(movieJson)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(movieImpl,times(1)).updateMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieImpl);
	}
	
	@Test
	public void updateMovieExceptionTest() throws MovieNotFoundException, Exception {
		movie.setComments("This is comments");
		when(movieImpl.updateMovie(movie)).thenThrow(MovieNotFoundException.class);
		mockMvc.perform(post(Url.UPDATE_MOVIE_API_URL).contentType(MediaType.APPLICATION_JSON).content(movieJson)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(movieImpl,times(1)).updateMovie(Mockito.any(Movie.class));
		verifyZeroInteractions(movieImpl);
	}
	
	@Test
	public void deleteMovieTest() throws MovieNotFoundException, Exception{
		doNothing().when(movieImpl).deleteMovieById(50l);
		mockMvc.perform(get(Url.DELETE_MOVIE_API_URL,50l)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(movieImpl,times(1)).deleteMovieById(Mockito.any());
		verifyNoMoreInteractions(movieImpl);
	}
	
	@Test
	public void deleteMovieExceptionTest() throws MovieNotFoundException, Exception{
		when(movieImpl.deleteMovieById(50l)).thenThrow(MovieNotFoundException.class);
		mockMvc.perform(get(Url.DELETE_MOVIE_API_URL,50l)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(movieImpl,times(1)).deleteMovieById(Mockito.any());
		verifyZeroInteractions(movieImpl);
	}
	
	@Test
	public void fetchMovieByIdTest() throws MovieNotFoundException, Exception{
		when(movieImpl.getMovieById(50l)).thenReturn(movieList.get(1));
		mockMvc.perform(get(Url.FETCH_MOVIE_BY_ID_URL,50l)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(movieImpl,times(1)).getMovieById(Mockito.any());
		verifyNoMoreInteractions(movieImpl);
	}
	
	@Test
	public void allMovieTest() throws MovieNotFoundException, Exception{
		when(movieImpl.getAllMovies()).thenReturn(null);
		mockMvc.perform(get(Url.FETCH_ALL_MOVIE_API_URL)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(movieImpl,times(1)).getAllMovies();
		verifyNoMoreInteractions(movieImpl);
	}
}
*/