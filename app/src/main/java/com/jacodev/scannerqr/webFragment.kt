package com.jacodev.scannerqr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_web.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val URL = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [webFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class webFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var url: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista:View =  inflater.inflate(R.layout.fragment_web, container, false)
        vista.miWebView.webViewClient = WebViewClient()
        url?.let { vista.miWebView.loadUrl(it) }
        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment webFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(url: String) =
            webFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url )
                                   }
            }
    }
}