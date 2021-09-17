package com.feelme.feelmeapp.features.dialog.usecase

data class EmojiList (val emoji: Int, val feeling: String, val closeDialog: Boolean = false, val onClickListener: () -> Unit)