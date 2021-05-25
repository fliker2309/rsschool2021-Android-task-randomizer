package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.NumberFormatException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null

    private var communicator: Communicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Communicator) {
            communicator = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            try {
                val min = minValue?.text.toString()
                val max = maxValue?.text.toString()

                if (min.isEmpty() || max.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please, input numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (min.toInt() > max.toInt()) {
                    Toast.makeText(
                        requireContext(),
                        "Please, input correct numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                communicator?.onFirstFragmentListener(min.toInt(), max.toInt())

            } catch (e: NumberFormatException) {
                Toast.makeText(
                    requireContext(),
                    "Please, input lesser numbers",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        communicator = null
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}