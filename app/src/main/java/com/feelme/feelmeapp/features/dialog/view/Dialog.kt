package com.feelme.feelmeapp.features.dialog.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.feelme.feelmeapp.databinding.FragmentDialogBinding

class Dialog(var title: String? = null, var subTitle: String? = null, var content: String? = null, var image: Int, buttonAction: Unit? = null) : DialogFragment() {
    private var binding: FragmentDialogBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    private fun setupView(view: View) {
        if(title.isNullOrEmpty()) binding?.tvTitle?.visibility = View.GONE
        else binding?.tvTitle?.text = title

        if(subTitle.isNullOrEmpty()) binding?.tvSubtitle?.visibility = View.GONE
        else binding?.tvSubtitle?.text = subTitle

        if(content.isNullOrEmpty()) binding?.tvContent?.visibility = View.GONE
        else binding?.tvContent?.text = content

        binding?.ivDestaqImage?.setImageResource(image)
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