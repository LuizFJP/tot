package com.ddm.steps.firebase.readtogether.ui.services

import com.ddm.steps.firebase.readtogether.ui.api.RetrofitInstance
import com.ddm.steps.firebase.readtogether.ui.dao.UserDAO
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

suspend fun SendFriendInvite(referralCode: String) {
    val userDAO = UserDAO()
    userDAO.addFriendInvite(referralCode)
    val userFriend = userDAO.getUserByReferralCode(referralCode)
    var fmcToken: String = ""
    if  (userFriend?.fmcToken != null) {
        fmcToken = userFriend.fmcToken
    }
    var username = ""
    val user = Firebase.auth.currentUser
    user?.let {
        val thisUser = userDAO.get(it.uid)
        if (thisUser != null) {
            username = thisUser.username.toString()
        }
    }
        RetrofitInstance.api.sendFriendRequestNotification(fmcToken, username)
}