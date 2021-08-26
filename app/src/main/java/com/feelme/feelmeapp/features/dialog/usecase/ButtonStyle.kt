package com.feelme.feelmeapp.features.dialog.usecase

data class ButtonStyle(
    val text: String,
    val icon: Int,
    val backgroundColor: Int,
    val onClickListener: () -> Unit,
)