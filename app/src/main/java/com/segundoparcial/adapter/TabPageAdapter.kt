package com.segundoparcial.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.segundoparcial.view.HomeFragment
import com.segundoparcial.view.ListFragment
import com.segundoparcial.view.RegisterFragment

class TabPageAdapter(
    framentManager :FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter (framentManager , lifecycle)
{
    override fun getItemCount(): Int  = 3
    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1 -> RegisterFragment()
            2 -> ListFragment()
            else -> HomeFragment()
        }
    }


}