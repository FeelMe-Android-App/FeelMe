package com.feelme.feelmeapp.features.dialog.model

data class ButtonStyle(
    val text: String,
    val icon: Int,
    val backgroundColor: Int,
    val onClickListener: () -> Unit,
)