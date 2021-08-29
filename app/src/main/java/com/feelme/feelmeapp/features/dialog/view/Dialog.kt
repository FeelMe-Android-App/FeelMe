package com.feelme.feelmeapp.features.dialog.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feelme.feelmeapp.databinding.FragmentDialogBinding
import com.feelme.feelmeapp.features.dialog.adapter.EmojiListAdapter
import com.feelme.feelmeapp.features.dialog.usecase.ButtonStyle
import com.feelme.feelmeapp.features.dialog.usecase.DialogData
import com.google.android.material.button.MaterialButton

class Dialog(var params: DialogData) : DialogFragment() {
    private var binding: FragmentDialogBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
            with(binding) {
                this?.fabBackAction?.visibility = View.GONE
                this?.btPersonalized?.setBackgroundColor(ContextCompat.getColor(requireContext(), (params.button as ButtonStyle).backgroundColor))
                this?.btPersonalized?.text = (params.button as ButtonStyle).text
                (this?.btPersonalized as MaterialButton).setIconResource((params.button as ButtonStyle).icon)
                this.btPersonalized.setOnClickListener {
                    (params.button as ButtonStyle).onClickListener()
                }
            }
        }

        if(!params.emojiList.isNullOrEmpty()) {
            binding?.btPersonalized?.visibility = View.GONE
            binding?.fabBackAction?.visibility = View.GONE
            binding?.tvContent?.visibility = View.GONE
            binding?.rvEmojiList?.visibility = View.VISIBLE

            val emojiItens = params.emojiList
            binding?.rvEmojiList?.adapter = emojiItens?.let { EmojiListAdapter(it) }
            binding?.rvEmojiList?.layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }

        binding?.ivDestaqImage?.setImageResource(params.image)
        binding?.fabBackAction?.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}