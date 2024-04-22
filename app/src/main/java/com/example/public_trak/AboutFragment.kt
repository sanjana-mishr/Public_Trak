package com.example.public_trak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about2, container, false)

        // Access the string from resources
        val aboutDescription = getString(R.string.about_us_description)

        // Now you can use `aboutDescription` wherever you need it, such as setting a TextView text

        return view
    }





}
