package io.keepcoding.eh_ho.post


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.databinding.ActivityCreatePostBinding
import io.keepcoding.eh_ho.di.DIProvider

class CreatePostActivity : AppCompatActivity() {

    companion object {
        private const val ARG_TOPIC_ID = "TOPIC_ID"

        @JvmStatic
        fun createIntent(context: Context, topicId: Int) =
            Intent(context, CreatePostActivity::class.java).apply {
                putExtra(ARG_TOPIC_ID, topicId)
            }
    }

    private val binding: ActivityCreatePostBinding by lazy {
        ActivityCreatePostBinding.inflate(
            layoutInflater
        )
    }

    private val vm: PostsViewModel by viewModels { DIProvider.postsViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            buttonCreatePost.setOnClickListener { createPost() }

            vm.state.observe(this@CreatePostActivity) {
                when (it) {
                    PostsViewModel.State.Default -> viewLoading.root.visibility = View.GONE
                    PostsViewModel.State.Loading -> viewLoading.root.visibility = View.VISIBLE
                }
            }

            vm.action.observe(this@CreatePostActivity) {
                when (it) {
                    PostsViewModel.Action.OnPostCreated -> onPostCreated()
                }
            }
        }
    }

    private fun createPost() {
        if (checkData()) {
            val body = binding.inputBody.text.toString()
            val topicId = intent.getIntExtra(ARG_TOPIC_ID, 0)

            vm.createPost(body, topicId)
        }
    }

    private fun checkData(): Boolean {
        with(binding) {
            var isValid = true

            val body = inputBody.text.toString()
            if (body.isBlank()) {
                isValid = false
                layoutBody.apply {
                    error = getString(R.string.error_field_empty)
                    isErrorEnabled = true
                }
            } else {
                layoutBody.apply {
                    error = null
                    isErrorEnabled = false
                }
            }

            return isValid
        }
    }

    private fun onPostCreated() {
        Toast.makeText(this, R.string.post_created, Toast.LENGTH_SHORT).show()
        finish()
    }
}