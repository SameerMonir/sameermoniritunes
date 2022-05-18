package com.example.sameermoniritunes.fragments


import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sameermoniritunes.R
import com.example.sameermoniritunes.apis.ApiServiceITunes
import com.example.sameermoniritunes.model.ITunesResponse
import com.example.sameermoniritunes.view.MusicAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicFragment : Fragment() {

    lateinit var rvSongList: RecyclerView
    lateinit var musicAdapter: MusicAdapter
    lateinit var swipeRefresher: SwipeRefreshLayout

    private var musicType : Int = CLASSIC

    companion object {

        const val MUSIC_KEY = "MUSIC_TYPE"

        const val CLASSIC = 0
        const val ROCK = 1
        const val POP = 2

        fun newInstance(musicType: Int) : MusicFragment{
            val fragment =MusicFragment()
            val bundle = Bundle()

            bundle.putInt(MUSIC_KEY, musicType)
            fragment.arguments = bundle

            return fragment
        }
    }

    private fun setRandomBackground(view : View)
    {
        val randomBackground : GradientDrawable = GradientDrawable()
        val background_colours: IntArray = IntArray(2)
        background_colours[0] = (0x70000000 or (0..16777215).random()).toInt()
        background_colours[1] = (0x70000000 or (0..16777215).random()).toInt()

        randomBackground.colors = background_colours
        randomBackground.gradientType = LINEAR_GRADIENT

        view.background = randomBackground
    }

    fun getSongs(inflater: LayoutInflater, view: View)
    {
        musicType = requireArguments().getInt(MUSIC_KEY)

        setRandomBackground(view)

        if (musicType == CLASSIC){
            startRetrofit(inflater, ApiServiceITunes.createRetrofit().create(ApiServiceITunes::class.java).getClassicSongs())
        }else if(musicType == ROCK){
            startRetrofit(inflater, ApiServiceITunes.createRetrofit().create(ApiServiceITunes::class.java).getRockSongs())
        }else if(musicType == POP){
            startRetrofit(inflater, ApiServiceITunes.createRetrofit().create(ApiServiceITunes::class.java).getPopSongs())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_layout, container, false)

        rvSongList = view.findViewById(R.id.rv_songs)
        swipeRefresher = view.findViewById(R.id.swipe_refresher)

        getSongs(inflater, view)

        // TODO: tab_viewpager. set background color for frags

        swipeRefresher.setOnRefreshListener {

            swipeRefresher.isRefreshing = true

            getSongs(inflater, view)

            // TODO: shuffle song cards
        }

        return view
    }

    private fun startRetrofit(inflater: LayoutInflater, call: Call<ITunesResponse>){
        call.enqueue(object: Callback<ITunesResponse>{
            override fun onResponse(
                call: Call<ITunesResponse>,
                response: Response<ITunesResponse>
            ) {
                if (response.isSuccessful){
                   musicAdapter = MusicAdapter(response.body()!!.results)
                    rvSongList.adapter = musicAdapter
                    swipeRefresher.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                Toast.makeText(inflater.context, t.message, Toast.LENGTH_LONG).show()
                swipeRefresher.isRefreshing = false
            }
        })
    }
}