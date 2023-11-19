package com.spotify.quavergd06.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.fetchables.ArtistFetchable
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.databinding.FragmentTrackInfoBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class TrackInfoFragment : Fragment() {

    private var _binding: FragmentTrackInfoBinding? = null
    private val binding get() = _binding!!

    private var statsItem: StatsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TrackInfoFragment", "onCreate")

        arguments?.let {
            statsItem = it.getSerializable("statsItem") as? StatsItem
        }

        Log.d("TrackInfoFragment", "statsItem: $statsItem")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackInfoBinding.inflate(inflater, container, false)

        setButtonListener()

        return binding.root
    }

    private fun setButtonListener() {
        binding.artistName.setOnClickListener{
            Log.d("TrackInfoFragment", "artistName clicked")
            findNavController().navigate(R.id.action_trackInfoFragment_to_artistInfoFragment, ArtistInfoFragment.newInstance(statsItem?.artist!!.toStatsItem()).arguments)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(statsItem!!)
        Log.d("TrackInfoFragment", "onViewCreated")
    }

    private fun setupUI(statsItem: StatsItem) {
        with(binding) {
            artistName.text = getString(R.string.artist, statsItem.artist?.name)

            trackName.text = getString(R.string.track_title, statsItem.name.toString())
            albumName.text = getString(R.string.album_name, statsItem.album)

            Picasso.get().load(statsItem.imageUrls?.get(0)).into(albumImage)
        }
    }

    companion object {
        fun newInstance(statsItem: StatsItem) = TrackInfoFragment().apply {
            arguments = Bundle().apply {
                putSerializable("statsItem", statsItem)
            }
        }
    }

}