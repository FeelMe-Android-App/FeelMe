package com.feelme.feelmeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.feelme.feelmeapp.databinding.ActivityMainBinding
import com.feelme.feelmeapp.features.dialog.usecase.ButtonStyle
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.feelme.feelmeapp.features.dialog.usecase.EmojiList
import com.feelme.feelmeapp.features.dialog.view.Dialog
import com.feelme.feelmeapp.features.search.view.SearchActivity
import com.feelme.feelmeapp.globalLiveData.UserProfile
import com.feelme.feelmeapp.utils.ConstantApp.Emojis.emojiList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val currentUser = UserProfile.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.fragmentNavHost)
        setupWithNavController(binding.bottomNavigationView, navController)
        val emojiList: List<EmojiList> = emojiList.map {
            EmojiList(it.icon, it.name) {
                openMoodFragment(it.name)
            }
        }
        binding.bottomNavigationView.menu.getItem(1).setOnMenuItemClickListener {
            if(currentUser.value == null || currentUser.value?.logged == false) {
                showLoginFacebookDialog()
            }
            else {
                findNavController(R.id.fragmentNavHost).navigate(R.id.feedFragment)
                binding.bottomNavigationView.menu.getItem(1).isChecked = true
            }
            true
        }
        binding.bottomNavigationView.menu.getItem(2).setOnMenuItemClickListener {
            Dialog(DialogData(
                title = "O que assistir?",
                subtitle = "Escolha como voc?? quer se sentir ap??s assistir",
                image = R.drawable.ic_watched_outlined,
                emojiList = emojiList
            )).show(this.supportFragmentManager, "CustomDialog")
            true
        }
        binding.bottomNavigationView.menu.getItem(3).setOnMenuItemClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
            true
        }
    }

    private fun openMoodFragment(feeling: String) {
        val arguments = Bundle().apply {
            putString(MOOD_CONST, feeling)
        }

        findNavController(R.id.fragmentNavHost).navigate(R.id.whatToWatchFragment, arguments)
        binding.bottomNavigationView.menu.getItem(2).isChecked = true
    }

    private fun showLoginFacebookDialog() {
        Dialog(
            DialogData(
                title = "Entre",
                subtitle = "Fa??a login com seu Facebook para acessar esse e outros recursos.",
                image = R.drawable.ic_signup,
                button = ButtonStyle("Logar com Facebook",R.drawable.ic_facebook,R.color.facebook_bt) {
                    Log.i("ButtonAction","Teste de A????o Personalizada")
                }
            )
        ).show(supportFragmentManager, "LoginDialog")
    }

    fun restartMood() {
        binding.bottomNavigationView.selectedItemId = R.id.whatToWatchFragment
    }

    companion object {
        const val MOOD_CONST = "mood"
    }
}