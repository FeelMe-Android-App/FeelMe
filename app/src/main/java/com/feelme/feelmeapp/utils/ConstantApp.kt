package com.feelme.feelmeapp.utils

import MoodList
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.model.EmojiCategory

class ConstantApp {

    object Api {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_TOKEN_KEY = "api_key"
        const val API_TOKEN = "b5be49ab0adbe0f2875221897ecc4526"
        const val QUERY_PARAM_LANGUAGE_KEY = "language"
        const val QUERY_PARAM_LANGUAGE_VALUE = "pt-BR"
        const val QUERY_PARAM_WATCH_REGION = "watch_region"
        const val QUERY_PARAM_WATCH_REGION_VALUE = "BR"
        const val BASE_URL_FEELME = "https://feelme-app.herokuapp.com/"
    }

    object Emojis {
        val emojiList: List<MoodList> = listOf(
            MoodList(R.drawable.ic_happy, "Feliz", listOf(10749,14,28,12)),
            MoodList(R.drawable.ic_in_love, "Apaixonado", listOf(10749)),
            MoodList(R.drawable.ic_shocked, "Chocado", listOf(878,9648,80)),
            MoodList(R.drawable.ic_sad, "Comovido", listOf(18,10751)),
            MoodList(R.drawable.ic_comedy, "Rolando de Rir", listOf(35)),
            MoodList(R.drawable.ic_fear, "Assombrado", listOf(27)),
        )
    }

    object Categories {
        val icons: List<EmojiCategory> = listOf(
            EmojiCategory("Ação", 0),
            EmojiCategory("Aventura", 0),
            EmojiCategory("Animação", 0),
            EmojiCategory("Comédia", 0),
            EmojiCategory("Crime", 0),
            EmojiCategory("Documentário", 0),
            EmojiCategory("Drama", 0),
            EmojiCategory("Família", 0),
            EmojiCategory("Fantasia", 0),
            EmojiCategory("História", 0),
            EmojiCategory("Terror", 0),
            EmojiCategory("Música", 0),
            EmojiCategory("Mistério", 0),
            EmojiCategory("Romance", 0),
            EmojiCategory("Ficção científica", 0),
            EmojiCategory("Cinema TV", 0),
            EmojiCategory("Thriller", 0),
            EmojiCategory("Guerra", 0),
            EmojiCategory("Faroeste", 0),
        )
    }
}