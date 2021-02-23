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

class SecondFragment : Fragment() {
    companion object {
        var globalCount = 0
    }

    val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        var count = args.count
        globalCount = count
        val countDisplay = view.findViewById<TextView>(R.id.second_screen_text)
        countDisplay.text = count.toString()
        view.findViewById<Button>(R.id.second_screen_button).setOnClickListener {
            val action = SecondFragmentDirections.actionSecondScreenFragmentSelf(++count)
            it.findNavController().navigate(action)
        }
        return view
    }
}