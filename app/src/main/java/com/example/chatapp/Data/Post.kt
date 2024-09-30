package com.example.chatapp.Data


data class Post (
    var Title: String? = null,
    var Description: String? = null,
    var LikeCount: String? = null,
    var CommnetCount: String? = null,
    var Images: List<String>? = null
)