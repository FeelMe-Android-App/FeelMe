package com.feelme.feelmeapp.utils

import MoodList
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.model.EmojiCategory

class ConstantApp {

    object Api {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_TOKEN_KEY = "api_key"
        const val API_TOKEN = "3fdab48e2bddf5d597050debe64abb1c"
        const val QUERY_PARAM_LANGUAGE_KEY = "language"
        const val QUERY_PARAM_LANGUAGE_VALUE = "pt-BR"
        const val QUERY_PARAM_WATCH_REGION = "watch_region"
        const val QUERY_PARAM_WATCH_REGION_VALUE = "BR"
        const val QUERY_PARAM_REGION_LABEL = "3fdab48e2bddf5d597050debe64abb1c"
    }

    object emojis {
        val emojiList: List<MoodList> = listOf(
            MoodList(R.drawable.ic_impressed, "Impressionado", listOf(878)),
            MoodList(R.drawable.ic_comedy, "Engraçado", listOf(35)),
            MoodList(R.drawable.ic_sad, "Triste", listOf(18)),
            MoodList(R.drawable.ic_shocked, "Chocado", listOf(27)),
            MoodList(R.drawable.ic_afflicted, "Aflito", listOf(9648)),
        )
    }

    object categories {
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