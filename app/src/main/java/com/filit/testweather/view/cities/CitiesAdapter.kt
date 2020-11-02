package com.filit.testweather.view.cities

import android.view.View
import android.view.ViewGroup
import com.filit.domain.model.CityModel
import com.filit.testweather.R
import com.filit.testweather.common.extension.createSmallCloudyImageUrl
import com.filit.testweather.common.extension.loadImage
import com.filit.testweather.view.BaseAdapter
import com.filit.testweather.view.BaseViewHolder
import kotlinx.android.synthetic.main.item_city.*


class CitiesAdapter(
    items: List<CityModel>,
    private val listener: Listener
): BaseAdapter<CityModel>(items) {

    interface Listener {
        fun onClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CityModel> =
        ViewHolder(
            createView(
                viewGroup = parent,
                viewRes = R.layout.item_city
            )
        )

    inner class ViewHolder(view: View) : BaseViewHolder<CityModel>(view) {
        override fun bind(item: CityModel, position: Int) {
            tvCityName.text = item.city
            tvCountry.text = item.country
            tvTemperature.text = item.temperature
            icCloudy.loadImage(item.cloudy_URL.createSmallCloudyImageUrl())
        }

        init {
            view.setOnClickListener{listener.onClick(adapterPosition)}
        }
    }
}