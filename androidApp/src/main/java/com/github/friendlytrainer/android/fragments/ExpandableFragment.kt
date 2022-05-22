package com.github.friendlytrainer.android.fragments

import android.view.View
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import com.github.friendlytrainer.android.R

open class ExpandableFragment(
    @LayoutRes contentLayoutId: Int,
    private val groupId: Int
) : Fragment(contentLayoutId) {

    class FragmentIdentifierNotValid(message: String) : Exception(message)

    fun flipState(buttonId: Int) {
        val group = view?.findViewById<Group>(groupId)
            ?: throw FragmentIdentifierNotValid("The group ID for the fragment ${this::class} is not valid")
        val (visibility, image) = if (group.visibility == View.VISIBLE) {
            View.GONE to R.drawable.expand_arrow
        } else {
            View.VISIBLE to R.drawable.collapse_arrow
        }
        group.visibility = visibility
        view?.findViewById<Button>(buttonId)?.setCompoundDrawablesWithIntrinsicBounds(0, 0, image, 0)
    }
}