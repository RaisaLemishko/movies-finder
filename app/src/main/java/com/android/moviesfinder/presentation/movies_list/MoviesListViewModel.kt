package com.android.moviesfinder.presentation.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.android.moviesfinder.data.db.MovieEntity
import com.android.moviesfinder.data.db.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class MoviesListViewModel
@Inject constructor(
    pager: Pager<Int, MovieEntity>
) : ViewModel() {

    val moviePagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toMovie() }
        }
        .cachedIn(viewModelScope)

}
