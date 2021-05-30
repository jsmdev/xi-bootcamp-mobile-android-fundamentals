package io.keepcoding.eh_ho.model

sealed class LogIn {
    data class Success(val userName: String) : LogIn()
    data class Error(val error: String) : LogIn()
}

data class Topic(
    val id: Int,
    val title: String,
    val lastPosterUsername: String,
    val postsCount: Int,
    val replyCount: Int,
    val likeCount: Int,
    val pinned: Boolean,
    val bumped: Boolean
)