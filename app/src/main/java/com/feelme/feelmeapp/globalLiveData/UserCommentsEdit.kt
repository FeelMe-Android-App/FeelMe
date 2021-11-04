package com.feelme.feelmeapp.globalLiveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object UserCommentsEdit {
    private val editing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEditing: LiveData<Boolean>
        get() = editing

    private val selectedItens: MutableList<String> = mutableListOf()

    fun isEditing(status: Boolean) {
        editing.postValue(status)
    }

    fun addItem(id: String) {
        selectedItens.add(id)
    }

    fun removeItem(id: String) {
        selectedItens.remove(id)
    }

    fun isSelected(id: String): Boolean {
        val exists = selectedItens.find { it == id }
        return exists !== null
    }

    fun getSelectedItens() = selectedItens.toList()

    fun resetList() = selectedItens.clear()
}