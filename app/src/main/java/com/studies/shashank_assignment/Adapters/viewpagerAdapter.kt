package com.studies.shashank_assignment.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.studies.shashank_assignment.Fragments.MCPFragment
import com.studies.shashank_assignment.Fragments.PIPFragment


class viewpagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = mutableListOf(
        MCPFragment(),
        PIPFragment()
    )

    private val fragmentTitles = mutableListOf(
        "MCP",
        "PIP"
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
        notifyItemInserted(fragments.size - 1)
    }

    fun getTitle(position: Int): String = fragmentTitles[position]
}
