package com.feelme.feelmeapp.features.dialog.usecase

data class DialogData(
    val title: String? = null,
    val subtitle: String? = null,
    val content: String? = null,
    val image: Int,
    val button: ButtonStyle? = null,
    val emojiList: List<EmojiList>? = null,
)