package io.keepcoding.eh_ho.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.keepcoding.eh_ho.repository.Repository

class PostsViewModel(private val repository: Repository) : ViewModel() {

    class PostsViewModelProviderFactory(private val repository: Repository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
            PostsViewModel::class.java -> PostsViewModel(repository) as T
            else -> throw IllegalArgumentException("PostsViewModelProviderFactory can only create instances of the PostsViewModel")
        }
    }

    sealed class State {
        object Default : State()
        object Loading : State()
    }

    sealed class Action {
        object OnPostCreated : Action()
    }

    val state = MutableLiveData<State>()
    val action = MutableLiveData<Action>()

    fun createPost(body: String, topicId: Int) {
        state.postValue(State.Loading)

        repository.createPost(body, topicId) {
            state.postValue(State.Default)
            action.postValue(Action.OnPostCreated)
        }
    }
}