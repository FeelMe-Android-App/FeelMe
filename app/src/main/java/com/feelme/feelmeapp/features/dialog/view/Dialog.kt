package com.feelme.feelmeapp.features.dialog.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.feelme.feelmeapp.databinding.FragmentDialogBinding
import com.feelme.feelmeapp.features.dialog.adapter.EmojiListAdapter
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.feelme.feelmeapp.features.dialog.usecase.EmojiList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.feelme.feelmeapp.features.dialog.usecase.ButtonStyle
import com.feelme.feelmeapp.firebase.UserProfile
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FacebookAuthProvider


class Dialog(private var params: DialogData) : DialogFragment() {
    private var binding: FragmentDialogBinding? = null
    private lateinit var callbackManager: CallbackManager
    private lateinit var signingFacebookCallback: FacebookCallback<LoginResult>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        signingFacebookCallback = object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.let { handleFacebookAccessToken(result.accessToken) }
            }

            override fun onCancel() {
                Log.i("facebookLogin", "Teste1")
            }

            override fun onError(error: FacebookException?) {
                Log.i("facebookLogin", error?.message.toString())
            }
        }

        binding = FragmentDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        if(params.title.isNullOrEmpty()) binding?.tvTitle?.visibility = View.GONE
        else binding?.tvTitle?.text = params.title

        if(params.subtitle.isNullOrEmpty()) binding?.tvSubtitle?.visibility = View.GONE
        else binding?.tvSubtitle?.text = params.subtitle

        if(params.content.isNullOrEmpty()) binding?.tvContent?.visibility = View.GONE
        else binding?.tvContent?.text = params.content

        if(params.button === null) binding?.btPersonalized?.visibility = View.GONE
        else {
            binding?.let {
                LoginManager.getInstance().registerCallback(callbackManager, signingFacebookCallback)
                it.fabBackAction.visibility = View.GONE
                it.btPersonalized.setBackgroundColor(ContextCompat.getColor(requireContext(), (params.button as ButtonStyle).backgroundColor))
                it.btPersonalized.text = (params.button as ButtonStyle).text
                (it.btPersonalized as MaterialButton).setIconResource((params.button as ButtonStyle).icon)
                it.btPersonalized.setOnClickListener { view ->
                    it.btPersonalized.isEnabled = false
                    LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
                }
            }
        }

        if(!params.emojiList.isNullOrEmpty()) {
            binding?.let {
                it.btPersonalized.visibility = View.GONE
                it.fabBackAction.visibility = View.GONE
                it.tvContent.visibility = View.GONE
                it.rvEmojiList.visibility = View.VISIBLE

                val emojiList = params.emojiList
                it.rvEmojiList.adapter = emojiList?.let { emoji -> EmojiListAdapter((emoji as List<*>).filterIsInstance<EmojiList>(), this) }
                it.rvEmojiList.layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            }
        }

        binding?.let {
            it.ivDestaqImage.setImageResource(params.image)
            it.fabBackAction.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
            } else {
                Toast.makeText(context, "Login com facebook falhou", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            UserProfile.updateProfile()
            if(params.button !== null) this.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}