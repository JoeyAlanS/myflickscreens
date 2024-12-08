// ApiService.kt
package com.example.myflickscreens.network

import com.example.myflickscreens.ui.movie.MovieDetailsResponse
import com.example.myflickscreens.ui.movie.MovieResponse
import com.example.myflickscreens.utils.APIConstants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(APIConstants.POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = APIConstants.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): MovieResponse

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey: String = APIConstants.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = APIConstants.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = APIConstants.API_KEY
        ,@Query("language") language: String = "pt-BR"
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = APIConstants.API_KEY,
        @Query("query") query: String,
        @Query("language") language: String = "pt-BR"
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = APIConstants.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): MovieDetailsResponse
    
}
