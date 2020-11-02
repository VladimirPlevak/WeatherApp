package com.filit.testweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.filit.testweather.viewmodel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.ParametersDefinition
import kotlin.reflect.KClass

abstract class BaseActivity<T : BaseViewModel>(
    private val viewModelClass: KClass<T>
) : AppCompatActivity() {

    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(clazz = viewModelClass, parameters = viewModelParameters)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    protected open val viewModelParameters: ParametersDefinition? = null
}