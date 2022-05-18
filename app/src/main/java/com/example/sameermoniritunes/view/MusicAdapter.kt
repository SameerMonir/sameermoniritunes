package com.example.sameermoniritunes.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sameermoniritunes.R
import com.example.sameermoniritunes.model.ITunesSong
import com.squareup.picasso.Picasso

class MusicAdapter(private val list: List<ITunesSong>): RecyclerView.Adapter<MusicAdapter.SongViewHolder>() {

    inner class  SongViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun onBind(iTunesSong: ITunesSong){

            val tvArtist: TextView = itemView.findViewById(R.id.tv_artist_name)
            val tvCollection: TextView = itemView.findViewById(R.id.tv_collection)
            val tvSong : TextView =  itemView.findViewById(R.id.tv_song_name)
            val tvSongPrice: TextView = itemView.findViewById(R.id.tv_song_price)

            val ivUserThumbnail: ImageView = itemView.findViewById(R.id.iv_song_thumbnail)

            tvArtist.text = iTunesSong.artistName
            tvCollection.text = iTunesSong.collectionName
            tvSong.text = iTunesSong.trackName

            ("$" + iTunesSong.trackPrice.toString()).also { tvSongPrice.text = it }
            tvSongPrice.text = tvSongPrice.text.replace("^[$][-].*$".toRegex(), "***FREE***")

            Picasso.get()
                .load(iTunesSong.artworkUrl60)
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .into(ivUserThumbnail)

            itemView.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.setDataAndType(Uri.parse(iTunesSong.previewUrl), "audio/*")
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val listItem = LayoutInflater.from(parent.context).inflate(R.layout.music_detail, parent, false)

        return SongViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}