package com.stackroute.moviecruiser.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.moviecruiser.model.Movie;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class MovieRepositoryTest {

	@Autowired
	private transient MovieRepository movieRepository;
	
	private transient Movie movie;
	
	@Before
	public void init() {
		movie = new Movie(50l, "KICK", "Super movie", "/image/i.png", Date.valueOf("2018-10-29"), "12.5", "100");
	}
	
	@Test
	public void deleteMovieTest() {
		movieRepository.save(movie);
		movieRepository.deleteById(50l);
		assertEquals(Optional.empty(), movieRepository.findById(50l));
	}
	
	@Test
	public void getMovieTest() {
		movieRepository.save(movie);
		Optional<Movie> optional= movieRepository.findById(50l);
		assertEquals("KICK", optional.get().getName());
	}
	
	@Test
	public void fetchAllMovieTest() {
		List<Movie> mvList=movieRepository.findAll();
		assertTrue(mvList.isEmpty());
	}
	
	@Test
	public void updateMovieTest() {
		movieRepository.save(movie);
		final Movie m= movieRepository.getOne(50l);
		m.setComments("Super Super Movie");
		movie = movieRepository.save(movie);
		assertEquals(movie.getComments(), m.getComments());
	}

	
	@Test
	public void saveMovieTest() {
		movieRepository.save(movie);
		final Movie m= movieRepository.getOne(50l);
		assertEquals(movie.getId(), m.getId());
	}
	

}
