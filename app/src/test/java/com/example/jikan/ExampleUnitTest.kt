package com.example.jikan

import com.example.jikan.utils.NetworkClient
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NetworkLayerTest {

    @Test
    fun testSearchAnimeByName() {
        val response = NetworkClient.getAnimeByName("cowboy Bebop").execute()
        with(response) {
            if (isSuccessful) println(body())
            else println(errorBody())
        }
    }
}
