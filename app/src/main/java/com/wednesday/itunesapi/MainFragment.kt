package com.wednesday.itunesapi

import android.app.ActionBar
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginEnd
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.wednesday.itunesapi.data.Results
import com.wednesday.itunesapi.data.room.SearchDatabase
import com.wednesday.itunesapi.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MainFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = SearchDatabase.getInstance(application).searchDatabaseDao

        val viewModelFactory = MainViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        setListeners(binding)

        return binding.root
    }

    private fun setListeners(binding: MainFragmentBinding) {

        binding.mainSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {

                if (text.isNotEmpty()) {
                    if (isNetworkAvailable())
                        viewModel.searchOnline(text)
                    else
                        viewModel.getFromDatabase(text)
                }

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        viewModel.result.observe(viewLifecycleOwner, {
            if(it.isEmpty()){
                Toast.makeText(requireContext(), "Not Found!", Toast.LENGTH_SHORT).show()
            }
            else {
                binding.gridView.removeAllViews()
                val inflater =
                    requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.MATCH_PARENT
                )
                binding.gridView.addView(inflater.inflate(R.layout.table_header, null))
                it.forEach { track ->

                    binding.gridView.addView(generateRow(layoutInflater, track, layoutParams))

                }
            }
        })
    }

    private fun generateRow(inflater: LayoutInflater, track: Results, layoutParams: TableRow.LayoutParams): TableRow{
        val newRow = TableRow(requireContext())

        val wrapperType = inflater.inflate(R.layout.list_item, null)
        wrapperType.findViewById<TextView>(R.id.textView).text = track.wrapperType
        wrapperType.layoutParams = layoutParams
        newRow.addView(wrapperType)

        val kind = inflater.inflate(R.layout.list_item, null)
        kind.findViewById<TextView>(R.id.textView).text = track.kind
        kind.layoutParams = layoutParams
        newRow.addView(kind)

        val artistId = inflater.inflate(R.layout.list_item, null)
        artistId.findViewById<TextView>(R.id.textView).text = track.artistId.toString()
        artistId.layoutParams = layoutParams
        newRow.addView(artistId)

        val collectionId = inflater.inflate(R.layout.list_item, null)
        collectionId.findViewById<TextView>(R.id.textView).text = track.collectionId.toString()
        collectionId.layoutParams = layoutParams
        newRow.addView(collectionId)

        val trackId = inflater.inflate(R.layout.list_item, null)
        trackId.findViewById<TextView>(R.id.textView).text = track.trackId.toString()
        trackId.layoutParams = layoutParams
        newRow.addView(trackId)

        val artistName = inflater.inflate(R.layout.list_item, null)
        artistName.findViewById<TextView>(R.id.textView).text = track.artistName
        artistName.layoutParams = layoutParams
        newRow.addView(artistName)

        val collectionName = inflater.inflate(R.layout.list_item, null)
        collectionName.findViewById<TextView>(R.id.textView).text = track.collectionName
        collectionName.layoutParams = layoutParams
        newRow.addView(collectionName)

        val trackName = inflater.inflate(R.layout.list_item, null)
        trackName.findViewById<TextView>(R.id.textView).text = track.trackName
        trackName.layoutParams = layoutParams
        newRow.addView(trackName)

        val collectionCensoredName = inflater.inflate(R.layout.list_item, null)
        collectionCensoredName.findViewById<TextView>(R.id.textView).text = track.collectionCensoredName
        collectionCensoredName.layoutParams = layoutParams
        newRow.addView(collectionCensoredName)

        val trackCensoredName = inflater.inflate(R.layout.list_item, null)
        trackCensoredName.findViewById<TextView>(R.id.textView).text = track.trackCensoredName
        trackCensoredName.layoutParams = layoutParams
        newRow.addView(trackCensoredName)

        val artistViewUrl = inflater.inflate(R.layout.list_item, null)
        artistViewUrl.findViewById<TextView>(R.id.textView).text = track.artistViewUrl
        artistViewUrl.layoutParams = layoutParams
        newRow.addView(artistViewUrl)

        val collectionViewUrl = inflater.inflate(R.layout.list_item, null)
        collectionViewUrl.findViewById<TextView>(R.id.textView).text = track.collectionViewUrl
        collectionViewUrl.layoutParams = layoutParams
        newRow.addView(collectionViewUrl)

        val trackViewUrl = inflater.inflate(R.layout.list_item, null)
        trackViewUrl.findViewById<TextView>(R.id.textView).text = track.trackViewUrl
        trackViewUrl.layoutParams = layoutParams
        newRow.addView(trackViewUrl)

        val previewUrl = inflater.inflate(R.layout.list_item, null)
        previewUrl.findViewById<TextView>(R.id.textView).text = track.previewUrl
        previewUrl.layoutParams = layoutParams
        newRow.addView(previewUrl)

        val artworkUrl30 = inflater.inflate(R.layout.list_item, null)
        artworkUrl30.findViewById<TextView>(R.id.textView).text = track.artworkUrl30
        artworkUrl30.layoutParams = layoutParams
        newRow.addView(artworkUrl30)

        val artworkUrl60 = inflater.inflate(R.layout.list_item, null)
        artworkUrl60.findViewById<TextView>(R.id.textView).text = track.artworkUrl60
        artworkUrl60.layoutParams = layoutParams
        newRow.addView(artworkUrl60)

        val artworkUrl100 = inflater.inflate(R.layout.list_item, null)
        artworkUrl100.findViewById<TextView>(R.id.textView).text = track.artworkUrl100
        artworkUrl100.layoutParams = layoutParams
        newRow.addView(artworkUrl100)

        val collectionPrice = inflater.inflate(R.layout.list_item, null)
        collectionPrice.findViewById<TextView>(R.id.textView).text = track.collectionPrice.toString()
        collectionPrice.layoutParams = layoutParams
        newRow.addView(collectionPrice)

        val trackPrice = inflater.inflate(R.layout.list_item, null)
        trackPrice.findViewById<TextView>(R.id.textView).text = track.trackPrice.toString()
        trackPrice.layoutParams = layoutParams
        newRow.addView(trackPrice)

        val releaseDate = inflater.inflate(R.layout.list_item, null)
        releaseDate.findViewById<TextView>(R.id.textView).text = track.releaseDate
        releaseDate.layoutParams = layoutParams
        newRow.addView(releaseDate)

        val collectionExplicitness = inflater.inflate(R.layout.list_item, null)
        collectionExplicitness.findViewById<TextView>(R.id.textView).text = track.collectionExplicitness
        collectionExplicitness.layoutParams = layoutParams
        newRow.addView(collectionExplicitness)

        val trackExplicitness = inflater.inflate(R.layout.list_item, null)
        trackExplicitness.findViewById<TextView>(R.id.textView).text = track.trackExplicitness
        trackExplicitness.layoutParams = layoutParams
        newRow.addView(trackExplicitness)

        val discCount = inflater.inflate(R.layout.list_item, null)
        discCount.findViewById<TextView>(R.id.textView).text = track.discCount.toString()
        discCount.layoutParams = layoutParams
        newRow.addView(discCount)

        val discNumber = inflater.inflate(R.layout.list_item, null)
        discNumber.findViewById<TextView>(R.id.textView).text = track.discNumber.toString()
        discNumber.layoutParams = layoutParams
        newRow.addView(discNumber)

        val trackCount = inflater.inflate(R.layout.list_item, null)
        trackCount.findViewById<TextView>(R.id.textView).text = track.trackCount.toString()
        trackCount.layoutParams = layoutParams
        newRow.addView(trackCount)

        val trackNumber = inflater.inflate(R.layout.list_item, null)
        trackNumber.findViewById<TextView>(R.id.textView).text = track.trackNumber.toString()
        trackNumber.layoutParams = layoutParams
        newRow.addView(trackNumber)

        val trackTimeMillis = inflater.inflate(R.layout.list_item, null)
        trackTimeMillis.findViewById<TextView>(R.id.textView).text = track.trackTimeMillis.toString()
        trackTimeMillis.layoutParams = layoutParams
        newRow.addView(trackTimeMillis)

        val country = inflater.inflate(R.layout.list_item, null)
        country.findViewById<TextView>(R.id.textView).text = track.country
        country.layoutParams = layoutParams
        newRow.addView(country)

        val currency = inflater.inflate(R.layout.list_item, null)
        currency.findViewById<TextView>(R.id.textView).text = track.currency
        currency.layoutParams = layoutParams
        newRow.addView(currency)

        val primaryGenreName = inflater.inflate(R.layout.list_item, null)
        primaryGenreName.findViewById<TextView>(R.id.textView).text = track.primaryGenreName
        primaryGenreName.layoutParams = layoutParams
        newRow.addView(primaryGenreName)

        val isStreamable = inflater.inflate(R.layout.list_item, null)
        isStreamable.findViewById<TextView>(R.id.textView).text = track.isStreamable.toString()
        isStreamable.layoutParams = layoutParams
        newRow.addView(isStreamable)

        return newRow

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

}