package com.example.chatapp.Data

data class User(
    var uid: String? = null,
    var FirstName: String? = null,
    var LastName: String? = null,
    var Location: String? = null,
    var Email: String? = null,
    var Password: String? = null,
    var posts: List<Post>? = null,
    val ImageProfile: String? = null,
    var Followers: Int? = null,
    var NumberPosts: Int? = null,
    var Following: Int? = null,
)
{
    // No-argument constructor
    constructor() : this(null, null, null,null, null, null,null, null, null,null, null)
}
