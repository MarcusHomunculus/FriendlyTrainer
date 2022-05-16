package com.github.friendlytrainer.android.fragments

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import com.github.friendlytrainer.android.R

class ExpandableFragment(
    @LayoutRes contentLayoutId: Int,
    private val groupId: Int,
    private val arrowId: Int
) : Fragment(contentLayoutId) {

    class FragmentIdentifierNotValid(message: String) : Exception(message)

    fun flipState() {
        val group = view?.findViewById<Group>(groupId)
            ?: throw FragmentIdentifierNotValid("The group ID for the fragment ${this::class} is not valid")
        val arrow = view?.findViewById<ImageView>(arrowId)
            ?: throw FragmentIdentifierNotValid("The ID for the arrow of fragment ${this::class} is not correct")
        val (visibility, image) = if (group.visibility == View.VISIBLE) {
            View.GONE to R.drawable.expand_arrow
        } else {
            View.VISIBLE to R.drawable.collapse_arrow
        }
        group.visibility = visibility
        arrow.setImageResource(image)
    }
}