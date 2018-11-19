package com.stackroute.moviecruiser.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.moviecruiser.exceptions.MovieAlreadyExistsException;
import com.stackroute.moviecruiser.exceptions.MovieNotExistsException;
import com.stackroute.moviecruiser.exceptions.MovieNotFoundException;
import com.stackroute.moviecruiser.model.Movie;
import com.stackroute.moviecruiser.repository.MovieRepository;

@RunWith(SpringRunner.class)
public class MovieServiceImplTest {

	@Mock
	private transient MovieRepository repository;

	private transient Movie movie;

	@InjectMocks
	private transient MovieServiceImpl serviceImpl;

	private transient Optional<Movie> options;

	private List<Movie> movieList = new ArrayList<Movie>();

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		movie = new Movie(50l, "KICK", "Super movie", "/image/i.png", Date.valueOf("2018-10-29"), "12.5", "100");
		options = Optional.of(movie);
		movieList.add(movie);
	}

	@Test
	public void mockTest() {
		assertNotNull(" Moview object has been set up properly", movie);
	}

	@Test(expected = MovieAlreadyExistsException.class)
	public void saveMovieFailureTest() throws MovieAlreadyExistsException {
		when(repository.save(movie)).thenReturn(movie);
		when(repository.findById(50l)).thenReturn(options);
		boolean flag = serviceImpl.saveMovie(movie);
		assertFalse(" save failed ", flag);
		verify(repository, times(1)).findById(movie.getId());
	}

	@Test
	public void saveMovieSuccessTest() throws MovieAlreadyExistsException {
		when(repository.save(movie)).thenReturn(movie);
		boolean flag = serviceImpl.saveMovie(movie);
		assertTrue(" Should return false ", flag);
		verify(repository, times(1)).save(movie);
		verify(repository, times(1)).findById(movie.getId());
	}

	@Test
	public void fetchAllMovieTest() {
		when(repository.findAll()).thenReturn(movieList);
		final List<Movie> movieServList = serviceImpl.getAllMovies();
		assertEquals(movieList, movieServList);
		verify(repository, times(1)).findAll();
	}

	@Test
	public void deleteMovieByIdTest() throws MovieNotFoundException {
		when(repository.findById(50l)).thenReturn(options);
		doNothing().when(repository).delete(movie);
		boolean flag = serviceImpl.deleteMovieById(50l);
		assertTrue(" Should return false ", flag);
		//verify(repository, times(1)).delete(movie);
		verify(repository, times(1)).findById(movie.getId());
	}

	@Test
	public void updateMovieByIdTest() throws MovieNotExistsException {
		when(repository.findById(movie.getId())).thenReturn(options);
		when(repository.save(movie)).thenReturn(movie);
		movie.setComments("Hi I AM HERE");
		final Movie updatedOne = serviceImpl.updateMovie(movie);
		assertEquals(" Checking movie ", "Hi I AM HERE", updatedOne.getComments());
		verify(repository, times(1)).save(movie);
		verify(repository, times(1)).findById(movie.getId());
	}

	@Test
	public void getMovieByIdTest() throws MovieNotFoundException {
		when(repository.findById(movie.getId())).thenReturn(options);
		final Movie movieOne = serviceImpl.getMovieById(movie.getId());
		assertEquals(" Checking movie equals", movieOne, movie);
		verify(repository, times(1)).findById(movie.getId());
	}

}