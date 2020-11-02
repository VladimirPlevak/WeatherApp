package com.filit.testweather.view.weather

import android.view.View
import android.view.ViewGroup
import com.filit.domain.model.WeatherForecastModel
import com.filit.testweather.R
import com.filit.testweather.common.extension.*
import com.filit.testweather.view.BaseAdapter
import com.filit.testweather.view.BaseViewHolder
import kotlinx.android.synthetic.main.item_weather_forecast.*

class WeatherForecastAdapter(
    items: List<WeatherForecastModel>
) : BaseAdapter<WeatherForecastModel>(items) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<WeatherForecastModel> =
        ViewHolder(createView(viewGroup = parent, viewRes = R.layout.item_weather_forecast))

    inner class ViewHolder(view: View) : BaseViewHolder<WeatherForecastModel>(view) {
        override fun bind(item: WeatherForecastModel, position: Int) {
            tvDateForecast.text = item.date.toDate()
            tvDayOfWeek.text = item.date.toDayOfWeek()
            tvAverageTemp.text = item.minTemp
            tvTemp.text = item.maxTemp
            icCloudy.loadImage(item.imageUrl.createSmallCloudyImageUrl())
        }
    }
}