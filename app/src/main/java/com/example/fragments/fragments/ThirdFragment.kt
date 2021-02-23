package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fragments.R

class ThirdFragment : Fragment() {
    companion object {
        var globalCount = 0
    }

    val args: ThirdFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        var count = args.count
        globalCount = count
        val countDisplay = view.findViewById<TextView>(R.id.third_screen_text)
        countDisplay.text = count.toString()
        view.findViewById<Button>(R.id.third_screen_button).setOnClickListener {
            val action = ThirdFragmentDirections.actionThirdScreenFragmentSelf(++count)
            it.findNavController().navigate(action)
        }
        return view
    }
}