package io.keepcoding.eh_ho.network

import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RequestBuilder(private val baseUrl: String, private val apiKey: String) {

    fun signInRequest(userName: String): Request = createGetRequest(signInEndpoint(userName))

    fun signUpRequest(username: String, email: String, password: String): Request =
        createPostRequest(
            signUpEndpoint(),
            signUpBodyRequest(username, email, password)
        )

    fun topicsRequest(): Request = createGetRequest(topicsEndpoint())

    fun createPostRequest(body: String, topicId: Int, username: String): Request {
        val requestBody = createPostBodyRequest(
            body = body,
            topicId = topicId
        )

        return createAuthenticatedPostRequest(username, createPostEndpoint(), requestBody)
    }

    private fun signInEndpoint(userName: String): HttpUrl = buildHttpUrl("users/$userName.json")
    private fun signUpEndpoint(): HttpUrl = buildHttpUrl("users")
    private fun topicsEndpoint(): HttpUrl = buildHttpUrl("latest.json")
    private fun createPostEndpoint(): HttpUrl = buildHttpUrl("posts.json")

    private fun createAuthenticatedPostRequest(
        username: String,
        httpUrl: HttpUrl,
        body: RequestBody,
        transform: Request.Builder.() -> Unit = { }
    ): Request = createPostRequest(httpUrl, body) {
        header("Api-Key", apiKey)
        header("Api-Username", username)
        transform()
    }

    private fun createPostRequest(
        httpUrl: HttpUrl,
        body: RequestBody,
        transform: Request.Builder.() -> Unit = { },
    ): Request = createRequest(httpUrl) {
        post(body)
        header("Content-Type", "application/json")
        header("Accept", "application/json")
        transform()
    }

    private fun createGetRequest(httpUrl: HttpUrl): Request =
        createRequest(httpUrl, Request.Builder::get)

    private fun createRequest(
        httpUrl: HttpUrl,
        transform: Request.Builder.() -> Unit = { }
    ): Request = Request.Builder()
        .url(httpUrl)
        .apply(transform)
        .build()

    private fun buildHttpUrl(pathSegments: String): HttpUrl =
        HttpUrl.Builder()
            .scheme("https")
            .host(baseUrl)
            .addPathSegments(pathSegments)
            .build()

    companion object {
        private val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

        private fun signUpBodyRequest(
            username: String,
            email: String,
            password: String
        ): RequestBody = JSONObject()
            .put("name", username)
            .put("username", username)
            .put("email", email)
            .put("password", password)
            .put("active", true)
            .put("approved", true)
            .toString()
            .toRequestBody(JSON)

        private fun createPostBodyRequest(
            body: String,
            topicId: Int
        ): RequestBody = JSONObject()
            .put("raw", body)
            .put("topic_id", topicId)
            .toString()
            .toRequestBody(JSON)
    }
}