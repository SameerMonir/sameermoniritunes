package com.example.sameermoniritunes.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sameermoniritunes.fragments.MusicFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {MusicFragment.newInstance(MusicFragment.CLASSIC)}
            1 -> {MusicFragment.newInstance(MusicFragment.ROCK)}
            2 -> {MusicFragment.newInstance(MusicFragment.POP)}
            else -> {throw Resources.NotFoundException("Tab not found")}
        }
    }

    override fun getItemCount() = 3
}