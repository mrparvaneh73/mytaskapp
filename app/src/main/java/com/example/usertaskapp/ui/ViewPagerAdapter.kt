package com.example.usertaskapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.usertaskapp.ui.home.doing.DoingFragment
import com.example.usertaskapp.ui.home.done.DoneFragment
import com.example.usertaskapp.ui.home.all.AllFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return AllFragment()
            1 -> return DoingFragment()
            2 -> return DoneFragment()
            else -> return AllFragment()
        }

    }
}
