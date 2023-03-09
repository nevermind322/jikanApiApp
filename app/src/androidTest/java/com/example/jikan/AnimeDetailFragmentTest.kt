package com.example.jikan

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.jikan.data.AnimeInfo
import com.example.jikan.ui.fragments.AnimeDetailFragment
import com.example.jikan.ui.fragments.AnimeDetailFragmentArgs

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@MediumTest
@RunWith(AndroidJUnit4::class)
class AnimeDetailFragmentTest {

    @Test
    fun expandButtonNotVisibleWhenSynopsisIsSmallEnough() {

        val infoWithSmallSynopsis = AnimeInfo("Anime", "rick_roll", "Ha-ha")
        val arg = AnimeDetailFragmentArgs(infoWithSmallSynopsis).toBundle()
        launchFragmentInContainer<AnimeDetailFragment>(arg, R.style.Theme_Jikan)
        Thread.sleep(10000)
    }

    @Test
    fun expandButtonVisibleWhenSynopsisIsBigEnough() {

        val infoWithSmallSynopsis = AnimeInfo("Anime", "rick_roll", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"")
        val arg = AnimeDetailFragmentArgs(infoWithSmallSynopsis).toBundle()
        launchFragmentInContainer<AnimeDetailFragment>(arg, R.style.Theme_Jikan)
        Thread.sleep(10000)
    }
}