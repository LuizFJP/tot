package com.ddm.steps.firebase.readtogether.ui.states

import com.google.firebase.firestore.PropertyName

data class UserUiState (
    @get:PropertyName("uid")
    @set:PropertyName("uid")
    var uid: String = "",

    @get:PropertyName("username")
    @set:PropertyName("username")
    var username: String? = null,

    @get:PropertyName("referral_code")
    @set:PropertyName("referral_code")
    var referralCode: String = "",

    @get:PropertyName("friends")
    @set:PropertyName("friends")
    var friends: MutableList<FriendData>? = null,

    @get:PropertyName("profile_photo_url")
    @set:PropertyName("profile_photo_url")
    var profilePhotoUrl: String = "",

    @get:PropertyName("friends_invite")
    @set:PropertyName("friends_invite")
    var friendsInvite: MutableList<FriendData>? = null,

    @get:PropertyName("fmc_token")
    @set:PropertyName("fmc_token")
    var fmcToken: String = ""
)
