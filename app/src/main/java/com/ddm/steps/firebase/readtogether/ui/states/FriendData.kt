package com.ddm.steps.firebase.readtogether.ui.states

import com.google.firebase.firestore.PropertyName

data class FriendData(
    @PropertyName("referral_code") var referralCode: String? = null,
    @PropertyName("username") var username: String? = null,
    @PropertyName("profile_photo_url") var profilePhotoUrl: String = "",
)