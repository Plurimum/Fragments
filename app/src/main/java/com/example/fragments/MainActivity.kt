package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.fragments.fragments.FirstFragment
import com.example.fragments.fragments.SecondFragment
import com.example.fragments.fragments.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERSIST_BOTTOM_NAVIGATION_STATE = "BOTTOM_STATE"
        const val CUR_BACK_STACK = "CUR_BACK"
    }

    private var currentNavController: LiveData<NavController>? = null
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var stackGlobal: ArrayList<Int>
    private var curBackStack = 0
    private val graphIdToTagMap = SparseArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stackGlobal = savedInstanceState?.getIntegerArrayList(PERSIST_BOTTOM_NAVIGATION_STATE)
            ?: ArrayList<Int>().also {
                it.add(R.id.first_navigation)
            }
        curBackStack = savedInstanceState?.getInt(CUR_BACK_STACK) ?: 0
        setupBottomNavigation()
        savedInstanceState?.let {
            bottomNavigationView.selectedItemId = it.getInt(PERSIST_BOTTOM_NAVIGATION_STATE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList(PERSIST_BOTTOM_NAVIGATION_STATE, stackGlobal)
        outState.putInt(CUR_BACK_STACK, curBackStack)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (getCurrentCount(curBackStack) != 0 || stackGlobal.size == 1) {
            super.onBackPressed()
        } else {
            curBackStack = bottomNavigationView.handleBackButtonPressed(
                supportFragmentManager,
                stackGlobal,
                graphIdToTagMap
            )
        }
    }

    private fun getCurrentCount(number: Int) : Int {
        return when (number) {
            0 -> FirstFragment.globalCount
            1 -> SecondFragment.globalCount
            else -> ThirdFragment.globalCount
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navGraphIds = listOf(
            R.navigation.first_navigation,
            R.navigation.second_navigation,
            R.navigation.third_naviagtion
        )
        bottomNavigationView.selectedItemId = stackGlobal.last()
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.my_nav_host_fragment,
            intent = intent,
            stackGlobal = stackGlobal,
            graphIdToTagMap = graphIdToTagMap
        ) { newGraph -> curBackStack = newGraph}
        currentNavController = controller
    }
}